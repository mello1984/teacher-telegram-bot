package ru.butakov.teachertelegrambot.bot.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class AddActivityHandlerImpl implements InputMessageHandler {
    SendMessageFormat sendMessageFormat;
    ActivityService activityService;

    @Override
    public SendMessage handle(Message message) {
        String json = message.getText().replaceFirst("/add ", "");

        ObjectMapper mapper = new ObjectMapper();
        String answerText = "";
        try {
            var activity = mapper.readValue(json, Activity.class);
            activityService.save(activity);
            answerText = "Ok, id=" + activity.getId();
        } catch (JsonProcessingException e) {
            answerText = "Exception in JsonProcessing message";
        }

        long chatId = message.getChatId();
        var sendMessage = sendMessageFormat.getSendMessageBaseFormat(chatId);
        sendMessage.setText(answerText);
        return sendMessage;
    }

    @Override
    public String getHandlerCommand() {
        return "add";
    }
}
