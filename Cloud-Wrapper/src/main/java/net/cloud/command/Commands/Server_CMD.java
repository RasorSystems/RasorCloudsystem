package net.cloud.command.Commands;

import net.cloud.command.Command;
import net.cloud.gameserver.GameServerConfig;
import net.cloud.gameserver.GamesServer;
import net.cloud.service.ServiceHelper;
import net.cloud.utils.Logger;
import net.cloud.utils.LoggerType;

public class Server_CMD extends Command {
    public Server_CMD(String command) {
        super(command);
    }

    @Override
    public void onCommand(String[] args) {
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("info")){
                GamesServer gamesServer = new GamesServer(args[1]);
                if(gamesServer.isAlive()) {
                    new Logger().send("Servername " + args[1], LoggerType.INFO);
                    new Logger().send("MemoryUsagInPercent " + ServiceHelper.services.get(args[1]).getMemoryUsageInPercent(), LoggerType.INFO);
                    new Logger().send("Port " + new GameServerConfig(args[1]).getServerPort(), LoggerType.INFO);
                    new Logger().send("TotalMemory " + ServiceHelper.services.get(args[1]).getTotalMemory(), LoggerType.INFO);
                    new Logger().send("MemoryUsage " + ServiceHelper.services.get(args[1]).getMemoryUsage(), LoggerType.INFO);
                    new Logger().send("ServerType " + new GameServerConfig(args[1]).getServerType(), LoggerType.INFO);
                }else{
                    new Logger().send("Server is not online!", LoggerType.ERROR);
                }
            }else if(args[0].equalsIgnoreCase("start")){
                GamesServer gamesServer = new GamesServer(args[1]);
                if(!gamesServer.isAlive()) {
                    gamesServer.startServer();
                }else{
                    new Logger().send("Server is allready online on port " + new GameServerConfig(args[1]).getServerPort(), LoggerType.ERROR);
                }
            }else if(args[0].equalsIgnoreCase("stop")){
                GamesServer gamesServer = new GamesServer(args[1]);
                if(gamesServer.isAlive()) {
                    gamesServer.stopServer();
                }else{
                    new Logger().send("Server is not online!", LoggerType.ERROR);
                }
            }
        }
    }
}
