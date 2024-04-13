package com.dskbot.bots.films.TorrentFileGet.DownloadFilms;

import com.frostwire.jlibtorrent.AlertListener;
import com.frostwire.jlibtorrent.LibTorrent;
import com.frostwire.jlibtorrent.SessionManager;
import com.frostwire.jlibtorrent.TorrentInfo;
import com.frostwire.jlibtorrent.alerts.AddTorrentAlert;
import com.frostwire.jlibtorrent.alerts.Alert;
import com.frostwire.jlibtorrent.alerts.AlertType;
import com.frostwire.jlibtorrent.alerts.BlockFinishedAlert;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.dskbot.utilities.Config;
import com.dskbot.utilities.Utilities;
import com.dskbot.bots.films.TorrentFileGet.TorrentFileDownload;


public class FilmDownlodFromTorrent {
    private static final TorrentFileDownload torrentFileDownload = new TorrentFileDownload();
    private static final String chatID = "-1001815210010";

    public void DownloadTorrent(String torFile) throws Throwable {
        // comment this line for a real application
        String[] args = new String[]{torFile};
        File torrentFile = new File(args[0]);
        System.out.println("Using libtorrent version: " + LibTorrent.version());
        final SessionManager s = new SessionManager();
        final CountDownLatch signal = new CountDownLatch(1);
        s.addListener(new AlertListener() {
            @Override
            public int[] types() {
                return null;
            }

            @Override
            public void alert(Alert<?> alert) {
                AlertType type = alert.type();

                switch (type) {
                    case ADD_TORRENT:
                        System.out.println("Torrent added");
                        ((AddTorrentAlert) alert).handle().resume();
                        torrentFileDownload.sendMessageToUser("Torrent added size : " + (s.stats().totalDownload()) / (1024.0 * 1024.0), chatID);
                        break;
                    case BLOCK_FINISHED:
                        BlockFinishedAlert a = (BlockFinishedAlert) alert;
                        int p = (int) (a.handle().status().progress() * 100);

                        String message = "Your File is Downloading " + p + "%";
                        String message2 = "Progress: " + p + " for torrent name: " + a.torrentName() + " and size: " + (s.stats().totalDownload()) / (1024.0 * 1024.0);
                        if (p == 1 || p == 50 || p == 98) {
                            System.out.println((s.stats().totalDownload()) / (1024 * 1024));
                            torrentFileDownload.sendMessageToUser(message + new Utilities().capLogo(), chatID);
                            torrentFileDownload.sendMessageToUser(message2+ new Utilities().capLogo(), chatID);
                        }


                        break;
                    case TORRENT_FINISHED:
                        Config config = new Config();
                        System.out.println("Torrent finished");
                        signal.countDown();
                        Utilities utils = new Utilities();
                        List<String> utStr = utils.getFiles(utils.getFolders(config.getProperty("telegram.tor.download.path")));
                        List<String> realFiles = utils.fileFilter(utStr);
                        String file = null;
                        torrentFileDownload.sendMessageToUser("Your File was Downloaded"+ new Utilities().capLogo(), chatID);
                        new Utilities().deleteFile(torFile);
                        torrentFileDownload.sendMessageToUser("Torrent file was deleted " + new Utilities().capLogo(), chatID);
                        for (String fl : realFiles) {
                            System.out.println(fl);
                        }
                }
            }
        });

        s.start();
        TorrentInfo ti = new TorrentInfo(torrentFile);
        s.download(ti, new File(new Config().getProperty("telegram.localhost.download.path").toString()));
        signal.await();
        s.stop();

    }
}
