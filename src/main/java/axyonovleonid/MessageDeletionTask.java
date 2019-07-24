package axyonovleonid;


import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MessageDeletionTask implements Runnable {
    private static Logger logger = Logger.getLogger(MessageDeletionTask.class);

    private Bot bot;
    private Long chatId;
    private List<Pair<Integer, Long>> times = Collections.synchronizedList(new ArrayList<>());

    public MessageDeletionTask(Bot bot, Long chatId) {
        this.bot = bot;
        this.chatId = chatId;
    }

    public void deleteMessage(Integer messageId, Long deletionTime) {
        times.add(new Pair<>(messageId, deletionTime));
    }

    private boolean timeExceeded(Pair<Integer, Long> time) {
        long currentTime = System.currentTimeMillis();
        if (currentTime >= time.second) {
            try {
                bot.execute(new DeleteMessage(chatId, time.first));
            } catch (TelegramApiException e) {
                logger.error(e);
            }
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            times.removeIf(this::timeExceeded);
//            Iterator it = times
        }
    }
}
