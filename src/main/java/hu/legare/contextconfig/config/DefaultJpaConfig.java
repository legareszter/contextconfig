package hu.legare.contextconfig.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class DefaultJpaConfig implements JpaConfig {

    @Autowired
    private DataSourceConfig dataSourceConfig;
    @Autowired
    private OrmConfig ormConfig;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSourceConfig.dataSource());

        entityManagerFactoryBean.setPackagesToScan("hu.legare.contextconfig");

        entityManagerFactoryBean.setJpaProperties(ormConfig.jpaProperties());
        entityManagerFactoryBean.setJpaVendorAdapter(ormConfig.vendorAdapter());
        entityManagerFactoryBean.setJpaDialect(ormConfig.jpaDialect());

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

    private LoadTimeWeaver loadTimeWeaver() {
        return new InstrumentationLoadTimeWeaver();
    }

}
