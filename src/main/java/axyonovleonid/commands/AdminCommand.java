package axyonovleonid.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.objects.ChatMember;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public interface AdminCommand extends IBotCommand {
    static List<User> getAdmins(AbsSender absSender, GetChatAdministrators getChatAdministrators) throws TelegramApiException {
        List<ChatMember> memberList = absSender.execute(getChatAdministrators);
        List<User> admins = new ArrayList<>();
        for (ChatMember member : memberList) {
            admins.add(member.getUser());
        }
        return admins;
    }
}
