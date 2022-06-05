package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "notifications")
public class NotificationModel {

    public enum NotificationStatus {
        SCHEDULED,

        SENT,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long chatId;

    private String notificationMessage;

    private LocalDateTime notificationDate;

    private LocalDateTime notificationSent;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.SCHEDULED;

    public NotificationModel() {
    }

    public NotificationModel(String notificationMessage, LocalDateTime notificationDate) {
        this.notificationMessage = notificationMessage;
        this.notificationDate = notificationDate;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    public LocalDateTime getNotificationSent() {
        return notificationSent;
    }

    public void setNotificationSent(LocalDateTime notificationSent) {
        this.notificationSent = notificationSent;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public void setAsSent() {
        this.status = NotificationStatus.SENT;
        this.notificationSent = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationModel)) return false;
        NotificationModel that = (NotificationModel) o;
        return Objects.equals(Id, that.Id) && Objects.equals(getChatId(), that.getChatId()) && Objects.equals(getNotificationMessage(), that.getNotificationMessage()) && Objects.equals(getNotificationDate(), that.getNotificationDate()) && Objects.equals(getNotificationSent(), that.getNotificationSent()) && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, getChatId(), getNotificationMessage(), getNotificationDate(), getNotificationSent(), getStatus());
    }

    @Override
    public String toString() {
        return "NotificationModel{" +
                "Id=" + Id +
                ", chatId=" + chatId +
                ", notificationMessage='" + notificationMessage + '\'' +
                ", notificationDate=" + notificationDate +
                ", notificationSent=" + notificationSent +
                ", status=" + status +
                '}';
    }
}
