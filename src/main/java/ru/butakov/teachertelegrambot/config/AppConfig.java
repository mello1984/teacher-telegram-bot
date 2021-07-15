package ru.butakov.teachertelegrambot.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.butakov.teachertelegrambot.bot.TeacherBot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableConfigurationProperties({BotProps.class})
@PropertySources({
        @PropertySource("classpath:private.yml")
})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public TeacherBot teacherBot(
            BotProps botProps,
            @Value("${server.port}") String port,
            RestTemplate restTemplate
    ) {
        return botProps.isUse_self_signed_certificate() ?
                getBotWithSelfSignedCertificate(botProps, port) :
                getBotWithoutSelfSignedCertificate(botProps, restTemplate);
    }

    private TeacherBot getBotWithoutSelfSignedCertificate(BotProps botProps, RestTemplate restTemplate) {
        TeacherBot bot = new TeacherBot(botProps.getToken(), botProps.getName(), botProps.getWebHookPath());

        String url = String.format(botProps.getRegisterWebhookPath(), botProps.getToken(), botProps.getWebHookPath());
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        log.info("Create WebHookBot without self-signed certificate, telegram answer: {}", response);
        return bot;
    }

    private TeacherBot getBotWithSelfSignedCertificate(BotProps botProps, String port) {
        TeacherBot bot = new TeacherBot(botProps.getToken(), botProps.getName(), "/");
        try {
            log.info("Create WebHookTeseraBot, try register self-signed certificate");
            String url = botProps.getIp() + ":" + port;
            InputStream resource = new ClassPathResource(botProps.getCertificate()).getInputStream();
            InputFile certificateFile = new InputFile(resource, botProps.getCertificate());
            int maxConnections = 40;
            List<String> allowedUpdates = Collections.emptyList();
            Boolean dropPendingUpdates = null;
            SetWebhook webhook = new SetWebhook(url, certificateFile, maxConnections, allowedUpdates, botProps.getIp(), dropPendingUpdates);
            bot.setWebhook(webhook);
            log.info("Register webhook successful");
        } catch (TelegramApiException | IOException e) {
            log.warn("Exception on register bot", e);
        }
        return bot;
    }

}
