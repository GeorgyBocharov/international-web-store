package ru.sbt.store.read.api.loader;

import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

@Service
public interface CacheLoaderTask extends Runnable {

    PeriodicTrigger getPeriodicTrigger();
    void run();
}
