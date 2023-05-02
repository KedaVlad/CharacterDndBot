package app.bot.config;

import app.bot.telegramapi.Bot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@PropertySource("application.properties")
@Configuration
@Slf4j
public class BotConfig {
	
	@Value("${bot.name}")
	private String botName;
	@Value("${bot.token}")
	private String token;

	@Bean
	public Bot bot() {
		return new Bot(token, botName);
	}
}
