package net.cloud.network.packet;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;

import java.io.IOException;

public interface Packet {

    default void read(final ByteBufOutputStream bufOutputStream) throws IOException{}
    default void write(final ByteBufOutputStream bufOutputStream) throws IOException{}
}
