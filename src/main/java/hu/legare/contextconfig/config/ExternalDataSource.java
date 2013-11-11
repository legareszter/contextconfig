package hu.legare.contextconfig.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalDataSource implements DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/contextconfig?useUnicode=yes&characterEncoding=UTF-8&connectionCollation=utf8_general_ci");
        ds.setUsername("contextconfig");
        ds.setPassword("contextconfig");
        ds.setTestOnBorrow(true);
        ds.setTestOnReturn(true);
        ds.setTestWhileIdle(true);
        ds.setTimeBetweenEvictionRunsMillis(1800000);
        ds.setNumTestsPerEvictionRun(3);
        ds.setMinEvictableIdleTimeMillis(1800000);
        ds.setValidationQuery("SELECT version();");
        return ds;
    }

}
