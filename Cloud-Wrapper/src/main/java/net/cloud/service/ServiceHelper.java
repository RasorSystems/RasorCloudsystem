package net.cloud.service;

import net.cloud.CloudWrapper;
import net.cloud.utils.Logger;
import net.cloud.utils.LoggerType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class ServiceHelper {

    public static volatile ConcurrentHashMap<String, Service> services = new ConcurrentHashMap<>();
    public static volatile HashMap<String, Thread> toggledScreens = new HashMap<>();
    public final ExecutorService service;
    public final ExecutorService server;

    public ServiceHelper() {
        this.service = Executors.newFixedThreadPool(10);
        this.server = Executors.newCachedThreadPool(createThreadFactory((thread, throwable) -> {
            if (thread != null && !thread.isInterrupted())
                thread.interrupt();
        }));
    }

    public void tryStart(String command, File location, Consumer<Process> processConsumer) {
        CloudWrapper.executorService.execute(() -> {
            if (!this.server.isTerminated() && command != null && location.exists() && location.isDirectory()) {
                this.server.submit(() -> {
                    try {
                        ProcessBuilder processBuilder = (new ProcessBuilder(command.split(" "))).directory(location);
                        Process process = processBuilder.start();
                        if (process.isAlive()) {
                            processConsumer.accept(process);
                        }
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                });
            }else{
                new Logger().send("error in terminal.", LoggerType.ERROR);
            }
        });
    }

    ThreadFactory createThreadFactory(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        AtomicLong atomicLong = new AtomicLong(0L);
        return runnable -> {
            Thread thread = threadFactory.newThread(runnable);
            thread.setName(String.format(Locale.ROOT, "PoolThread-%d", new Object[] { Long.valueOf(atomicLong.getAndIncrement()) }));
            thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            thread.setDaemon(true);
            return thread;
        };
    }
}
