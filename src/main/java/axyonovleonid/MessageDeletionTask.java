package axyonovleonid;

import jdk.internal.net.http.common.Pair;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class MessageDeletionTask implements Runnable {
    private static Logger logger = Logger.getLogger(MessageDeletionTask.class);

    private Bot bot;
    private Long chatId;
    private List<Pair<Integer, Long>> times = new ArrayList<>();

    public MessageDeletionTask(Bot bot, Long chatId) {
        this.bot = bot;
        this.chatId = chatId;
    }

    public void deleteMessage(Integer messageId, Long deletionTime) {
        times.add(new Pair<>(messageId, deletionTime));
    }

    @Override
    public void run() {
        try {
            while (true) {
                long currentTime = System.currentTimeMillis();
                List<Pair<Integer, Long>> toDelete = new ArrayList<>();
                for (Pair<Integer, Long> time : times) {
                    if (currentTime >= time.second) {
                        bot.execute(new DeleteMessage(chatId, time.first));
                        toDelete.add(time);
                    }
                }
                times.removeAll(toDelete);
                Thread.sleep(100);
            }
        } catch (InterruptedException | TelegramApiException e) {
            logger.error(e);
        }
    }
}
