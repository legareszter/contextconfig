package hu.legare.contextconfig.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public interface DataSourceConfig {

    @Bean
    DataSource dataSource();

}
