package net.rasorcloud.cloud.Command;

import net.rasorcloud.cloud.utils.ColorUtils;
import net.rasorcloud.cloud.utils.Logger;
import net.rasorcloud.cloud.utils.LoggerType;

public class Command {

    public String command;

    public Command(String command) {
        this.command = command;
    }

    public void onCommand(String[] args) {}
    public String getCommand() {
        return this.command;
    }
    public final Logger getLogger() {
        return new Logger();
    }
    public final ColorUtils getColor() {
        return new ColorUtils();
    }
    public final void sendMessage(String message) {
        getLogger().send(message, LoggerType.MESSAGE);
    }
    public final void sendInfoMessage(String message) {
        getLogger().send(message, LoggerType.INFO);
    }
    public final void sendWarningMessage(String message) {
        getLogger().send(message, LoggerType.WARNING);
    }
    public final void sendErrorMessage(String message) {
        getLogger().send(message, LoggerType.ERROR);
    }

}
