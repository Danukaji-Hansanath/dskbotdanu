package com.dskbot.main;

/*
*
* @Author: Danukaji Hansanath
* @GitHub:https://github.com/Danukaji-Hansanath
* @Project: DSKBot
* @Class: main
*
*/

import com.dskbot.bots.films.FilmThread;

public class main {
    public static void main(String[] args) {
        FilmThread filmThread = new FilmThread();
        filmThread.setPriority(10);
        filmThread.start();
    }
}
