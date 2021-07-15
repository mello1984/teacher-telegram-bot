package ru.butakov.teachertelegrambot.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.butakov.teachertelegrambot.bot.HandlerManager;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BotController {
    HandlerManager handlerManager;

    @RequestMapping(value = {"/", "/callback/"}, method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        System.out.println("Update getted");
        System.out.println(update.getMessage().getText());
        return handlerManager.handleInputMessage(update.getMessage());
    }
}
