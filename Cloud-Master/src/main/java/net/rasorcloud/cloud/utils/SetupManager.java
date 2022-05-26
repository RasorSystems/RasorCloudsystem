package net.rasorcloud.cloud.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.GsonBuildConfig;
import com.google.gson.stream.JsonWriter;
import jdk.nashorn.internal.parser.JSONParser;
import net.rasorcloud.cloud.cloudconfig.CloudConfig;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SetupManager {

    public int STEP = 0;
    public List<String> questions = new ArrayList<>();
    public String IP_ADRESS;
    public int PORT;
    public boolean USE_DEFAULT_PROXY;
    public boolean USE_DEFAULT_LOBBY;
    public boolean IS_SETUPED = false;
    Scanner scanner;

    public SetupManager(){
        questions.add("What is your server ip adress?");
        questions.add("Witch port do you want to use?");
        questions.add("Do you want to create a default proxy?");
        questions.add("Do you want to create a default Lobby?");
    }

   public boolean isSetuped(){
       if(new File("configs//config.json").exists()){
           return true;
       }else{
           return false;
       }
   }

    public void startSetup(){
        scanner = new Scanner(System.in);
        switch (STEP) {
            case 0:
                try {
                    new Logger().send(questions.get(STEP),  LoggerType.INFO);
                    IP_ADRESS = scanner.next();
                    STEP++;
                    startSetup();
                } catch (Exception e) {
                    new Logger().send("Wrong answare!",  LoggerType.ERROR);
                    startSetup();
                }
                break;
            case 1:
                try {
                    new Logger().send(questions.get(STEP),  LoggerType.INFO);
                    PORT = scanner.nextInt();
                    STEP++;
                    startSetup();
                } catch (Exception e) {
                    new Logger().send("Wrong answare!",  LoggerType.ERROR);
                    startSetup();
                }
                break;
            case 2:
                try {
                    new Logger().send(questions.get(STEP),  LoggerType.INFO);
                    USE_DEFAULT_PROXY = scanner.nextBoolean();
                    STEP++;
                    startSetup();
                } catch (Exception e) {
                    new Logger().send("Wrong answare!",  LoggerType.ERROR);
                    startSetup();
                }
                break;
            case 3:
                try {
                    new Logger().send(questions.get(STEP),  LoggerType.INFO);
                    USE_DEFAULT_LOBBY = scanner.nextBoolean();
                    STEP++;
                    startSetup();
                } catch (Exception e) {
                    new Logger().send("Wrong answare!",  LoggerType.ERROR);
                    startSetup();
                }
                break;
            case 4:
                try {
                    Writer writer = null;
                    new File("configs//").mkdirs();
                    writer = Files.newBufferedWriter(Paths.get("configs//config.json"));
                    CloudConfig cloudConfig = new CloudConfig(PORT, IP_ADRESS, USE_DEFAULT_PROXY, USE_DEFAULT_LOBBY);
                    Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
                    gson.toJson(new CloudConfig(PORT, IP_ADRESS, USE_DEFAULT_PROXY, USE_DEFAULT_LOBBY), writer);
                    writer.close();
                    scanner.close();
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
