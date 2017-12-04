package com.duchen.design.command.command.impl;

import com.duchen.design.command.command.Command;

public class ListCommand implements Command {

    Command[] mCommands;

    public ListCommand(Command[] commands) {
        mCommands = commands;
    }

    @Override
    public void execute() {
        for (int i = 0; i < mCommands.length; i++) {
            mCommands[i].execute();
        }
    }

    @Override
    public void undo() {
        for (int i = 0; i < mCommands.length; i++) {
            mCommands[i].undo();
        }
    }
}
