package de.c24.finacc.klt;

import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

/**
 * Spring boot application start point
 */
@SpringBootApplication
@EnableCaching
public class KltApplication {
    private static final Logger log = LoggerFactory.getLogger(KltApplication.class);

    /**
     * start point
     *
     * @param args system arguments
     */
    public static void main(String[] args) {
        log.info("init KltApplication");
        SpringApplication.run(KltApplication.class, args);
        log.info("start KltApplication");
    }

    @Bean("cacheManager")
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(
                        name,
                        CacheBuilder.newBuilder()
                                .expireAfterWrite(1, TimeUnit.SECONDS)
                                .build().asMap(),
                        false);
            }
        };
    }
}
