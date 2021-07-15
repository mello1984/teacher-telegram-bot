package ru.butakov.teachertelegrambot.bot.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface InputMessageHandler {
    SendMessage handle(Message message);
    String getHandlerCommand();
}
