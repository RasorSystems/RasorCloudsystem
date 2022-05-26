package utils;

import com.sun.management.OperatingSystemMXBean;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class NetworkUtils {
    public static ChannelGroup allClients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final char[] VALUES = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception ex) {
            return "127.0.0.1";
        }
    }

    public static ChannelGroup getAllClients() {
        return allClients;
    }

    public void addClient(ChannelHandlerContext channelHandlerContext){
        allClients.add(channelHandlerContext.channel());
    }

    public void removeClient(ChannelHandlerContext channelHandlerContext){
        allClients.remove(channelHandlerContext.channel());
    }

    public static double cpuUsage() {
        return ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getSystemCpuLoad() * 100;
    }

    public static double internalCpuUsage() {
        return ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getProcessCpuLoad() * 100;
    }

    public static long systemMemory() {
        return ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
    }

    public static OperatingSystemMXBean system() {
        return ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean());
    }

    public static void writeWrapperKey() {
        Random random = new Random();

        Path path = Paths.get("WRAPPER_KEY.rsc");
        if (!Files.exists(path)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (short i = 0; i < 4096; i++) {
                stringBuilder.append(VALUES[random.nextInt(VALUES.length)]);
            }

            try {
                Files.createFile(path);
                try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(Files.newOutputStream(path), StandardCharsets.UTF_8)) {
                    outputStreamWriter.write(stringBuilder.substring(0) + '\n');
                    outputStreamWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String readWrapperKey() {
        Path path = Paths.get("WRAPPER_KEY.rsc");
        if (!Files.exists(path)) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        try {
            for (String string : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                builder.append(string);
            }
            return builder.substring(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.substring(0);
    }

}
