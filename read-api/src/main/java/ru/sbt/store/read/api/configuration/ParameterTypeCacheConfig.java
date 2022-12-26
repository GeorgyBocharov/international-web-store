package ru.sbt.store.read.api.configuration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ru.sbt.common.entities.ParameterTypeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.sbt.store.read.api.client.CoreClient;
import ru.sbt.store.read.api.exceptions.EntityNotFoundException;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ParameterTypeCacheConfig {

    private final CoreClient coreClient;


    @Bean
    @Scope("singleton")
    public LoadingCache<Long, ParameterTypeDto> parameterTypeLoadingCache() {
        return CacheBuilder.newBuilder()
                .refreshAfterWrite(60, TimeUnit.MINUTES)
                .maximumSize(50)
                .build(parameterTypeCacheLoader());
    }

    @Bean
    public CacheLoader<Long, ParameterTypeDto> parameterTypeCacheLoader() {
        return new CacheLoader<Long, ParameterTypeDto>() {
            @Override
            public ParameterTypeDto load(Long key) {
                log.warn("ParameterType with key {} wasn't cached yet, caching...", key);

                return coreClient.getParameterType(key)
                        .orElseThrow(() -> new EntityNotFoundException(ParameterTypeDto.class.getName(), key.toString()));
            }
        };
    }


}
