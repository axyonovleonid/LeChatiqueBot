package axyonovleonid;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class MessageDeletionTask implements Runnable {
    private static Logger logger = Logger.getLogger(MessageDeletionTask.class);

    static {

    }
    private Long time;
    private DeleteMessage method;
    private Bot bot;

    public MessageDeletionTask(Long time, DeleteMessage method, Bot bot) {
        this.time = time;
        this.method = method;
        this.bot = bot;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(time * 1000);
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
