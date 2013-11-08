package hu.legare.contextconfig.config;

import java.util.Properties;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@ComponentScan(basePackages = "hu.legare.contextconfig", excludeFilters = @Filter(Configuration.class), includeFilters = @Filter(Controller.class))
@EnableWebMvc
@Configuration
public class DispatcherServletContext extends WebMvcConfigurerAdapter {

    public DispatcherServletContext() {
        System.out.println("DispatcherServletContext");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/", "classpath:/META-INF/web-resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/login");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/uncaughtException");
        registry.addViewController("/resourceNotFound");
        registry.addViewController("/dataAccessFailure");
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("WEB-INF/i18n/messages", "WEB-INF/i18n/application");
        messageSource.setFallbackToSystemLocale(false);
        return messageSource();
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("locale");
        return localeResolver;
    }

    @Bean
    public HandlerExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        Properties exceptionMappings = new Properties();
        exceptionMappings.setProperty(".DataAccessException", "dataAccessFailure");
        exceptionMappings.setProperty(".NoSuchRequestHandlingMethodException", "resourceNotFound");
        exceptionMappings.setProperty(".TypeMismatchException", "resourceNotFound");
        exceptionMappings.setProperty(".MissingServletRequestParameterException", "resourceNotFound");
        exceptionResolver.setExceptionMappings(exceptionMappings);
        return exceptionResolver;
    }

    @Bean
    public UrlBasedViewResolver tilesViewResolver() {
        UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
        tilesViewResolver.setViewClass(TilesView.class);
        return tilesViewResolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        String[] definitions = { "/WEB-INF/layouts/layouts.xml", "/WEB-INF/views/**/views.xml" };
        tilesConfigurer.setDefinitions(definitions);
        return tilesConfigurer;
    }
}
