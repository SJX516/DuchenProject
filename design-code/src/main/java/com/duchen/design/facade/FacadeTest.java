package com.duchen.design.facade;

public class FacadeTest implements Runnable {
    @Override
    public void run() {
        HomeTheaterFacade homeTheaterFacade = new HomeTheaterFacade();
        homeTheaterFacade.watchMovie("Star Wars");
        homeTheaterFacade.endMovie();
    }
}
