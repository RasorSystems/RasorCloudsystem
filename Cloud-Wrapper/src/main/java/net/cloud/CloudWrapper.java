package net.cloud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.cloud.command.CommandManager;
import net.cloud.gameserver.GameServerTypes;
import net.cloud.gameserver.GamesServer;
import net.cloud.network.Client;
import net.cloud.network.ClientHandler;
import net.cloud.utils.Logger;
import net.cloud.utils.LoggerType;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CloudWrapper {
    ///////////////////////////////////////////////////////////////////////////
    // Bootstrap the Wrapper TODOO: Cloud muss vor dem Wrapper starten
    ///////////////////////////////////////////////////////////////////////////
    public static LineReader lineReader;
    public static ExecutorService executorService = Executors.newFixedThreadPool(20);
    public final NetworkUtils networkUtils = new NetworkUtils();
    public final ClientHandler clientHandler = new ClientHandler();
    public HashMap<String, ExecutorService> runningThreads = new HashMap<>();
    public void bootstrap() throws InterruptedException, IOException {
        System.out.println(" ██████╗  █████╗ ███████╗ ██████╗ ██████╗  ██████╗██╗      ██████╗ ██╗   ██╗██████╗ ");
        System.out.println(" ██╔══██╗██╔══██╗██╔════╝██╔═══██╗██╔══██╗██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗");
        System.out.println(" ██████╔╝███████║███████╗██║   ██║██████╔╝██║     ██║     ██║   ██║██║   ██║██║  ██║");
        System.out.println(" ██╔══██╗██╔══██║╚════██║██║   ██║██╔══██╗██║     ██║     ██║   ██║██║   ██║██║  ██║");
        System.out.println(" ██║  ██║██║  ██║███████║╚██████╔╝██║  ██║╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝");
        System.out.println(" ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝");
        setupLogger();
        setupFiles();
        new CommandManager().run();
        new GamesServer("tewst").startServer();
        new Client().connect();
    }

    public void setupLogger(){
        try {
            Terminal terminal = TerminalBuilder.builder().dumb(true).encoding(StandardCharsets.UTF_8).jansi(true).build();
            lineReader = LineReaderBuilder.builder().terminal(terminal).build();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public HashMap<String, ExecutorService> getRunningThreads() {
        return runningThreads;
    }
    public void addRunningThread(String service){
        runningThreads.put(service, executorService);
    }
    public static HashMap<String, Integer> getActiveServer = new HashMap<>();
    public static LineReader getLineReader() {
        return lineReader;
    }

    public NetworkUtils getNetworkUtils() {
        return networkUtils;
    }

    public void setupFiles() throws IOException {
        File cloudfile = new File("Cloudconfigs/config.json");
        File serverfile = new File("Serverconfigs/server.json");
        File groupfile = new File("Serverconfigs/groups.json");

        if(!new File("Cloudconfigs/").exists()){
            new File("Cloudconfigs/").mkdir();
            new Logger().send("Cloudconfigsdirectory was succesfully created.", LoggerType.INFO);
            cloudfile.createNewFile();
            new Logger().send("Cloudconfiguration was succesfully created.", LoggerType.INFO);
        }

        if(!new File("Serverconfigs/").exists()){
            new File("Serverconfigs/").mkdir();
            new Logger().send("Serverconfigsdirectory was succesfully created.", LoggerType.INFO);
            serverfile.createNewFile();
            new Logger().send("Serverconfiguration was succesfully created.", LoggerType.INFO);
            groupfile.createNewFile();
            new Logger().send("Groupconfiguration was succesfully created.", LoggerType.INFO);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();


        }

        if(!new File("temp/").exists()){
            new File("temp/").mkdir();
            new Logger().send("Tempdirectory was succesfully created.", LoggerType.INFO);
        }

        if(!new File("templates/").exists()){
            new File("templates/").mkdir();
            new Logger().send("Templatesdirectory was succesfully created.", LoggerType.INFO);
        }

        if(!new File("modules/").exists()){
            new File("modules/").mkdir();
            new Logger().send("Moduledirectory was succesfully created.", LoggerType.INFO);
        }

        if(!new File("cache/").exists()){
            new File("cache/").mkdir();
            new Logger().send("Cachedirectory was succesfully created.", LoggerType.INFO);
        }

        if(!new File("logs/").exists()){
            new File("logs/").mkdir();
            new File("logs/crashreports").mkdir();
            new Logger().send("Logdirectory was succesfully created.", LoggerType.INFO);
        }

        if(!new File("static/").exists()){
            new File("static/").mkdir();
            new Logger().send("Static-serverdirectory was succesfully created.", LoggerType.INFO);
        }

    }

    public static HashMap<String, Integer> getGetActiveServer() {
        return getActiveServer;
    }
}
