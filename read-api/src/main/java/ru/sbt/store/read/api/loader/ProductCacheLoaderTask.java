package ru.sbt.store.read.api.loader;

import com.google.common.cache.LoadingCache;
import ru.sbt.common.entities.Product;
import ru.sbt.common.entities.ProductKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;
import ru.sbt.store.read.api.converter.ProductConverter;
import ru.sbt.store.read.api.client.CoreClient;

import java.util.concurrent.TimeUnit;


@RequiredArgsConstructor
@Slf4j
@Component
public class ProductCacheLoaderTask implements CacheLoaderTask {

    private final LoadingCache<ProductKey, Product> productLoadingCache;

    private final CoreClient coreClient;

    private final ProductConverter productConverter;

    @Value("${cache.loader.poll.interval.mins.product}")
    private Long pollIntervalMins;



    @Override
    public void run() {

        productLoadingCache.invalidateAll();

        productLoadingCache.asMap()
                .putAll(productConverter.convertFromDtosWithMultipleInfos(coreClient.getAllProducts()));

        log.info("Reloaded Product Cache");

    }

    @Override
    public PeriodicTrigger getPeriodicTrigger() {
        return new PeriodicTrigger(pollIntervalMins, TimeUnit.MINUTES);
    }
}
