package app.dnd.service.memoirs;

import app.player.model.Stage;
import app.bot.model.user.ActualHero;

public interface MemoirsAction {
	Stage menu(ActualHero actualHero);
}
