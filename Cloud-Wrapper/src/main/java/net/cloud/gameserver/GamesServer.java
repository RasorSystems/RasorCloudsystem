package net.cloud.gameserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.cloud.CloudWrapper;
import net.cloud.network.ClientHandler;
import net.cloud.service.Service;
import net.cloud.service.ServiceHelper;
import net.cloud.utils.ColorUtils;
import net.cloud.utils.DownloadManager;
import net.cloud.utils.Logger;
import net.cloud.utils.LoggerType;
import org.apache.logging.log4j.core.jmx.Server;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Wrapper;
import java.util.*;
import java.util.List;

public class GamesServer {
    String name;
    int port;
    String group;
    int maxRamUsage;
    int maxCPUusage;
    int maxPlayers;
    int MinPlayers = 0;
    int servercount;
    String version;
    GameServerTypes gameServerTypes;
    Path sourceDirectory;
    Path targetDirectory;
    Path sourceDirectory1;
    Path targetDirectory2;
    String path;
    public GamesServer(String name,String version, int port, String group, int maxRamUsage, int maxCPUusage, int maxPlayers, int servercount, GameServerTypes gameServerTypes) {
        this.port = port;
        this.group = group;
        this.version = version;
        this.maxCPUusage = maxCPUusage;
        this.maxRamUsage = maxRamUsage;
        this.maxPlayers = maxPlayers;
        this.servercount = servercount;
        this.name = name;
        this.gameServerTypes = gameServerTypes;
    }

    public GamesServer(String name,String version, String port, String group, String maxRamUsage, String maxCPUusage, String maxPlayers, String servercount, String gameServerTypes) {
        this.port = Integer.parseInt(port);
        this.group = group;
        this.version = version;
        this.maxCPUusage = Integer.parseInt(maxCPUusage);
        this.maxRamUsage = Integer.parseInt(maxRamUsage);
        this.maxPlayers = Integer.parseInt(maxPlayers);
        this.servercount = Integer.parseInt(servercount);
        this.name = name;
        this.gameServerTypes = GameServerTypes.valueOf(gameServerTypes);
    }

    public GamesServer(String name){
        this.name = name;
    }

    public String getServerPath(){
        GameServerConfig gameServerConfig = new GameServerConfig(name);
        if(gameServerConfig.getServerType().equals(GameServerTypes.TEMP)){
            return "temp//" + name + "//";
        }else if(gameServerConfig.getServerType().equals(GameServerTypes.STATIC)){
            return "static//" + name + "//";
        }else {
            return null;
        }
    }

    public String getServerPathFromGameState(){
        if(gameServerTypes.equals(GameServerTypes.TEMP)){
            return "temp//" + name + "//";
        }else if(gameServerTypes.equals(GameServerTypes.STATIC)){
            return "static//" + name + "//";
        }else {
            return null;
        }
    }


