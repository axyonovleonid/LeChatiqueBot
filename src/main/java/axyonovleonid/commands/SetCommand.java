package axyonovleonid.commands;

import axyonovleonid.Bot;
import axyonovleonid.ChatTimers;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class SetCommand implements AdminCommand {
    @Override
    public String getCommandIdentifier() {
        return "le_set";
    }

    @Override
    public String getDescription() {
        return " set deletion timer value for selected type. Time in seconds, if set to 0 bot ignores type";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        GetChatAdministrators getChatAdministrators = new GetChatAdministrators().setChatId(message.getChatId());
        try {
            List<User> admins = AdminCommand.getAdmins(absSender, getChatAdministrators);
            if (admins.contains(message.getFrom())) {
                ChatTimers chatTimers = ((Bot) absSender).getTimers().get(message.getChatId());
                SendMessage response = new SendMessage()
                        .setReplyToMessageId(message.getMessageId())
                        .setChatId(message.getChatId());
                String type = null;
                Long time = Long.parseLong(strings[1]);
                switch (strings[0]) {
                    case "gif":
                        chatTimers.setGifTimer(time);
                        type = strings[0];
                        break;
                    case "sticker":
                        chatTimers.setStickerTimer(time);
                        type = strings[0];
                        break;
                    case "image":
                        chatTimers.setImageTimer(time);
                        type = strings[0];
                        break;
                    case "video":
                        chatTimers.setVideoTimer(time);
                        type = strings[0];
                        break;
                    case "animatedSticker":
                        chatTimers.setAnimatedStickerTimer(time);
                        type = strings[0];
                        break;
                    default:
                        break;
                }
                if (type != null) {
                    response.setText("Timer for " + type + " is " + time + "seconds");
                    absSender.execute(response);
                }
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
