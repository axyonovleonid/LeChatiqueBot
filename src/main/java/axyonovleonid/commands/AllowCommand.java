package axyonovleonid.commands;

import axyonovleonid.Bot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            Long channelId = message.getForwardFromChat().getId();
            Map<Long, Set<Long>> allowedChannels = ((Bot) absSender).getAllowedChannels();
            if (!allowedChannels.containsKey(message.getChatId())) {
                allowedChannels.put(message.getChatId(), new HashSet<>());
            }
            if (admins.contains(message.getFrom())) {
                SendMessage response = new SendMessage()
                        .setReplyToMessageId(message.getMessageId())
                        .setChatId(message.getChatId());

                if (allowedChannels.get(message.getChatId()).contains(channelId)) {
                    allowedChannels.get(message.getChatId()).remove(channelId);
                    response.setText("Channel with id " + message.getForwardFromChat().getFirstName() + " "
                            + message.getForwardFromChat().getLastName() + " is not in white list anymore");
                } else {
                    allowedChannels.get(message.getChatId()).add(channelId);
                    response.setText("Channel with id " + message.getForwardFromChat().getFirstName() + " "
                            + message.getForwardFromChat().getLastName() + " is in white list");
                }
                absSender.execute(response);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
