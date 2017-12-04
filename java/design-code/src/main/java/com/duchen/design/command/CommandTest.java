package com.duchen.design.command;

import com.duchen.design.command.command.Command;
import com.duchen.design.command.command.impl.CeilingFanHighCommand;
import com.duchen.design.command.command.impl.CeilingFanLowCommand;
import com.duchen.design.command.command.impl.LightOffCommand;
import com.duchen.design.command.command.impl.LightOnCommand;
import com.duchen.design.command.command.impl.ListCommand;
import com.duchen.design.command.receiver.CeilingFan;
import com.duchen.design.command.receiver.Light;

public class CommandTest implements Runnable {
    @Override
    public void run() {
        RemoteControl remoteControl = new RemoteControl();
        Light light = new Light();
        LightOnCommand lightOnCommand = new LightOnCommand(light);
        LightOffCommand lightOffCommand = new LightOffCommand(light);
        remoteControl.setCommand(0, lightOnCommand, lightOffCommand);

        CeilingFan fan = new CeilingFan();
        CeilingFanHighCommand highCommand = new CeilingFanHighCommand(fan);
        CeilingFanLowCommand lowCommand = new CeilingFanLowCommand(fan);
        remoteControl.setCommand(1, highCommand, lowCommand);
        System.out.println(remoteControl);

        Command[] onCommands = {lightOnCommand, highCommand};
        Command[] offCommands = {lightOffCommand, lowCommand};
        ListCommand openAllCommand = new ListCommand(onCommands);
        ListCommand closeAllCommand = new ListCommand(offCommands);

        remoteControl.setCommand(2, openAllCommand, closeAllCommand);

        remoteControl.onButtonPushed(2);
        remoteControl.offButtonPushed(2);
        remoteControl.undoButtonPushed();

        remoteControl.onButtonPushed(0);
        remoteControl.offButtonPushed(0);
        remoteControl.undoButtonPushed();
    }
}
