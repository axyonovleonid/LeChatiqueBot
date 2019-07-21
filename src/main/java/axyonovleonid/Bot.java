package axyonovleonid;//package axyonovleonid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Bot extends TelegramWebhookBot {
    private static Logger logger = Logger.getLogger(Bot.class.getName());
    //    private int defaultDeletionTime = 60;
    private Map<Long, ChatTimers> timers = new HashMap<>();
//    private static int imageTime = 60;
//    private static int imageTime = 60;

    public static void main(String... args) {
        ApiContextInitializer.init();
//        DOMConfigurator.configure("log4j.xml");
//        logger.debug("Log4j appender configuration is successful !!");
        logger.setLevel(Level.ALL);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new Bot());
            logger.info("Bot created");
        } catch (TelegramApiRequestException e) {
            logger.error("Exception!", e);
            System.out.println(e);
        }
    }

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        logger.info(update);
        if (update.hasMessage()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();
            Integer messageId = message.getMessageId();
            if (!timers.containsKey(chatId)) {
                timers.put(chatId, new ChatTimers());
            }
            if (message.isCommand()) {
                String[] args = message.getText().split(" ");
                if (Objects.equals(args[0], "set")) {
                    switch (args[1]) {
                        case "gif":
                            timers.get(chatId).setGifTimer(Long.parseLong(args[2]));
                            break;
                        case "sticker":
                            timers.get(chatId).setStickerTimer(Long.parseLong(args[2]));
                            break;
                        case "image":
                            timers.get(chatId).setImageTimer(Long.parseLong(args[2]));
                            break;
                        case "video":
                            timers.get(chatId).setVideoTimer(Long.parseLong(args[2]));
                            break;
                        case "animatedSticker":
                            timers.get(chatId).setAnimatedStickerTimer(Long.parseLong(args[2]));
                            break;
                        default:
                            break;
                    }
                    SendMessage reply = new SendMessage(chatId, "Timer for " + args[1] + " messages set for " + args[2] + " s");
                    try {
                        execute(reply);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    logger.info(message.toString());
                    if (message.hasText() && message.getText().contains("would like to kick @smeshnotebesuka")) {
                        DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
                        execute(deleteMessage);
                    }
                    if (message.hasAnimation()) {
                        new Thread(new MessageDeletionTask(timers.get(chatId).getGifTimer(),
                                new DeleteMessage(chatId, messageId), this));
                        SendMessage reply = new SendMessage(chatId, "animation");
                        execute(reply);
                    }
                    if (message.hasPhoto()) {
                        new Thread(new MessageDeletionTask(timers.get(chatId).getImageTimer(),
                                new DeleteMessage(chatId, messageId), this));
                        SendMessage reply = new SendMessage(chatId, "photo");
                        execute(reply);
                    }
                    if (message.hasVideo()) {
                        new Thread(new MessageDeletionTask(timers.get(chatId).getVideoTimer(),
                                new DeleteMessage(chatId, messageId), this));
                        SendMessage reply = new SendMessage(chatId, "video");
                        execute(reply);
                    }
                    if (message.hasSticker()) {
                        new Thread(new MessageDeletionTask(timers.get(chatId).getStickerTimer(),
                                new DeleteMessage(chatId, messageId), this));
                        SendMessage reply = new SendMessage(chatId, "sticker");
                        execute(reply);
                    }
                } catch (TelegramApiException e) {
                    logger.error(e);
                }
            }
        }
        logger.info("update end");
        return null;
    }

//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage()) {
//            Message message = update.getMessage();
//            logger.info(message.getText());
//            SendMessage sendMessage = new SendMessage(message.getChatId(), message.getText());
//            DeleteMessage deleteMessage = new DeleteMessage(message.getChatId(), message.getMessageId());
//            try {
//                execute(sendMessage);
//                execute(deleteMessage);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public String getBotUsername() {
        return "le_chatique_bot";
    }

    @Override
    public String getBotToken() {
        return "928489810:AAE-Ay0Hs5w4M1hRd1pgFDpU43xcfdWGuLQ";
    }

    @Override
    public String getBotPath() {
        logger.info("path requested");
        return "https://le-chatique-bot.herokuapp.com/";
    }
}
