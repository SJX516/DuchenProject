package com.duchen.design.command.command.impl;

import com.duchen.design.command.command.Command;
import com.duchen.design.command.receiver.Light;

public class LightOffCommand implements Command {

    private Light mLight;

    public LightOffCommand(Light light) {
        mLight = light;
    }

    @Override
    public void execute() {
        mLight.off();
        System.out.println(mLight.toString());
    }

    @Override
    public void undo() {
        mLight.on();
        System.out.println(mLight.toString());
    }
}
