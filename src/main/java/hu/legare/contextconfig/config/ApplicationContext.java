package hu.legare.contextconfig.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.config.TargetDatabase;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@ComponentScan(basePackages = "hu.legare.contextconfig", excludeFilters = { @Filter(Configuration.class), @Filter(Controller.class) })
@Configuration
public class ApplicationContext {

    public ApplicationContext() {
        System.out.println("ApplicationContext");
    }

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

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaProperties(jpaProperties());
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("hu.legare.contextconfig");
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter());
        entityManagerFactoryBean.setLoadTimeWeaver(loadTimeWeaver());

        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public BeanPostProcessor persistenceAnnotationBeanPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }

    private Properties jpaProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.put(PersistenceUnitProperties.TARGET_DATABASE, TargetDatabase.PostgreSQL);
        jpaProperties.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
        jpaProperties.put(PersistenceUnitProperties.DDL_GENERATION_MODE, PersistenceUnitProperties.DDL_DATABASE_GENERATION);
        jpaProperties.put(PersistenceUnitProperties.WEAVING, "static");
        return jpaProperties;
    }

    private JpaVendorAdapter vendorAdapter() {
        EclipseLinkJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(false);
        jpaVendorAdapter.setDatabasePlatform(TargetDatabase.PostgreSQL);
        return jpaVendorAdapter;
    }

    private LoadTimeWeaver loadTimeWeaver() {
        return new InstrumentationLoadTimeWeaver();
    }

}
