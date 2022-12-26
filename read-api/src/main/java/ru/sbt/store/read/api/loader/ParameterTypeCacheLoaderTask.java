package ru.sbt.store.read.api.loader;

import com.google.common.cache.LoadingCache;
import ru.sbt.common.entities.ParameterTypeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;
import ru.sbt.store.read.api.client.CoreClient;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ParameterTypeCacheLoaderTask implements CacheLoaderTask {

    private final LoadingCache<Long, ParameterTypeDto> parameterTypeLoadingCache;
    private final CoreClient coreClient;

    @Value("${cache.loader.poll.interval.mins.parameter.type}")
    private Long pollIntervalMins;

    @Override
    public void run() {
        parameterTypeLoadingCache.invalidateAll();
        parameterTypeLoadingCache.asMap()
                .putAll(
                        coreClient.getAllParameterTypes()
                                .stream()
                                .collect(Collectors.toMap(ParameterTypeDto::getId, p -> p))
                );
        log.info("Reloaded ProductType Cache");
    }


    @Override
    public PeriodicTrigger getPeriodicTrigger() {
        return new PeriodicTrigger(pollIntervalMins, TimeUnit.MINUTES);
    }
}
