package com.duchen.design.command.command.impl;

import com.duchen.design.command.command.Command;
import com.duchen.design.command.receiver.Light;

public class LightOnCommand implements Command {

    private Light mLight;

    public LightOnCommand(Light light) {
        mLight = light;
    }

    @Override
    public void execute() {
        mLight.on();
        System.out.println(mLight.toString());
    }

    @Override
    public void undo() {
        mLight.off();
        System.out.println(mLight.toString());
    }
}
