package app.dnd.service.logic.talants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.service.logic.ButtonLogic;
import app.dnd.service.logic.InformatorLogic;
import app.dnd.service.logic.talants.feature.AbilityAction;
import app.dnd.service.logic.talants.magicsoul.MagicSoulAction;
import app.dnd.service.logic.talants.prof.ProfAction;
import app.player.model.enums.Location;

public interface TalantsAction {
	
	public BaseAction menu(Long id);
	public AbilityAction feature();
	public MagicSoulAction magic();
	public ProfAction prof();
	
}

@Component
class TalantsActor implements TalantsAction {

	@Autowired
	private ButtonLogic buttonLogic;
	@Autowired
	private InformatorLogic informator;
	@Autowired
	private AbilityAction abilityAction;
	@Autowired
	private MagicSoulAction magicSoulAction;
	@Autowired
	private ProfAction profAction;
	
	
	@Override
	public BaseAction menu(Long id) {
		return Action.builder()
				.buttons(buttonLogic.talants().menu(id))
				.text(informator.talants().menu())
				.location(Location.TALANT)
				.replyButtons()
				.build();
	}

	@Override
	public AbilityAction feature() {
		return abilityAction;
	}

	@Override
	public MagicSoulAction magic() {
		return magicSoulAction;
	}

	@Override
	public ProfAction prof() {
		return profAction;
	}
	
}