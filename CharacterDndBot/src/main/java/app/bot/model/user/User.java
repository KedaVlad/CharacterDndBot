package app.bot.model.user;

import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.model.ActualHero;
import app.bot.model.act.ActiveAct;
import lombok.Data;

@Data
public class User {
	
	private Long id;
	private Script script;
	private Clouds clouds;
	private Trash trash;
	private Update update;
	private ActiveAct act;
	private ActualHero actualHero;
}
