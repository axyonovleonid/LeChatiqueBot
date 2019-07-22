package axyonovleonid.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class HelpCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "le_help";
    }

    @Override
    public String getDescription() {
        return "Print help";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        SendMessage msg = new SendMessage(message.getChatId(), "/le_set [gif, sticker, video, image] time — set deletion timer value " +
                "for selected type. Time in seconds, if set to 0 bot ignores type \n" +
                "/le_allow_channel — reply with this command to forwarded from channel message to ignore content from it [in progress]");
        try {
            absSender.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
