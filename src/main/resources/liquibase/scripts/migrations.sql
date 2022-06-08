-- liquibase formatted sql

-- changeset algmironov:1
create table notifications
(
    id                   SERIAL NOT NULL PRIMARY KEY,
    chatId               bigint NOT NULL,
    notification_date    timestamp NOT NULL,
    notification_message text NOT NULL,
    status               varchar(255) NOT NULL DEFAULT 'SCHEDULED',
    notification_sent    timestamp
);

-- changeset algmironov:2
CREATE INDEX notification_date_index ON notifications (notification_date) WHERE status = 'SCHEDULED';

-- changeset algmironov:3
ALTER TABLE notifications RENAME COLUMN chatId to chat_id;


-- CREATE INDEX notificationDate_index ON notifications (notificationDate);