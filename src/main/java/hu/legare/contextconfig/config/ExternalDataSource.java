package hu.legare.contextconfig.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalDataSource implements DataSourceConfig {

	@Value("${dbUrl}")
	private String dbUrl;

	@Bean
	public DataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		String url = "jdbc:postgresql://" + dbUrl;
		System.out.println(url);
		ds.setUrl(url);
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
