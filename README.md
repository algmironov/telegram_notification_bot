# telegram_notification_bot
Telegram Notification Bot

This bot is made to apply notifications (in format like "01.01.2023 01:01 _Notify me about something_") 
from user and resend user's message back to him at the date and time user asked (01.01.2023 01:01)
The bot parses date and time from message to correctly send the notification back at the correct time.
Bot uses PostgreSQL database to store userID, notification date/time and notification text, plus marks if notiifcation is sent and sets the timestamp of when notification was exected.
