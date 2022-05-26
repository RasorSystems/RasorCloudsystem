package net.rasorcloud.cloud.utils;

import net.rasorcloud.cloud.RasorCloud;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger{
    public void send(String Message, LoggerType loggerType) {
        if(loggerType.equals(LoggerType.MESSAGE)) {
            RasorCloud.lineReader.printAbove(getColor().RESET + Message + getColor().RESET);
        }
        if(loggerType.equals(LoggerType.INFO)) {
            RasorCloud.lineReader.printAbove(getColor().GRAY + "[" + getColor().CYAN + new SimpleDateFormat("HH:mm:ss").format(new Date()) + getColor().GRAY + "] [" + getColor().BLUE + "INFO" + getColor().GRAY + "] " + getColor().RESET + getColor().BLUE + Message + getColor().RESET);
        }
        if(loggerType.equals(LoggerType.WARNING)) {
            RasorCloud.lineReader.printAbove(getColor().GRAY + "[" + getColor().CYAN + new SimpleDateFormat("HH:mm:ss").format(new Date()) + getColor().GRAY + "] [" + getColor().YELLOW + "Warning" + getColor().GRAY + "] " + getColor().YELLOW + getColor().BLUE + Message + getColor().RESET);
        }
        if(loggerType.equals(LoggerType.ERROR)) {
            RasorCloud.lineReader.printAbove(getColor().GRAY + "[" + getColor().CYAN + new SimpleDateFormat("HH:mm:ss").format(new Date()) + getColor().GRAY + "] [" + getColor().RED + "ERROR" + getColor().GRAY + "] " + getColor().RED + getColor().BLUE + Message + getColor().RESET);
        }
    }

    public ColorUtils getColor() {
        return new ColorUtils();
    }
}
