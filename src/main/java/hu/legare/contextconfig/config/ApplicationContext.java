package hu.legare.contextconfig.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.config.TargetDatabase;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@ComponentScan(basePackages = "hu.legare.contextconfig", excludeFilters = @Filter(org.springframework.stereotype.Controller.class))
@PropertySource("classpath*:META-INF/spring/*.properties")
public class ApplicationContext {

    @Bean
    public DataSource dataSource() {
        final BasicDataSource ds = new BasicDataSource();
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

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceUnitName("persistenceUnit");
        entityManagerFactoryBean.setDataSource(dataSource());
        Map<String, Object> jpaProperties = new HashMap<String, Object>();
        jpaProperties.put(PersistenceUnitProperties.TARGET_DATABASE, TargetDatabase.PostgreSQL);
        jpaProperties.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
        jpaProperties.put(PersistenceUnitProperties.DDL_GENERATION_MODE, PersistenceUnitProperties.DDL_DATABASE_GENERATION);
        jpaProperties.put(PersistenceUnitProperties.WEAVING, "static");
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);
        return entityManagerFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setLocation(new ClassPathResource("META-INF/spring/*.properties"));
        return propertySourcesPlaceholderConfigurer;
    }

}
