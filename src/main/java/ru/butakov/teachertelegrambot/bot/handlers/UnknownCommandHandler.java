package ru.butakov.teachertelegrambot.bot.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.butakov.teachertelegrambot.bot.SendMessageFormat;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UnknownCommandHandler implements InputMessageHandler {
    SendMessageFormat sendMessageFormat;
    @Override
    public SendMessage handle(Message message) {
        long chatId = message.getChatId();

        var sendMessage = sendMessageFormat.getSendMessageBaseFormat(chatId);
        sendMessage.setText("unknown command, send 'next'");
        return sendMessage;
    }

    @Override
    public String getHandlerCommand() {
        return "unknown";
    }
}
