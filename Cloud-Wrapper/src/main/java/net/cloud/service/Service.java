package net.cloud.service;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.lang.reflect.Field;

public class Service {

    private String group;
    private int Port;
    private Process process;
    private boolean defaultServer;

    public Service(String group, int port, Process process, boolean defaultServer){
        this.group = group;
        this.process = process;
        this.defaultServer = defaultServer;
    }

    public String getGroup() {
        return group;
    }

    public String getTemplateName(){
        return group.split("-")[group.split("-").length -2];
    }

    public int getPort() {
        return Port;
    }

    public Process getProcess() {
        return process;
    }

    public boolean isDefaultServer() {
        return defaultServer;
    }

    public String getTotalMemory(){

        SystemInfo systemInfo = new SystemInfo();

        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();

        OSProcess osProcess = operatingSystem.getProcess((int) getPid());

        return convertSize(osProcess.getVirtualSize());
    }


    public String getMemoryUsage() {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        OSProcess process = operatingSystem.getProcess((int) getPid());
        return convertSize(process.getResidentSetSize());
    }


    public String getMemoryUsageInPercent() {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        OSProcess process = operatingSystem.getProcess((int) getPid());
        float percent = process.getResidentSetSize() * 100f / process.getVirtualSize();
        try {
            return Integer.parseInt(String.valueOf(percent)) + "%";
        } catch(NumberFormatException exception) {
            if(String.valueOf(percent).contains(".")) {
                String[] splitter = String.valueOf(percent).split("\\.");
                String[] splitter2 = splitter[1].split("");
                if(splitter2.length > 1) {
                    return splitter[0] + "," + splitter2[0] + "%";
                } else {
                    return percent + "%";
                }
            } else {
                return percent + "%";
            }
        }
    }

//diese converter
    public static String convertSize(long size) {
        String finalSize = "";
        long gigabyte = (1024 * 1024) * 1024;
        long terabyte = gigabyte * 1024;
        double gb = (((double) size / 1024) / 1024) / 1024;
        if(size < 1024) {
            finalSize = size + " Bytes";
        } else if(size >= 1024 && size < (1024 * 1024)) {
            finalSize =  String.format("%.2f", (double) size / 1024) + " KB";
        } else if(size >= (1024 * 1024) && size < ((1024 * 1024) * 1024)) {
            finalSize = String.format("%.2f", ((double) size / 1024) / 1024) + " MB";
        } else if(size >= gigabyte && size < terabyte) {
            finalSize = String.format("%.2f", (((double) size / 1024) / 1024) / 1024) + " GB";
        } else if(size >= terabyte) {
            finalSize = String.format("%.2f", gb / 1024) + " TB";
        }
        return finalSize;
    }

    public long getPid() {
        long result = -1;
        try {
            if (getProcess().getClass().getName().equals("java.lang.Win32Process") || getProcess().getClass().getName().equals("java.lang.ProcessImpl")) {
                Field f = getProcess().getClass().getDeclaredField("handle");
                f.setAccessible(true);
                long handl = f.getLong(getProcess());
                Kernel32 kernel = Kernel32.INSTANCE;
                WinNT.HANDLE hand = new WinNT.HANDLE();
                hand.setPointer(Pointer.createConstant(handl));
                result = kernel.GetProcessId(hand);
                f.setAccessible(false);
            } else if (getProcess().getClass().getName().equals("java.lang.UNIXProcess")) {
                Field f = getProcess().getClass().getDeclaredField("pid");
                f.setAccessible(true);
                result = f.getLong(getProcess());
                f.setAccessible(false);
            }
        } catch(Exception ex) {
            result = -1;
        }
        return result;
    }

}
