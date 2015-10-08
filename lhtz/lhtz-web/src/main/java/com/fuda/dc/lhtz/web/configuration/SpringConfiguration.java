package com.fuda.dc.lhtz.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fuda.dc.lhtz.web.search.pool.ElasticSearchPool;

@Configuration
public class SpringConfiguration {

    @Bean
    public ElasticSearchPool elasticSearchPool() {
        return new ElasticSearchPool();
    }

}
