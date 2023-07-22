package io.proj3ct.SpringDemoBot.config;


import io.proj3ct.SpringDemoBot.service.TelBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInit {

    @Autowired
    TelBot telBot;


    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try{
            telegramBotsApi.registerBot(telBot);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }

    }
}
