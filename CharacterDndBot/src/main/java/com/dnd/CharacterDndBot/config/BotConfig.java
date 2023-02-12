package com.dnd.CharacterDndBot.config;

import lombok.Data;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
@Data
@Getter
public class BotConfig {
	@Value("${bot.name}")
	private String botName;
	@Value("${bot.token}")
	private String token;
}
