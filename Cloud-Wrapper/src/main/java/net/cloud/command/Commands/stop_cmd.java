package net.cloud.command.Commands;

import net.cloud.command.Command;
import net.cloud.command.CommandManager;

public class stop_cmd extends Command {
    public stop_cmd(String command) {
        super(command);
    }

    @Override
    public void onCommand(String[] args) {
        System.exit(0);
    }
}
