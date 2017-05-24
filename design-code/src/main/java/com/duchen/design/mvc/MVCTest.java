package com.duchen.design.mvc;

public class MVCTest implements Runnable {

    @Override
    public void run() {
//        HeartModel heartModel = new HeartModel();
//        ControllerInterface model = new HeartController(heartModel);

        BeatModel beatModel = new BeatModel();
        ControllerInterface model = new BeatController(beatModel);
    }
}
