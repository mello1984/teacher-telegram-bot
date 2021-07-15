package ru.butakov.teachertelegrambot.bot.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.butakov.teachertelegrambot.bot.SendMessageFormat;
import ru.butakov.teachertelegrambot.service.ActivityService;
import ru.butakov.teachertelegrambot.utils.ActivityFormat;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizHandlerImpl implements InputMessageHandler {
    ActivityService activityService;
    SendMessageFormat sendMessageFormat;

    @Override
    public SendMessage handle(Message message) {
        if (!message.getText().equals(getHandlerCommand())) return null;
        var activity = activityService.findRandom();
        long chatId = message.getChatId();

        var sendMessage = sendMessageFormat.getSendMessageBaseFormat(chatId);
        sendMessage.setText(ActivityFormat.toQuiz(activity));
        return sendMessage;
    }

    @Override
    public String getHandlerCommand() {
        return "next";
    }
}
