package com.dnd.CharacterDndBot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Configuration

public class JacksonConfig {
  
	@Bean
    @Primary
	public ObjectMapper objectMapper() {
	    ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.activateDefaultTyping(
				  LaissezFaireSubTypeValidator.instance,
				  ObjectMapper.DefaultTyping.NON_FINAL,
				  JsonTypeInfo.As.PROPERTY);
	    return objectMapper;
	}
}
