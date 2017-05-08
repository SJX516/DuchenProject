package com.duchen.design.facade;

import com.duchen.design.facade.subSystem.Projector;
import com.duchen.design.facade.subSystem.Screen;
import com.duchen.design.facade.subSystem.DvdPlayer;

public class HomeTheaterFacade {

    Projector mProjector;
    Screen mScreen;
    DvdPlayer mDvdPlayer;

    public HomeTheaterFacade() {
        mProjector = new Projector();
        mScreen = new Screen();
        mDvdPlayer = new DvdPlayer();
    }

    public void watchMovie(String movie) {
        System.out.println("Get ready to watch a movie...");
        mScreen.down();
        mProjector.on();
        mProjector.wideScreenMode();
        mDvdPlayer.on();
        mDvdPlayer.play(movie);
    }

    public void endMovie() {
        System.out.println("Shutting movie theater down...");
        mScreen.up();
        mProjector.off();
        mDvdPlayer.stop();
        mDvdPlayer.off();
    }
}
