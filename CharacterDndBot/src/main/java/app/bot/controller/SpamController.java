package app.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.conteiner.SpamConteiner;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SpamController {

	@Autowired
	private SpamConteiner spamConteiner;

	public void add(Update update) {
		try {
			spamConteiner.add(update);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}

	public Update get() {
		try {
			return spamConteiner.get();
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
		return null;
	}



}
