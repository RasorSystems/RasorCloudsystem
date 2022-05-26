package net;

import net.cloud.CloudWrapper;

import java.io.IOException;

public class Start {
    public static CloudWrapper wrapperBootstrap = new CloudWrapper();

    public static void main(String[] args) {
        try {
            wrapperBootstrap.bootstrap();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
