package axyonovleonid;//package axyonovleonid;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Bot extends TelegramLongPollingBot {
    private static Logger logger = Logger.getLogger(Bot.class.getName());

    public static void main(String... args) {
        ApiContextInitializer.init();
//        DOMConfigurator.configure("log4j.xml");
        logger.debug("Log4j appender configuration is successful !!");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new Bot());
            System.out.println("Bot created!!!");
            logger.info("Bot created");
        } catch (TelegramApiRequestException e) {
            logger.error("Exception!", e);
        }
    }

//    @Override
//    public BotApiMethod onWebhookUpdateReceived(Update update) {
//        if(update.hasMessage()){
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
//        return null;
//    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            logger.info(message.getText());
            SendMessage sendMessage = new SendMessage(message.getChatId(), message.getText());
            DeleteMessage deleteMessage = new DeleteMessage(message.getChatId(), message.getMessageId());
            try {
                execute(sendMessage);
                execute(deleteMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "le_chatique_bot";
    }

    @Override
    public String getBotToken() {
        return "928489810:AAE-Ay0Hs5w4M1hRd1pgFDpU43xcfdWGuLQ";
    }

//    @Override
//    public String getBotPath() {
//        return "https://le-chatique-bot.herokuapp.com/";
//    }
}
