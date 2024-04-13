package com.dskbot.bots.films.TorrentFileGet;

import com.dskbot.utilities.Config;
import com.dskbot.utilities.Utilities;
import com.dskbot.bots.films.TorrentFileGet.DownloadFilms.FilmDownlodFromTorrent;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class TorrentFileDownload extends TelegramLongPollingBot {
    public static String chat_Id;
    //configuration class static object
    private static final Config config = new Config();

    //utilities class static object
    private static final Utilities utilities = new Utilities();

    //torrent file path
    private static final String TORRENT_FILE_PATH = config.getProperty("telegram.tor.download.path");

    @Override
    public String getBotUsername() {
        return config.getProperty("telegram.tor.bot.username");
    }

    @Override
    public String getBotToken() {
        return config.getProperty("telegram.tor.bot.token");
    }

    @Override
    public void onUpdateReceived(Update update) {
        String usId = update.getMessage().getFrom().getId().toString();
        List<String> users = config.getArrayProperty("superusers");
        System.out.println(update.getMessage().getChatId());
        //check the user is authorized to use the bot
        if(users.contains(usId)){
            //check the message is text or document
            if(update.getMessage().hasText()){
                sendMessageToUser("You are authorized to use this bot"+ utilities.capLogo() ,chat_Id = update.getMessage().getChatId().toString() );
                System.out.println(update.getMessage().getDocument());
            }else if(update.getMessage().hasDocument()){
                //get the file id and file name
                Document document = update.getMessage().getDocument();
                String fileId_1 = document.getFileId();
                try{

                    //get the file from the telegram
                    GetFile getFile = new GetFile();
                    getFile.setFileId(fileId_1);
                    File file = execute(getFile);
                    if(file != null && file.getFilePath().endsWith(".torrent")){
                        //download the file
                        downloadFile1(file.getFilePath(),document.getFileName(),update.getMessage().getChatId().toString());
                    }else{
                        sendMessageToUser("Please send the torrent file only" + utilities.capLogo(), update.getMessage().getChatId().toString());
                    }
                    //delete the file from the telegram
                }catch (TelegramApiException e){
                    //
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }else{
            //send message to the user
            sendMessageToUser("You are not authorized to use this bot " + utilities.capLogo(), update.getMessage().getChatId().toString());
        }
    }
    //send message to the user
    public boolean sendMessageToUser(String message, String chatId) {
        boolean isSent = false;
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
            isSent = true;
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return isSent;
    }


    //download the file
    public void downloadFile1(String filePath,String fileName, String chat_id) throws TelegramApiException, IOException {
        System.out.println("1");
        //get the file from the telegram
        String fileUrl = "https://api.telegram.org/file/bot" + getBotToken()+"/" + filePath;

        //download the file
        String torFile =TORRENT_FILE_PATH +"/"+fileName;
        System.out.println(torFile);
        //copy the file
        try (InputStream inputStream = new URL(fileUrl).openStream();
             FileOutputStream outputStream = new FileOutputStream(torFile)) {
            System.out.println("2");
            //copy the file
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            //send message to the user
            String message = "bitTorrent file Downloaded" + new Utilities().capLogo();
            new TorrentFileDownload().
            sendMessageToUser(message, chat_id);
            outputStream.close();
            FilmDownlodFromTorrent filmDownlodFromTorrent = new FilmDownlodFromTorrent();
            filmDownlodFromTorrent.DownloadTorrent(torFile);
            //change the file name
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
