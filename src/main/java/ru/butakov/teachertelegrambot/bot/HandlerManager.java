package ru.butakov.teachertelegrambot.bot;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.butakov.teachertelegrambot.bot.handlers.InputMessageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HandlerManager {
    Map<String, InputMessageHandler> handlersMap = new HashMap<>();

    public HandlerManager(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> handlersMap.put(handler.getHandlerCommand(), handler));
    }

    public SendMessage handleInputMessage(Message message) {
        Pattern answerPattern = Pattern.compile("/[0-9]+");
        Matcher answerMatcher = answerPattern.matcher(message.getText());

        String command = "unknown";
        if (message.getText().equals("next")) command = "next";
        if (answerMatcher.matches()) command = "answer";
        if (message.getText().startsWith("/add ")) command = "add";
        return handlersMap.get(command).handle(message);
    }
}
