package pro.sky.telegrambot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationModel;
import pro.sky.telegrambot.repository.NotificationsRepository;
import pro.sky.telegrambot.service.NotificationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final NotificationsRepository repository;

    public NotificationServiceImpl(NotificationsRepository repository) {
        this.repository = repository;
    }

    //Saves notification into the Database
    @Override
    public NotificationModel scheduleNotification(NotificationModel notification, Long chatId) {
        notification.setChatId(chatId);
        NotificationModel savedNotification = repository.save(notification);
        logger.info("Notification " + notification + " scheduled successfully ");
        return savedNotification;
    }

    //This method search desirable format of date, time and notification message to work with it
    @Override
    public Optional<NotificationModel> parseMessage(String botMessage) {
        NotificationModel notification = null;
        try {
            String notificationDate = botMessage.substring(0, 16);
            String notificationMessage = botMessage.substring(17);
            LocalDateTime notificationDateTime = LocalDateTime.parse(notificationDate, formatter);
            if (notificationDateTime.isAfter(LocalDateTime.now())) {
                notification = new NotificationModel(notificationMessage, notificationDateTime);
            }
        } catch (Exception e) {
            logger.error("Something went wrong. Probably there were wrong date or time.");
        }
        return Optional.ofNullable(notification);
    }

    //Takes all SCHEDULED notifications from the Database, and if they alredy sent, marks them as SENT.
    @Override
    public void sendNotificationMessage(Consumer<NotificationModel> notification) {
        Collection<NotificationModel> notifications = repository.getScheduledNotifications();
        notifications.forEach(task -> {
            notification.accept(task);
            task.setAsSent();
        });
        repository.saveAll(notifications);

    }
}
