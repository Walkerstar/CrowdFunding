package com.mcw.scw.project.config;

import com.mcw.scw.project.component.OssTemplate;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author mcw 2019\12\14 0014-11:55
 */
@SpringBootConfiguration
public class AppProjectConfig {

    @ConfigurationProperties(prefix = "oss")
    @Bean
    public OssTemplate ossTemplate(){
        return new OssTemplate();
    }
}
