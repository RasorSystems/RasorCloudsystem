package net.rasorcloud.cloud;

import net.rasorcloud.cloud.Command.CommandManager;
import net.rasorcloud.cloud.networking.Server;
import net.rasorcloud.cloud.utils.SetupManager;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RasorCloud {

    public boolean IS_WRAPPER_ONLINE = false;
    public SetupManager setupManager = new SetupManager();
    public static LineReader lineReader;
    public static ExecutorService executorService = Executors.newFixedThreadPool(40);



    ///////////////////////////////////////////////////////////////////////////
    // Server als letztes starten
    ///////////////////////////////////////////////////////////////////////////

    public void bootup() {
        System.out.println(" ██████╗  █████╗ ███████╗ ██████╗ ██████╗  ██████╗██╗      ██████╗ ██╗   ██╗██████╗ ");
        System.out.println(" ██╔══██╗██╔══██╗██╔════╝██╔═══██╗██╔══██╗██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗");
        System.out.println(" ██████╔╝███████║███████╗██║   ██║██████╔╝██║     ██║     ██║   ██║██║   ██║██║  ██║");
        System.out.println(" ██╔══██╗██╔══██║╚════██║██║   ██║██╔══██╗██║     ██║     ██║   ██║██║   ██║██║  ██║");
        System.out.println(" ██║  ██║██║  ██║███████║╚██████╔╝██║  ██║╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝");
        System.out.println(" ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝");

        setupLogger();
        if(setupManager.isSetuped()){
            new CommandManager().run();
            new Server().run();
        }else{
            setupManager.startSetup();
        }
    }

    public void setupLogger(){
            try {
                Terminal terminal = TerminalBuilder.builder().dumb(true).encoding(StandardCharsets.UTF_8).jansi(true).build();
                lineReader = LineReaderBuilder.builder().terminal(terminal).build();
            } catch(IOException exception) {
                exception.printStackTrace();
            }


    }
}
