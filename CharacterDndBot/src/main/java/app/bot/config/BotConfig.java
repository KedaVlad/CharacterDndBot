package app.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("application.properties")
@Configuration
public class BotConfig {
	
	@Value("${bot.name}")
	private String botName;
	@Value("${bot.token}")
	private String token;
	
	@Bean
	public String botName() {
		return botName;
	}
	
	@Bean
	public String token() {
		return token;
	}

}
