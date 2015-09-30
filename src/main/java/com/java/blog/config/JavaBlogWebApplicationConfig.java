package com.java.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.java.blog.controller.AdminFilter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.java.blog" }, excludeFilters = { @Filter(Configuration.class) })
@Import({ PersistenceConfig.class })
public class JavaBlogWebApplicationConfig {

	@Bean
	public AdminFilter adminFilter() {
		return new AdminFilter();
	}

}
