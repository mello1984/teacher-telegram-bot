package ru.butakov.teachertelegrambot.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bot", ignoreInvalidFields = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class BotProps {
    String name;
    String token;
    String webHookPath;
    String ip;
    String certificate;
    boolean use_self_signed_certificate;
    String registerWebhookPath;

}
