package net.cloud.utils;

import org.fusesource.jansi.Ansi;

public class ColorUtils {

    public String RESET = "\u001B[0m", BLACK = "\u001B[30m", RED = (char) 27 + "[31m", GREEN = (char) 27 + "[32m", YELLOW = (char) 27 + "[33m", BLUE = (char) 27 + "[34m", MAGENTA = (char) 27 + "[35m", CYAN = (char) 27 + "[36m", WHITE = (char) 27 + "[30m", GRAY = (char) 27 + "[37m";

    public ColorUtils() {
        checkOS();
    }

    public void checkOS() {
        RESET = Ansi.ansi().reset().fg(Ansi.Color.DEFAULT).boldOff().toString();
        BLACK = "";
        RED = Ansi.ansi().reset().fg(Ansi.Color.RED).bold().toString();
        GREEN = Ansi.ansi().reset().fg(Ansi.Color.GREEN).boldOff().toString();
        YELLOW = Ansi.ansi().reset().fg(Ansi.Color.YELLOW).bold().toString();
        BLUE = Ansi.ansi().reset().fg(Ansi.Color.BLUE).bold().toString();
        MAGENTA = Ansi.ansi().reset().fg(Ansi.Color.MAGENTA).boldOff().toString();
        CYAN = Ansi.ansi().reset().fg(Ansi.Color.CYAN).boldOff().toString();
        WHITE = Ansi.ansi().reset().fg(Ansi.Color.WHITE).bold().toString();
        GRAY = Ansi.ansi().reset().fg(Ansi.Color.WHITE).boldOff().toString();
    }
}
