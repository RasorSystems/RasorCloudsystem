package net.cloud.command;

import net.cloud.CloudWrapper;
import net.cloud.command.Commands.Server_CMD;
import net.cloud.command.Commands.stop_cmd;
import net.cloud.command.Commands.test;
import net.cloud.utils.ColorUtils;
import net.cloud.utils.Logger;
import net.cloud.utils.LoggerType;
import org.jline.reader.UserInterruptException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CommandManager{

    private final Set<Command> commands;

    public static ArrayList<String> consoleLogs = new ArrayList<>();

    public CommandManager(){
        this.commands = new HashSet<Command>();
    }
    public static String lastInput;
    public static boolean RUN_CMD = true;

    public void run() {
        CloudWrapper.executorService.execute(() -> {
            loadCommands();
            String answer = null;
            while (true) {
                    try {
                        answer = CloudWrapper.lineReader.readLine(new ColorUtils().BLUE + "RasorCloud" + new ColorUtils().GRAY + " > " + new ColorUtils().RESET);
                        consoleLogs.add(answer);
                        lastInput = answer;
                        if (!answer.startsWith(" ")) {
                            String command = answer.split(" ")[0];
                            String[] args = new String[answer.split(" ").length - 1];
                            System.arraycopy(answer.split(" "), 1, args, 0, answer.split(" ").length - 1);
                            executeCommand(command, args);
                        }
                    } catch (UserInterruptException exception) {
                    }
            }

        });
    }

    public void loadCommands(){
        commands.add(new test("test"));
        commands.add(new stop_cmd("stop"));
        commands.add(new Server_CMD("server"));
    }

    public void executeCommand(String command, String[] args) {
            Command cmd = getCommand(command);
            if (cmd != null) {
                cmd.onCommand(args);
            } else {
                if (!command.equals("")) {
                    getLogger().send("Dieser Befehl existiert nicht!", LoggerType.INFO);
                }
            }
    }

    public Command getCommand(String name) {
        for (Command command : this.commands) {
            if (command.getCommand().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return null;
    }

    public Logger getLogger() {
        return new Logger();
    }

    public ColorUtils getColor() {
        return new ColorUtils();
    }

}
