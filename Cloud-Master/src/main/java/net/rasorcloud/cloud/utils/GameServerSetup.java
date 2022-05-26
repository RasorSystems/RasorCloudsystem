package net.rasorcloud.cloud.utils;

import net.rasorcloud.cloud.RasorCloud;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import org.bukkit.craftbukkit.libs.jline.console.UserInterruptException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameServerSetup {
    String name;
    String version;
    int port;
    String group;
    int maxRamUsage;
    int maxCPUusage;
    int maxPlayers;
    int servercount;
    String answer;
    GameServerTypes gameServerTypes;
    public ArrayList<String> questions = new ArrayList<>();
    int STEP = 0;
    public GameServerSetup() throws UnknownHostException {
        questions.add("Whats the Name of this server?");
        questions.add("Whats the Port of this server?");
        questions.add("Whats the ServerVersion of this server?");
        questions.add("How many Ram can use this server?");
        questions.add("How many CPU can use this server in procent?");
        questions.add("How many server do you need from this template?");
        questions.add("Is this a STATIC or TEMP server?");
    }
    public void start() throws IOException {
        RasorCloud.executorService.execute(() -> {
            String answer = null;
            while (true) {
                try {
                    answer = RasorCloud.lineReader.readLine( new ColorUtils().BLUE + "RasorCloud" + new ColorUtils().GRAY + " > " + new ColorUtils().RESET);
                    System.out.println(answer);
                    if(!answer.startsWith(" ")) {
                        String command = answer.split(" ")[0];
                        String[] args = new String[answer.split(" ").length - 1];
                        System.arraycopy(answer.split(" "), 1, args, 0, answer.split(" ").length - 1);;
                    }
                } catch(UserInterruptException exception) {}
            }

        });
    }
}
