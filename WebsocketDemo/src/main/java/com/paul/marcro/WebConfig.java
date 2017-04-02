package com.paul.marcro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan
@EnableScheduling
public class WebConfig extends WebMvcConfigurerAdapter{
	
	//设置视图解析
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}
	
	//管理静态文件
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/") ;
	}
	
	@Bean
	public ViewResolver viewResolver(SpringTemplateEngine templateEngine){
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		return viewResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine(TemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}
	
	//配置视图前缀后缀  视图模型
	@Bean
	public TemplateResolver templateResolver(){
		TemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		return templateResolver;
	}
}
