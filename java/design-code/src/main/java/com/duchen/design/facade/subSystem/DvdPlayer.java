package com.duchen.design.facade.subSystem;

public class DvdPlayer {

    public void on() {
        System.out.println("DvdPlayer on");
    }

    public void play(String movie) {
        System.out.println("DvdPlayer play [" + movie + "]");
    }

    public void stop() {
        System.out.println("DvdPlayer stop");
    }

    public void off() {
        System.out.println("DvdPlayer off");
    }
}
