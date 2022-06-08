package pro.sky.telegrambot.service;

import pro.sky.telegrambot.model.NotificationModel;

import java.util.Optional;
import java.util.function.Consumer;

public interface NotificationService {

    NotificationModel scheduleNotification(NotificationModel notification, Long chatId);

    Optional<NotificationModel> parseMessage(String notificationBotMessage);

    void sendNotificationMessage(Consumer<NotificationModel> notification);
}
