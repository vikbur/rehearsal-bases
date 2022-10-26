package org.vikbur.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Setter
@Getter
public class DBConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
