package net.cloud.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import net.cloud.CloudWrapper;
import net.cloud.gameserver.GamesServer;
import net.cloud.utils.Logger;
import net.cloud.utils.LoggerType;
import org.mariadb.jdbc.internal.com.Packet;

import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;
import java.util.jar.Pack200;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        if ((ctx.channel().isActive() || ctx.channel().isOpen() || ctx.channel().isWritable())) {
            this.ctx = ctx;
            new Logger().send("Master is connected to Wrapper!", LoggerType.INFO);
        }
    }

    public void sendMessage(String msg){
        ctx.writeAndFlush(Unpooled.copiedBuffer(msg, StandardCharsets.UTF_8));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if ((!ctx.channel().isActive() || !ctx.channel().isOpen() || !ctx.channel().isWritable())) {
            new Logger().send("Master is disconnected from Wrapper!", LoggerType.ERROR);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf inBuffer = (ByteBuf) msg;
        String received = inBuffer.toString(CharsetUtil.UTF_8);
        new Logger().send("Server hat eine Nachricht gesendet -> " + received, LoggerType.INFO);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }
}