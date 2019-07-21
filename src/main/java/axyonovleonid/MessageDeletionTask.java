package axyonovleonid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class MessageDeletionTask implements Runnable {
    private static Logger logger = Logger.getLogger(MessageDeletionTask.class);

    static {
        logger.setLevel(Level.OFF);
    }
    private Long time;
    private BotApiMethod method;
    private Bot bot;

    public MessageDeletionTask(Long time, BotApiMethod method, Bot bot) {
        this.time = time;
        this.method = method;
        this.bot = bot;
    }

    @Override
    public void run() {
        try {
            wait(time);
            bot.execute(method);
        } catch (InterruptedException | TelegramApiException e) {
            logger.error(e);
        }
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
