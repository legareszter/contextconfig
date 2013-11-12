package hu.legare.contextconfig.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = "hu.legare.contextconfig", excludeFilters = { @Filter(Configuration.class), @Filter(Controller.class) })
@Import({ ExternalDataSource.class, DefaultJpaConfig.class, EclipseLinkOrmConfig.class })
public class ApplicationContext {

}
