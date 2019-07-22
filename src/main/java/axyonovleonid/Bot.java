package axyonovleonid;//package axyonovleonid;

import axyonovleonid.commands.SetCommand;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bot extends TelegramLongPollingCommandBot {
    private static Logger logger = Logger.getLogger(Bot.class.getName());

    static {
        List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
        loggers.add(LogManager.getRootLogger());
        for (Logger logger : loggers) {
            logger.setLevel(Level.OFF);
        }
    }

    private Map<Long, ChatTimers> timers = new HashMap<>();
    private Map<Long, List<Long>> allowedChannels = new HashMap<>();

    public Bot(String botUsername) {
        super(botUsername);
        register(new HelpCommand());
        register(new axyonovleonid.commands.HelpCommand());
        register(new SetCommand());
    }

    public static void main(String... args) {
        ApiContextInitializer.init();
        logger.setLevel(Level.INFO);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new Bot("le_chatique_bot"));
            logger.info("Bot created");
        } catch (TelegramApiRequestException e) {
            logger.error("Exception!", e);
            System.out.println(e);
        }
    }

    @Override
//    public BotApiMethod onWebhookUpdateReceived(Update update) {
//    public void onUpdateReceived(Update update) {
//        logger.info(update);
    public void processNonCommandUpdate(Update update) {
        if (update.hasChannelPost()) {
            logger.info("CHANNEL POST = " + update.getChannelPost().getForwardFromChat().getTitle());
        }
        if (update.hasMessage()) {
            Message message = update.getMessage();
            logger.info(message.toString());
//            if (message.hasVideoNote()) {
//                logger.info("video note");
//            }
//            if (message.hasDocument()) {
//                logger.info("document");
//            }
//            if (message.hasEntities()) {
//                logger.info("entities");
//            }

            long chatId = message.getChatId();
            Integer messageId = message.getMessageId();
            if (!timers.containsKey(chatId)) {
                timers.put(chatId, new ChatTimers());
            }

            try {
//                if (message.hasText() && message.getText().startsWith("/"))
//                {
//                    String text = message.getText();
//                    SendMessage response = new SendMessage();
//                    if (text.startsWith("/le_help")) {
//
//                    } else if (update.getC) (text.startsWith("/set")) {
//                        String[] args = text.split(" ");
//                        switch (args[1]) {
//                            case "gif":
//                                timers.get(chatId).setGifTimer(Long.parseLong(args[2]));
//                                break;
//                            case "sticker":
//                                timers.get(chatId).setStickerTimer(Long.parseLong(args[2]));
//                                break;
//                            case "image":
//                                timers.get(chatId).setImageTimer(Long.parseLong(args[2]));
//                                break;
//                            case "video":
//                                timers.get(chatId).setVideoTimer(Long.parseLong(args[2]));
//                                break;
//                            case "animatedSticker":
//                                timers.get(chatId).setAnimatedStickerTimer(Long.parseLong(args[2]));
//                                break;
//                            default:
//                                break;
//                        }
//
//                    }
//                } else
                if (message.hasText() && message.getText().contains("would like to kick @smeshnotebesuka")) {
                    DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
                    execute(deleteMessage);
                } else if (message.hasAnimation()) {
                    new Thread(new MessageDeletionTask(timers.get(chatId).getGifTimer(),
                            new DeleteMessage(chatId, messageId), this)).start();
                } else if (message.hasPhoto()) {
                    new Thread(new MessageDeletionTask(timers.get(chatId).getImageTimer(),
                            new DeleteMessage(chatId, messageId), this)).start();
                } else if (message.hasVideo()) {
                    new Thread(new MessageDeletionTask(timers.get(chatId).getVideoTimer(),
                            new DeleteMessage(chatId, messageId), this)).start();
                } else if (message.hasSticker()) {
                    new Thread(new MessageDeletionTask(timers.get(chatId).getStickerTimer(),
                            new DeleteMessage(chatId, messageId), this)).start();
                }
            } catch (TelegramApiException e) {
                logger.error(e);
            }


            logger.info("update end");
//        return null;
        }
    }


//    @Override
//    public String getBotUsername() {
//        return "le_chatique_bot";
//    }

//    @Override
//    public void processNonCommandUpdate(Update update) {
//
//    }

    @Override
    public String getBotToken() {
        return "928489810:AAE-Ay0Hs5w4M1hRd1pgFDpU43xcfdWGuLQ";
    }

    public Map<Long, List<Long>> getAllowedChannels() {
        return allowedChannels;
    }

    public void setAllowedChannels(Map<Long, List<Long>> allowedChannels) {
        this.allowedChannels = allowedChannels;
    }

    public Map<Long, ChatTimers> getTimers() {
        return timers;
    }

    public void setTimers(Map<Long, ChatTimers> timers) {
        this.timers = timers;
    }

    //    @Override
//    public String getBotPath() {
//        logger.info("path requested");
//        return "https://le-chatique-bot.herokuapp.com/";
//    }
}
