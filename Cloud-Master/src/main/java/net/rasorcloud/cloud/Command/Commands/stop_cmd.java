package net.rasorcloud.cloud.Command.Commands;

import net.rasorcloud.cloud.Command.Command;

public class stop_cmd extends Command {
    public stop_cmd(String command) {
        super(command);
    }

    @Override
    public void onCommand(String[] args) {
        System.exit(0);
    }
}
