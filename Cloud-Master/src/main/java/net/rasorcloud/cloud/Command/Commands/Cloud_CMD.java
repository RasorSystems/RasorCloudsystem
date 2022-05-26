package net.rasorcloud.cloud.Command.Commands;

import net.rasorcloud.cloud.Command.Command;
import net.rasorcloud.cloud.networking.Server;
import net.rasorcloud.cloud.networking.ServerHandler;
import net.rasorcloud.cloud.wrapper.WrapperMessager;

public class Cloud_CMD extends Command {
    public Cloud_CMD(String command) {
        super(command);
    }

    @Override
    public void onCommand(String[] args) {
        if(args.length == 0){
            //help
        }else if(args.length == 1){

        }else if(args.length == 3){
            if(args[0].equalsIgnoreCase("start")){
                if(args[1].equalsIgnoreCase("server")){

                }
            }
        }
    }
}
