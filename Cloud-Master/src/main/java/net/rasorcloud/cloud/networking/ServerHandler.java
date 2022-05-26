package net.rasorcloud.cloud.networking;

import io.netty.buffer.ByteBuf;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.util.CharsetUtil;
import net.rasorcloud.cloud.RasorCloud;
import net.rasorcloud.cloud.utils.Logger;
import net.rasorcloud.cloud.utils.LoggerType;
import net.rasorcloud.cloud.wrapper.WrapperMessager;
import utils.NetworkUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@ChannelHandler.Sharable
public class ServerHandler extends ByteArrayDecoder {
    public static ChannelHandlerContext ctx;

    NetworkUtils networkUtils = new NetworkUtils();
    public static HashMap<String, ChannelHandlerContext> getChannels = new HashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf inBuffer = (ByteBuf) msg;
        String received = inBuffer.toString(CharsetUtil.UTF_8);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        if ((ctx.channel().isActive() || ctx.channel().isOpen() || ctx.channel().isWritable())) {
            this.ctx = ctx;
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if ((!ctx.channel().isActive() || !ctx.channel().isOpen() || !ctx.channel().isWritable())) {
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }

}