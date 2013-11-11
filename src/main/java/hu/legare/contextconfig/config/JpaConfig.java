package hu.legare.contextconfig.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public interface JpaConfig {

    @Bean
    EntityManagerFactory entityManagerFactory();

    @Bean
    PlatformTransactionManager transactionManager();

    @Bean
    BeanPostProcessor persistenceAnnotationBeanPostProcessor();
}
