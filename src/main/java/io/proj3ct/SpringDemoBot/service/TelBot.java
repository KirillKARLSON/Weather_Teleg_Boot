package io.proj3ct.SpringDemoBot.service;

import io.proj3ct.SpringDemoBot.config.BotConfig;
import io.proj3ct.SpringDemoBot.model.User;
import io.proj3ct.SpringDemoBot.model.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.Timestamp;

@Component
public class TelBot extends TelegramLongPollingBot {


    @Autowired
    private UserRepository userRepository;

    final BotConfig config;

    public TelBot(BotConfig config){
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    public static String baseUrl = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid=26c28da8bee39866fe7ad8f24ea09390&units=metric&lang=ru";


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText){
                case "/start":

                    startCommandRecieve(chatId, update.getMessage().getChat().getFirstName());
                    registerUser(update.getMessage());

                break;

                case "/help":
                    helpCommandRecieve(chatId, update.getMessage().getChat().getFirstName());
                break;

                case "/weather":
                    weatherCommandRecieve (chatId, update.getMessage().getChat().getFirstName());
                break;
                default:
                    String normalLink = linkBuilder(update.getMessage().getText());
                    WeatherJsonSender wjs = new WeatherJsonSender();

                    try {
                        String txt = wjs.cityInput(normalLink);
                        sendMessage(chatId, txt);
                    }catch(Exception e){
                        sendMessage(chatId, "В базе нет такого города");
                    }



            }
        }
    }

    private void registerUser(Message msg) {

        if(userRepository.findById(msg.getChatId()).isEmpty()){
            var chatId = msg.getChatId();
            var chat = msg.getChat();

            User user = new User();

            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));


            userRepository.save(user);
           // log.info("user saved:" + user);
        }

    }


    private void startCommandRecieve(long chatId, String name) throws TelegramApiException {

        String txt = "Привет,"+name+ ", это будущий чат-бот с погодой";
        sendMessage(chatId, txt);
    }

    private void weatherCommandRecieve(long chatId, String name) throws TelegramApiException, IOException {
        Update update = new Update();

        sendMessage(chatId, "Введите город");
        String city = update.getMessage().toString();

        sendMessage(chatId, city);
    }


    private void helpCommandRecieve(long chatId, String name) throws TelegramApiException{

        String txt = ""+name+", этот бот выдает погоду в крупных городах разных частей света." +
                " Город можно писать как на русском языке, так и на английском.";
        sendMessage(chatId, txt);
    }

    private void sendMessage(long chatId, String textToSend) throws TelegramApiException {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        execute(message);
    }

    public String linkBuilder(String city){
        String baseLink = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid=26c28da8bee39866fe7ad8f24ea09390&units=metric&lang=ru";
        return  baseUrl.replace("{city}", city);
    }




}
