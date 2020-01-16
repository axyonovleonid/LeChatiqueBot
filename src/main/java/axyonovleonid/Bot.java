package axyonovleonid;//package axyonovleonid;

import axyonovleonid.commands.AllowCommand;
import axyonovleonid.commands.SetCommand;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.*;

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
    private Map<Long, Set<Long>> allowedChannels = new HashMap<>();
    private Map<Long, MessageDeletionTask> deletionTaskMap = new HashMap();

    public Bot(String botUsername) {
        super(botUsername);
//        register(new HelpCommand());
        register(new axyonovleonid.commands.HelpCommand());
        register(new SetCommand());
        register(new AllowCommand());
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
        if (update.hasMessage()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();
            Integer messageId = message.getMessageId();
            if (!timers.containsKey(chatId)) {
                timers.put(chatId, new ChatTimers());
            }
            if (!allowedChannels.containsKey(chatId)) {
                allowedChannels.put(chatId, new HashSet<>());
                allowedChannels.get(chatId).add(-1001139599644L);
            }
            if (!deletionTaskMap.containsKey(chatId)) {
                deletionTaskMap.put(chatId, new MessageDeletionTask(this, chatId));
                new Thread(deletionTaskMap.get(chatId)).start();
            }
            try {
                if (message.getForwardFromChat() == null || !allowedChannels.get(chatId).contains(message.getForwardFromChat().getId())) {
                    long time = System.currentTimeMillis();
                    logger.info(message);
                    if (message.hasReplyMarkup() && (message.getText().contains("kick")
                            || message.getText().contains("кикнуть @smeshnotebesuka"))) {
                        DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
                        execute(deleteMessage);
                    } else if (message.hasAnimation()) {
                        Long timer = timers.get(chatId).getGifTimer();
                        if (timer > 0) {
                            deletionTaskMap.get(chatId).deleteMessage(messageId, time + timer * 1000);
                        }
                    } else if (message.hasPhoto()) {
                        Long timer = timers.get(chatId).getImageTimer();
                        if (timer > 0) {
                            deletionTaskMap.get(chatId).deleteMessage(messageId, time + timer * 1000);
                        }
                    } else if (message.hasVideo()) {
                        Long timer = timers.get(chatId).getVideoTimer();
                        if (timer > 0) {
                            deletionTaskMap.get(chatId).deleteMessage(messageId, time + timer * 1000);
                        }
                    } else if (message.hasSticker()) {
                        Long timer = timers.get(chatId).getStickerTimer();
                        if (timer > 0) {
                            deletionTaskMap.get(chatId).deleteMessage(messageId, time + timer * 1000);
                        }
                    }
                }

            } catch (TelegramApiException e) {
                logger.error(e);
            }


//            logger.info("update end");
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

    public Map<Long, Set<Long>> getAllowedChannels() {
        return allowedChannels;
    }

    public void setAllowedChannels(Map<Long, Set<Long>> allowedChannels) {
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
