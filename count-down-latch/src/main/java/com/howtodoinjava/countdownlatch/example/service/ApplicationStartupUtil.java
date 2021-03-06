package com.howtodoinjava.countdownlatch.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.howtodoinjava.countdownlatch.example.service.verifier.BaseHealthChecker;
import com.howtodoinjava.countdownlatch.example.service.verifier.CacheHealthChecker;
import com.howtodoinjava.countdownlatch.example.service.verifier.DatabaseHealthChecker;
import com.howtodoinjava.countdownlatch.example.service.verifier.NetworkHealthChecker;

/**
 * This clas is main startup class which initilizes the latch and wait of this latch till all services are checked.
 */
public class ApplicationStartupUtil {
    private static List<BaseHealthChecker> _services;
    private static CountDownLatch _latch;

    private ApplicationStartupUtil() {
    }

    private final static ApplicationStartupUtil INSTANCE = new ApplicationStartupUtil();

    public static ApplicationStartupUtil getInstance() {
        return INSTANCE;
    }

    public static boolean checkExternalServices() throws Exception {
        // Initialize the latch with number of service checkers
        _latch = new CountDownLatch(3);

        //All add checker in lists
        _services = new ArrayList<BaseHealthChecker>();
        _services.add(new NetworkHealthChecker(_latch));
        _services.add(new CacheHealthChecker(_latch));
        _services.add(new DatabaseHealthChecker(_latch));

        //Start service checkers using executor framework
        Executor executor = Executors.newFixedThreadPool(_services.size());

        for (final BaseHealthChecker v : _services) {
            executor.execute(v);
        }

        //Now wait till all services are checked
        _latch.await();

        //Services are available and now proceed startup
        for (final BaseHealthChecker v : _services) {
            if (!v.isServiceUp()) {
                return false;
            }
        }
        return true;
    }
}
