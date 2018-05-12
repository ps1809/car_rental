package com.car_rent.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.car_rent")
@PropertySource(value = { "classpath:application.properties" })
public class CarRentConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	Environment env;
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");

		return bean;
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Bean
	public DataSource datasource(){
		DriverManagerDataSource datasource=new DriverManagerDataSource();
		datasource.setDriverClassName(env.getProperty("jdbc-driver-class"));
		datasource.setUrl(env.getProperty("jdbc-url"));
		datasource.setUsername(env.getProperty("jdbc-username"));
		datasource.setPassword(env.getProperty("jdbc-password"));
		return datasource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource datasource){
		JdbcTemplate jdbcTemplate=new JdbcTemplate(datasource);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		return jdbcTemplate;
	}
}
