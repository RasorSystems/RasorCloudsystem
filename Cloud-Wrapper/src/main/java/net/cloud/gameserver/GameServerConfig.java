package net.cloud.gameserver;

import com.google.gson.Gson;
import net.cloud.utils.Logger;
import net.cloud.utils.LoggerType;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class GameServerConfig {

    String SERVER_NAME;
    UUID SERVER_UUID = UUID.randomUUID();
    int SERVER_PORT;
    String SERVER_GROUP;
    int SERVER_MAXRam;
    int SERVER_MAXCpu;
    String SERVER_VERSION;
    GameServerTypes SERVER_TYPE;

    public GameServerConfig(String name,String version, String port, String group, String maxRamUsage, String maxCPUusage, String servercount, String gameServerTypes){
        this.SERVER_PORT = Integer.parseInt(port);
        this.SERVER_GROUP = group;
        this.SERVER_VERSION = version;
        this.SERVER_MAXCpu = Integer.parseInt(maxCPUusage);
        this.SERVER_MAXRam = Integer.parseInt(maxRamUsage);
        this.SERVER_NAME = name;
        this.SERVER_TYPE = GameServerTypes.valueOf(gameServerTypes);
    }

    public GameServerConfig(String name){
        this.SERVER_NAME = name;
    }

    public GameServerTypes getServerType() {
        Gson gson = new Gson();
        BufferedReader br = null;
        GameServerConfig employee = null;
        try {
            br = new BufferedReader(new FileReader("Serverconfigs//" + this.SERVER_NAME + ".json"));
            employee = (GameServerConfig) gson.fromJson(br, GameServerConfig.class);
        } catch (FileNotFoundException e) {
            new Logger().send("an error occurred while running, please contact an admin or developer!", LoggerType.ERROR);
        }
        return employee.SERVER_TYPE;
    }

    public int getServerPort() {
        Gson gson = new Gson();
        BufferedReader br = null;
        GameServerConfig employee = null;
        try {
            br = new BufferedReader(new FileReader("Serverconfigs//" + this.SERVER_NAME + ".json"));
            employee = (GameServerConfig) gson.fromJson(br, GameServerConfig.class);
        } catch (FileNotFoundException e) {
            new Logger().send("an error occurred while running, please contact an admin or developer!", LoggerType.ERROR);
        }
        return employee.SERVER_PORT;
    }

}
