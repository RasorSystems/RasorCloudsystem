package net.rasorcloud.cloud.wrapper;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import net.rasorcloud.cloud.networking.ServerHandler;
import net.rasorcloud.cloud.utils.GameServerTypes;

import java.nio.charset.StandardCharsets;

public class WrapperMessager {

    public ChannelHandlerContext ctx = ServerHandler.ctx;

    public void sendCreateServer(String name, String version, int port, String group, int maxRamUsage, int maxCPUusage, int maxPlayers, int servercount, GameServerTypes gameServerTypes){
        String msg = "create server;" + name + ";"+ version + ";"+ port + ";"+ group + ";"+ maxRamUsage + ";"+ maxCPUusage + ";"+ maxPlayers + ";"+ servercount + ";"+ gameServerTypes;
        ctx.writeAndFlush(Unpooled.copiedBuffer(msg, StandardCharsets.UTF_8));
    }

    public void sendStartServer(String server){
        ctx.writeAndFlush(Unpooled.copiedBuffer("start server " + server, StandardCharsets.UTF_8));
    }

    public void sendStartGroup(String group){
        ctx.writeAndFlush(Unpooled.copiedBuffer("start group " + group, StandardCharsets.UTF_8));
    }

    public void sendStopGroup(String group){
        ctx.writeAndFlush(Unpooled.copiedBuffer("stop group " + group, StandardCharsets.UTF_8));
    }

    public void sendStopServer(String server){
        ctx.writeAndFlush(Unpooled.copiedBuffer("stop server " + server, StandardCharsets.UTF_8));
    }
}
