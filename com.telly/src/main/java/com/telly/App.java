package com.telly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;



@SpringBootApplication
@Import(RabbitConfiguration.class)
@EnableAutoConfiguration
public class App extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);

	}
	
	@Bean
	public TilesConfigurer tilesConfigurer(){
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		
		String[] defs = {"/WEB-INF/tiles.xml"};
		
		tilesConfigurer.setDefinitions(defs);
		
		return tilesConfigurer;
	}
	
	@Bean
	public UrlBasedViewResolver tilesViewResolver(){
		UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
		
		tilesViewResolver.setViewClass(TilesView.class);
		
		return tilesViewResolver;
		
	}
	
	@Bean
	EmbeddedServletContainerCustomizer errorHandler() {
		return new EmbeddedServletContainerCustomizer() {

			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403"));			}
			
		};
	}
	
	@Bean
	PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
}