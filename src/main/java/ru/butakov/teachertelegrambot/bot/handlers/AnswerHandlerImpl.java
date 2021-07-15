package ru.butakov.teachertelegrambot.bot.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.butakov.teachertelegrambot.bot.SendMessageFormat;
import ru.butakov.teachertelegrambot.domain.Activity;
import ru.butakov.teachertelegrambot.service.ActivityService;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnswerHandlerImpl implements InputMessageHandler {
    ActivityService activityService;
    SendMessageFormat sendMessageFormat;

    @Override
    public SendMessage handle(Message message) {
        long chatId = message.getChatId();
        long answerId = Long.parseLong(message.getText().substring(1));
        var activity = activityService.findById(answerId);
        var defaultActivity = Activity.builder().answer("Activity not exists").build();
        var sendMessage = sendMessageFormat.getSendMessageBaseFormat(chatId);
        sendMessage.setText(activity.orElse(defaultActivity).getAnswer());
        return sendMessage;
    }

    @Override
    public String getHandlerCommand() {
        return "answer";
    }
}
