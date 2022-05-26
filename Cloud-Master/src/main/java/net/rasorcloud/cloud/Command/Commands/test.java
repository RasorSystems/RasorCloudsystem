package net.rasorcloud.cloud.Command.Commands;

import io.netty.buffer.Unpooled;
import net.rasorcloud.cloud.Command.Command;
import net.rasorcloud.cloud.networking.ServerHandler;
import net.rasorcloud.cloud.utils.GameServerTypes;
import net.rasorcloud.cloud.wrapper.WrapperMessager;

import java.nio.charset.StandardCharsets;

public class test extends Command {


    public test(String command) {
        super(command);
    }

    @Override
    public void onCommand(String[] args) {
        WrapperMessager wrapperMessager = new WrapperMessager();
        wrapperMessager.sendCreateServer("tewst", "1.8.8", 22334, "test", 1024, 10, 200, 1, GameServerTypes.STATIC);
    }
}
