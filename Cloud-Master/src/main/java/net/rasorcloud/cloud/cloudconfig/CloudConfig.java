package net.rasorcloud.cloud.cloudconfig;

import com.google.gson.Gson;
import net.rasorcloud.cloud.utils.Logger;
import net.rasorcloud.cloud.utils.LoggerType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class CloudConfig {

    public int PORT;
    public String IP_ADRESS;
    public boolean DEFAULT_PROXY;
    public boolean DEFAULT_LOBBY;
    public boolean USE_MOTD;
    public String MOTD_1;
    public String MOTD_2;
    public boolean USE_TAB;
    public List<String> TAB_1;
    public List<String> TAB_2;

    public CloudConfig(int port, String ip, boolean usedefaultProxy, boolean usedefaultLobby){
        this.PORT = port;
        this.IP_ADRESS = ip;
        this.DEFAULT_LOBBY = usedefaultLobby;
        this.DEFAULT_PROXY = usedefaultProxy;
        this.MOTD_1 = "This is a default RasorCloud MOTD";
        this.MOTD_2 = "https://rasorcloud.de/download";
        this.TAB_1 = Arrays.asList("this is a default headder");
        this.TAB_2 = Arrays.asList("this is a default Footer");
        this.USE_MOTD = true;
        this.USE_TAB = true;
    }

    public CloudConfig(){

    }

    public static int PORT() {
        Gson gson = new Gson();
        BufferedReader br = null;
        CloudConfig employee = null;
        try {
            br = new BufferedReader(new FileReader("configs//config.json"));
            employee = (CloudConfig) gson.fromJson(br, CloudConfig.class);
        } catch (FileNotFoundException e) {
            new Logger().send("an error occurred while running, please contact an admin or developer!", LoggerType.ERROR);
        }
        return employee.PORT;
    }

    public static String IP() {
        Gson gson = new Gson();
        BufferedReader br = null;
        CloudConfig employee = null;
        try {
            br = new BufferedReader(new FileReader("configs//config.json"));
            employee = (CloudConfig) gson.fromJson(br, CloudConfig.class);
        } catch (FileNotFoundException e) {
            new Logger().send("an error occurred while running, please contact an admin or developer!", LoggerType.ERROR);
        }
        return employee.IP_ADRESS;
    }
}
