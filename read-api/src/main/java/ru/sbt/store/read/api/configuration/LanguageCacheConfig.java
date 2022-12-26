package ru.sbt.store.read.api.configuration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ru.sbt.common.entities.LanguageDto;
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
public class LanguageCacheConfig {

    private final CoreClient coreClient;

    @Bean
    @Scope("singleton")
    public LoadingCache<Long, LanguageDto> languageLoadingCache() {
        return CacheBuilder.newBuilder()
                .refreshAfterWrite(60, TimeUnit.MINUTES)
                .maximumSize(50)
                .build(languageCacheLoader());
    }

    @Bean
    public CacheLoader<Long, LanguageDto> languageCacheLoader() {
        return new CacheLoader<Long, LanguageDto>() {
            @Override
            public LanguageDto load(Long key) {
                log.warn("Language with key {} wasn't cached yet, caching...", key);

                return coreClient.getLanguage(key)
                        .orElseThrow(() -> new EntityNotFoundException(LanguageDto.class.getName(), key.toString()));
            }
        };
    }

}
