package ru.sbt.store.read.api.loader;

import com.google.common.cache.LoadingCache;
import ru.sbt.common.entities.LanguageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;
import ru.sbt.store.read.api.client.CoreClient;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class LanguageCacheLoaderTask implements CacheLoaderTask {

    @Value("${cache.loader.poll.interval.mins.language}")
    private Long pollIntervalMins;

    private final LoadingCache<Long, LanguageDto> languageLoadingCache;
    private final CoreClient coreClient;

    @Override
    public void run() {
        languageLoadingCache.invalidateAll();

        languageLoadingCache.asMap()
                .putAll(
                        coreClient.getAllLanguages()
                        .stream()
                        .collect(Collectors.toMap(LanguageDto::getId, l -> l))
                );

        log.info("Reloaded Language cache");

    }

    @Override
    public PeriodicTrigger getPeriodicTrigger() {
        return new PeriodicTrigger(pollIntervalMins, TimeUnit.MINUTES);
    }
}
