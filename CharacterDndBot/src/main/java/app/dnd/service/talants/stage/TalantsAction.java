package app.dnd.service.talants.stage;

import app.dnd.model.actions.BaseAction;
import app.bot.model.user.ActualHero;

public interface TalantsAction {
	
	public BaseAction menu(ActualHero hero);
	public FeatureAction feature();
	public MagicSoulAction magic();
	public ProfAction prof();
	
}

