package app.bot.event;

import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.Data;

@Data
public class CleanSpum {

	private final Update update;
	
	public CleanSpum(Update update) {
		this.update = update;
	}
}
