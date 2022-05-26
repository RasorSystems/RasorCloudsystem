package net.rasorcloud.cloud.Command;

import net.rasorcloud.cloud.Command.Commands.Cloud_CMD;
import net.rasorcloud.cloud.Command.Commands.stop_cmd;
import net.rasorcloud.cloud.Command.Commands.test;
import net.rasorcloud.cloud.RasorCloud;
import net.rasorcloud.cloud.utils.ColorUtils;
import net.rasorcloud.cloud.utils.Logger;
import net.rasorcloud.cloud.utils.LoggerType;
import org.jline.reader.UserInterruptException;

import java.util.HashSet;
import java.util.Set;

public class CommandManager{

    private final Set<Command> commands;

    public CommandManager(){
        this.commands = new HashSet<Command>();
    }

    public void run() {
        RasorCloud.executorService.execute(() -> {
            loadCommands();
            String answer = null;
            while (true) {
                try {
                    answer = RasorCloud.lineReader.readLine( new ColorUtils().BLUE + "RasorCloud" + new ColorUtils().GRAY + " > " + new ColorUtils().RESET);
                    System.out.println(answer);
                    if(!answer.startsWith(" ")) {
                        String command = answer.split(" ")[0];
                        String[] args = new String[answer.split(" ").length - 1];
                        System.arraycopy(answer.split(" "), 1, args, 0, answer.split(" ").length - 1);
                        executeCommand(command, args);
                    }
                } catch(UserInterruptException exception) {}
            }

        });
    }

    public void loadCommands(){
        commands.add(new test("test"));
        commands.add(new stop_cmd("stop"));
        commands.add(new Cloud_CMD("cloud"));
    }

    public void executeCommand(String command, String[] args) {
        Command cmd = getCommand(command);
        if (cmd != null) {
            cmd.onCommand(args);
        } else {
            if(!command.equals("")) {
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
