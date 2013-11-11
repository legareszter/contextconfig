package hu.legare.contextconfig.config;

import java.util.Properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaVendorAdapter;

@Configuration
public interface OrmConfig {

    JpaVendorAdapter vendorAdapter();

    JpaDialect jpaDialect();

    Properties jpaProperties();

}
