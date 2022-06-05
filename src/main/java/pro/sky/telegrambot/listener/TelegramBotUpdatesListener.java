package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationModel;
import pro.sky.telegrambot.service.NotificationService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;


@Service
@EnableScheduling
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private static final String START_CMD = "/start";
    private static final String WELCOME_TEXT = "Welcome to Notification BOT";
    private static final String INVALID_NOTIFICATION_OR_CMD = "Something went wrong, please try again!";

    @Autowired
    private TelegramBot telegramBot;
    private final NotificationService notificationService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationService notificationService) {
        this.telegramBot = telegramBot;
        this.notificationService = notificationService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    //setting up a schedule to check if it's time to send notification. The check completes every minute.
    @Scheduled(cron = "0 0/1 * * * *")
    public void sendNotificationMessage() {
        notificationService.sendNotificationMessage(this::sendMessage);
    }


    //This method processes incoming messages.
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Message message = update.message();
            if (message.text().startsWith(START_CMD)) {
                logger.info(START_CMD + " command has been received at " + LocalDateTime.now());
                sendMessage(extractChatId(message), "Welcome to notification Bot, " + message.from().firstName() + " ");
            } else {
                notificationService.parseMessage(message.text()).ifPresentOrElse(
                       task -> scheduledNotification(extractChatId(message), task),
                        () -> sendMessage(extractChatId(message), INVALID_NOTIFICATION_OR_CMD)
                );
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    //This method sends new notification to be saved
    private void scheduledNotification(Long chatId, NotificationModel notification) {
        notificationService.scheduleNotification(notification, chatId);
        sendMessage(chatId, "New task: " + notification.getNotificationMessage() + " has been scheduled successfully!");
    }

    //This method is from the base API, it makes the bot to send the message into the TG chat
    private void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        telegramBot.execute(sendMessage);
    }

    private void sendMessage(NotificationModel notification) {
        sendMessage(notification.getChatId(), notification.getNotificationMessage());
    }

    //This method extracts chat_id from message for further interaction with THIS user
    private Long extractChatId(Message message) {
        return message.chat().id();
    }

}
