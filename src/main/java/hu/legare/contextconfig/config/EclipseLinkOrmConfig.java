package hu.legare.contextconfig.config;

import java.util.Properties;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.config.TargetDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

@Configuration
public class EclipseLinkOrmConfig implements OrmConfig {

	@Value("${targetDatabase}")
    private String database;

    public Properties jpaProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.put(PersistenceUnitProperties.TARGET_DATABASE, database);
        jpaProperties.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
        jpaProperties.put(PersistenceUnitProperties.DDL_GENERATION_MODE, PersistenceUnitProperties.DDL_DATABASE_GENERATION);
        jpaProperties.put(PersistenceUnitProperties.WEAVING, "static");
        return jpaProperties;
    }

    public JpaVendorAdapter vendorAdapter() {
        EclipseLinkJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(false);
        jpaVendorAdapter.setDatabasePlatform(database);
        return jpaVendorAdapter;
    }

    public JpaDialect jpaDialect() {
        return new EclipseLinkJpaDialect();
    }

}
