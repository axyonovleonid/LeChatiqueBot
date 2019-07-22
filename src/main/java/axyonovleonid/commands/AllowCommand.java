package axyonovleonid.commands;

import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class AllowCommand implements AdminCommand {
    @Override
    public String getCommandIdentifier() {
        return "le_allow_channel";
    }

    @Override
    public String getDescription() {
        return "Reply with this command to forwarded from channel message to ignore content from it [in progress]";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        GetChatAdministrators getChatAdministrators = new GetChatAdministrators().setChatId(message.getChatId());
        try {
            List<User> admins = AdminCommand.getAdmins(absSender, getChatAdministrators);
            if (admins.contains(message.getFrom())) {
                if (message.isChannelMessage()) {

                    //                  message.getForwardFromChat().get
                }
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
