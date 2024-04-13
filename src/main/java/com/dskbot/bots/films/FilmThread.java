package com.dskbot.bots.films;

import com.dskbot.bots.films.TorrentFileGet.TorrentFileDownload;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class FilmThread extends Thread{
    public void run(){
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TorrentFileDownload());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

