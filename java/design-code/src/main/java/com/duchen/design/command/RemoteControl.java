package com.duchen.design.command;

import com.duchen.design.command.command.Command;
import com.duchen.design.command.command.impl.NoCommand;

public class RemoteControl {

    static int COMMAND_SIZE = 7;

    Command[] mOnCommands;
    Command[] mOffCommands;
    Command mLastCommand;

    public RemoteControl() {
        mOnCommands = new Command[COMMAND_SIZE];
        mOffCommands = new Command[COMMAND_SIZE];

        Command noCommand = new NoCommand();
        for (int i = 0; i < COMMAND_SIZE; i++) {
            mOnCommands[i] = noCommand;
            mOffCommands[i] = noCommand;
        }
        mLastCommand = noCommand;
    }

    public void setCommand(int index, Command onCommand, Command offCommand) {
        mOnCommands[index] = onCommand;
        mOffCommands[index] = offCommand;
    }

    public void onButtonPushed(int index) {
        mOnCommands[index].execute();
        mLastCommand = mOnCommands[index];
    }

    public void offButtonPushed(int index) {
        mOffCommands[index].execute();
        mLastCommand = mOffCommands[index];
    }

    public void undoButtonPushed() {
        mLastCommand.undo();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("\n----- Remote Control -----\n");
        for (int i = 0; i < COMMAND_SIZE; i++) {
            string.append("[slot " + i + "] " + mOnCommands[i].getClass().getSimpleName() + "     "
                    + mOffCommands[i].getClass().getSimpleName() + "\n");
        }
        return string.toString();
    }
}
