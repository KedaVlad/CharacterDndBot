package app.dnd.service.hp;

import app.dnd.model.actions.Action;
import app.player.model.Stage;
import app.bot.model.user.ActualHero;

public interface HpAction {

	Stage startBuildHp(ActualHero hero);

	Stage apruveHp(ActualHero actualHero, Action action);

}
