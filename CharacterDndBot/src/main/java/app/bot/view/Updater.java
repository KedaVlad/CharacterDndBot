package app.bot.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.controller.Controller;

public interface Updater {

	public void addUpdate(Update update);
}

@Service
class UpdateCatcher implements Updater {

	@Autowired
	private Controller controller;

	@Override
	public void addUpdate(Update update) {
		controller.start(update);
	}
	
	
}