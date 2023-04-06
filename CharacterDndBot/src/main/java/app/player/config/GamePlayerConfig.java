package app.player.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import app.player.service.EventHandler;
import app.player.service.Handler;
import app.user.model.User;


@PropertySource("application.properties")
@Configuration
public class GamePlayerConfig {
	
	@Value("${eventhandler.qualifiers}")
	private String[] qualifiers;
	
	@Bean
	public String[] qualifiers() {
		return qualifiers;
	}
	
	@Bean
	public List<Handler<User>> handlers(@Qualifier("qualifiers") String[] qualifiers, ListableBeanFactory beanFactory) {
		return  Arrays.stream(beanFactory.getBeanNamesForAnnotation(EventHandler.class))
	            .map(beanName -> beanFactory.getBean(beanName, Handler.class))
	            .filter(handler -> Arrays.asList(qualifiers).contains(handler.getClass().getAnnotation(Qualifier.class).value()))
	            .collect(Collectors.toList());
	}
}