    public void createNewServer(){
        CloudWrapper.executorService.execute(() -> {
            if(!serverExists(name)){
                try {
                    try {
                        new Logger().send("Downloading Spigot version...", LoggerType.INFO);
                            System.out.println(getServerPathFromGameState());
                            new File(getServerPathFromGameState()).mkdirs();
                            DownloadManager.DownloadManager("https://cdn.getbukkit.org/craftbukkit/craftbukkit-" + version + "-R0.1-SNAPSHOT-latest.jar", getServerPathFromGameState() + "server.jar");
                            new Logger().send("create serverconfig...", LoggerType.INFO);
                            try (BufferedInputStream inputStream = new BufferedInputStream(new URL("https://pastebin.com/raw/r9UqCrEv").openStream());
                                 FileOutputStream fileOS = new FileOutputStream(new File("static//" + name + "//server.properties"))) {
                                byte data[] = new byte[1024];
                                int byteContent;
                                while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                                    fileOS.write(data, 0, byteContent);
                                }
                            } catch (IOException e) {
                                new Logger().send(e.getMessage(), LoggerType.ERROR);
                            }

                           try (BufferedInputStream inputStream = new BufferedInputStream(new URL("https://pastebin.com/raw/1VaxzYyP").openStream());
                            FileOutputStream fileOS = new FileOutputStream(new File("static//" + name + "//eula.txt"))) {
                                byte data[] = new byte[1024];
                                int byteContent;
                                while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                                    fileOS.write(data, 0, byteContent);
                                }
                            } catch (IOException e) {
                               new Logger().send(e.getMessage(), LoggerType.ERROR);
                            }

                    } catch (IOException e) {
                        new Logger().send(e.getMessage(), LoggerType.ERROR);
                    }

                    Writer writer = null;
                    writer = Files.newBufferedWriter(Paths.get("Serverconfigs//" + name + ".json"));
                    Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
                    GameServerConfig gameServerConfig = new GameServerConfig(name, version, "" + port, group, "" + maxRamUsage, "" + maxCPUusage, "" +maxPlayers, "" + servercount, "" + gameServerTypes);
                    gson.toJson(gameServerConfig, writer);
                    try {
                        writer.close();
                    } catch (IOException e) {
                        new Logger().send(e.getMessage(), LoggerType.ERROR);
                    }
                    createWrapperInformations();
                    try (OutputStream output = new FileOutputStream(getServerPath() + "server.properties")) {
                        Properties prop = new Properties();
                        prop.setProperty("server-port", String.valueOf(port));
                        prop.store(output, null);
                    } catch (IOException io) {
                        new Logger().send(io.getMessage(), LoggerType.ERROR);
                    }
                } catch (IOException e) {
                    new Logger().send(e.getMessage(), LoggerType.ERROR);
                }
                new Logger().send("Server setup finished", LoggerType.INFO);
                startServer();
            }else {
                new Logger().send("Server is allready exists!", LoggerType.INFO);
            }
        });
    }


    public void deleteServer(String servername){
        //stopServer
        new File(getServerPath()).delete();
    }


    public void startServer(){
        GameServerConfig gameServerConfig = new GameServerConfig(name);
        if(!name.startsWith("Proxy")) {
            if (!isPortAllreadyUsed()) {
                new ServiceHelper().tryStart("java -Dfile.encoding=UTF-8 -Dcom.mojang.eula.agree=true -Djline.terminal=jline.UnsupportedTerminal -Xmx1G -jar server.jar", new File(getServerPath()), process -> ServiceHelper.services.put(name, new Service(name, gameServerConfig.getServerPort(), process, true)));
                CloudWrapper.getActiveServer.put(name, gameServerConfig.getServerPort());
                new Logger().send(new ColorUtils().CYAN + "Server " + new ColorUtils().RED + name + new ColorUtils().CYAN + " is starting on Port " + new ColorUtils().GREEN + gameServerConfig.getServerPort(), LoggerType.INFO);
                new Logger().send("MemoryUsagInPercent " + ServiceHelper.services.get(name).getMemoryUsageInPercent(), LoggerType.INFO);
                new Logger().send("Port " + ServiceHelper.services.get(name).getPort(), LoggerType.INFO);
                new Logger().send("TotalMemory " + ServiceHelper.services.get(name).getTotalMemory(), LoggerType.INFO);
                new Logger().send("MemoryUsage " + ServiceHelper.services.get(name).getMemoryUsage(), LoggerType.INFO);
            }else {
                new Logger().send("serverport from " + name + " is allready used!", LoggerType.ERROR);
            }
        }
    }

    public boolean isPortAllreadyUsed(){
        ArrayList<Integer> ports = new ArrayList<>();
        CloudWrapper.getActiveServer.forEach((s, integer) -> {
            ports.add(new GameServerConfig(s).getServerPort());
        });
        if(ports.contains(new GameServerConfig(name).getServerPort())){
            return true;
        }else{
            return false;
        }
    }

    public static boolean serverExists(String server){
        return new File("Serverconfigs//" + server + ".json").exists();
    }

    public static String getProperty(String key, String server, String state) {

        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(state + "/" + server + "/server.properties");
            prop.load(input);
            return prop.getProperty(key);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    new Logger().send(e.getMessage(), LoggerType.ERROR);
                }
            }
        }
    }

    public boolean isAlive(){
        return new ServiceHelper().services.get(name).getProcess().isAlive();
    }

    public void stopServer(){
        //plugin message senden
        new ClientHandler().sendMessage("stop server " + name);
        new ServiceHelper().services.get(name).getProcess().destroy();
        new Logger().send(new ColorUtils().CYAN + "Server " + new ColorUtils().RED + name + new ColorUtils().CYAN + " is stopping...", LoggerType.INFO);
    }

    public static int getMaxram(String server){
        return 2;
    }

    public void createWrapperInformations() throws IOException {
        Writer writer = null;
        writer = Files.newBufferedWriter(Paths.get(getServerPath()+ "wrapper.json"));
        Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
        WrapperInformations wrapperInformations = new WrapperInformations(name, port, "1");
        gson.toJson(wrapperInformations, writer);
        try {
            writer.close();
        } catch (IOException e) {
            new Logger().send(e.getMessage(), LoggerType.ERROR);
        }
    }

}
