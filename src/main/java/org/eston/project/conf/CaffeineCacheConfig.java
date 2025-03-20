package org.eston.project.conf;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

/**
 * caffeine缓存配置
 * @author shiyingchen
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "caffeine.cache.config")
public class CaffeineCacheConfig {

    // 最大对象数
    private long maximumSize;
    // 根据创建时间的过期时长，秒
    private long expireAfterWriteSec;
    // 初始容量
    private int initialCapacity;

    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(expireAfterWriteSec, TimeUnit.SECONDS)
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .recordStats()
        );
        return cacheManager;
    }

}
