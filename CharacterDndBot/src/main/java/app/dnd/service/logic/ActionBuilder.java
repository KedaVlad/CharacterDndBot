package app.dnd.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.actions.PoolActions;
import app.dnd.model.actions.PreRoll;
import app.dnd.model.actions.RollAction;
import app.dnd.model.characteristics.Stat;
import app.dnd.service.characterpool.CharacterPoolAction;
import app.dnd.service.logic.attack.AttackAction;
import app.dnd.service.logic.talants.TalantsAction;
import app.player.model.Stage;
import app.player.model.act.ArrayActsBuilder;
import app.player.model.enums.Location;

public interface ActionBuilder {

	public TalantsAction talants();
	public AttackAction attack();
	public CharacterPoolAction characterPool();
	public AbilityAction ability();

}

@Component
class ActionController implements ActionBuilder {

	@Autowired
	public TalantsAction talantsAction;
	@Autowired
	public AbilityAction abilityAction;
	@Autowired
	public AttackAction attackAction;
	@Autowired
	public CharacterPoolAction characterPoolAction;

	@Override
	public TalantsAction talants() {
		return talantsAction;
	}

	@Override
	public AttackAction attack() {
		return attackAction;
	}

	@Override
	public CharacterPoolAction characterPool() {
		return characterPoolAction;
	}

	@Override
	public AbilityAction ability() {
		return abilityAction;
	}

}

public interface StatAction {
	
	BaseAction menu(Long id);

	BaseAction singleStat(Stage stage);
	
	BaseAction changeStat(Stage stage, Long id);
}

class StatActor implements StatAction {
	
	@Autowired
	private ButtonLogic button;
	@Autowired
	private InformatorLogic info;

	@Override
	public BaseAction menu(Long id) {
		return PoolActions.builder()
				.text(info.characteristic().stat().menu())
				.actionsPool(button.characteristic().stat().menu(id))
				.build();
	}

	@Override
	public BaseAction singleStat(Stage stage) {
		Action action = (Action) stage;
		Stat stat = (Stat) action.getObjectDnd();
		action.setButtons(button.characteristic().stat().targetChangeButtons(stat));
		action.setText(info.characteristic().stat().targetFirst(stat) + ". If u have reason to change value...");
		return action;
	}

	@Override
	public BaseAction changeStat(Stage stage, Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

public interface AbilityAction {

	BaseAction menu();

	StatAction stat();

	BaseAction preRoll(Stage stage);

}

class AbilityActor implements AbilityAction {

	@Autowired
	private StatAction statAction;
	@Autowired
	private ButtonLogic button;
	@Autowired
	private InformatorLogic info;

	@Override
	public BaseAction menu() {
		return Action.builder()
				.location(Location.ABILITY)
				.buttons(button.characteristic().menu())
				.text(info.characteristic().menu())
				.replyButtons()
				.build();
	}

	@Override
	public BaseAction preRoll(Stage stage) {

		Action action = (Action) stage;
		Stat stat = (Stat) action.getObjectDnd();
		return PreRoll.builder()
				.text("... or roll.")
				.roll(RollAction.buider()
						.statDepend(stat.getName())
						.build())
				.build();
	}

	@Override
	public StatAction stat() {
		return statAction;
	} 

}