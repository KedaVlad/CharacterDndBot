package app.dnd.service.attack;

import app.dnd.model.actions.BaseAction;
import app.player.model.Stage;
import app.user.model.ActualHero;

public interface AttackAction {

	BaseAction preAttack(ActualHero hero, Stage stage);
	BaseAction postAttack(ActualHero hero, Stage stage);
	BaseAction preHit(ActualHero hero, Stage stage);
	BaseAction postHit(ActualHero hero, Stage stage);
}


