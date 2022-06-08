package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.NotificationModel;

import java.util.Collection;

@Repository
public interface NotificationsRepository extends JpaRepository<NotificationModel, Long> {

    @Query(value = "SELECT * FROM notifications WHERE notification_date <= CURRENT_TIMESTAMP AND status = 'SCHEDULED'", nativeQuery = true)
    Collection<NotificationModel> getScheduledNotifications();

}
