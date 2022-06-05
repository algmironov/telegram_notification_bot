package pro.sky.telegrambot.texts;

public class CommandsAndTexts {

    public static final String START_CMD = "/start";
    public static final String WELCOME_TEXT = "Welcome to Notification BOT";
    public static final String HELP_CMD = "/help";
    public static final String INVALID_NOTIFICATION_OR_CMD = "Something went wrong, please try again!";
    public static final String HELP_TEXT = "To make new notification just send to me new message in further format:\n" +
            "dd.MM.yyyy_HH:mm_Notification_text where\n" +
            "'dd' - day\n'MM' - month\n'yyyy' - year\n" +
            "'HH' - hour\n'mm' - minute\n" +
            "Notification_text - the text you wish to receive\n" +
            "and '_' are spaces between date, time and text";
}
