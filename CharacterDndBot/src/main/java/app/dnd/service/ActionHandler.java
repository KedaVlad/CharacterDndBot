package app.dnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import app.bot.model.act.actions.Action;
import app.bot.model.act.actions.BaseAction;
import app.bot.model.act.actions.PreRoll;
import app.bot.model.act.actions.RollAction;
import app.bot.model.wrapp.ActWrapp;
import app.bot.model.wrapp.BaseActionWrapp;
import app.bot.service.Handler;
import app.dnd.service.attackmachine.AttackMachine;
import app.dnd.service.attackmachine.AttackMachinePreRoll;
import app.dnd.service.character.AbilityExecutor;
import app.dnd.service.character.CharacterisricExecutor;
import app.dnd.service.character.DebuffExecutor;
import app.dnd.service.character.MemoirsExecutor;
import app.dnd.service.character.RestExecutor;
import app.dnd.service.character.StuffExecutor;
import app.dnd.service.factory.CharacterFactory;
import app.dnd.service.factory.ClassFactory;
import app.dnd.service.factory.HpFactory;
import app.dnd.service.factory.ItemFactory;
import app.dnd.service.factory.RaceFactory;
import app.dnd.service.factory.StatFactory;
import app.dnd.service.gamer.CharacterCaseExecutor;
import app.dnd.service.gamer.DownloadOrDeleteExecutor;
import app.dnd.service.gamer.Menu;
import app.dnd.service.gamer.RollsExecutor;
import app.dnd.service.gamer.Start;
import app.dnd.service.gamer.TextComandExecutor;
import app.dnd.service.roll.DefaultPreRollExecuter;
import app.dnd.service.roll.HeroRolleExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActionHandler implements Handler<BaseActionWrapp, ActWrapp> {

	@Autowired
	private ActionManager actionManager;
	@Autowired
	private PreRollManager preRollManager;
	@Autowired
	private RollActionManager rollActionManager;

	@Override
	public ActWrapp handle(BaseActionWrapp wrapp) {

		if (wrapp.getTarget() instanceof Action) {
			return new ActWrapp(wrapp.getUser(), actionManager.find(wrapp.getTarget().getLocation())
					.executeFor((Action) wrapp.getTarget(), wrapp.getUser()));
		} else if (wrapp.getTarget() instanceof PreRoll) {
			return new ActWrapp(wrapp.getUser(), preRollManager.find(wrapp.getTarget().getLocation())
					.executeFor((PreRoll) wrapp.getTarget(), wrapp.getUser()));
		} else if (wrapp.getTarget() instanceof RollAction) {
			return new ActWrapp(wrapp.getUser(), rollActionManager.find(wrapp.getTarget().getLocation())
					.executeFor((RollAction) wrapp.getTarget(), wrapp.getUser()));
		} else {
			log.error("ActionHandler : Unattended type action");
			return new ActWrapp(wrapp.getUser(), null);
		}
	}
}

interface Manager<T extends BaseAction> {
	abstract Executor<?> find(Location location);
}

@Slf4j
@Component
class ActionManager implements Manager<Action> {
	@Autowired
	private Start start;
	@Autowired
	private AbilityExecutor abilityExecutor;
	@Autowired
	private CharacterisricExecutor characterisricExecutor;
	@Autowired
	private CharacterCaseExecutor characterCaseExecutor;
	@Autowired
	private CharacterFactory characterFactory;
	@Autowired
	private ClassFactory classFactory;
	@Autowired
	private DebuffExecutor debuffExecutor;
	@Autowired
	private HpFactory hpFactory;
	@Autowired
	private ItemFactory itemFactory;
	@Autowired
	private MemoirsExecutor memoirsExecutor;
	@Autowired
	private Menu menu;
	@Autowired
	private RaceFactory raceFactory;
	@Autowired
	private RestExecutor restExecutor;
	@Autowired
	private RollsExecutor rollsExecutor;
	@Autowired
	private StatFactory statFactory;
	@Autowired
	private StuffExecutor stuffExecutor;
	@Autowired
	private DownloadOrDeleteExecutor downloadOrDeleteExecutor;
	@Autowired
	private AttackMachine attackMachine;
	@Autowired
	private TextComandExecutor textComandExecutor;

	@Override
	public Executor<Action> find(Location location) {
		log.info("ActionManager : " + location);
		switch (location) {
		case START:
			return start;
		case ABILITY:
			return abilityExecutor;
		case CHARACTERISTIC:
			return characterisricExecutor;
		case CHARACTER_CASE:
			return characterCaseExecutor;
		case CHARACTER_FACTORY:
			return characterFactory;
		case CLASS_FACTORY:
			return classFactory;
		case DEBUFF:
			return debuffExecutor;
		case DOWNLOAD_OR_DELETE:
			return downloadOrDeleteExecutor;
		case HP_FACTORY:
			return hpFactory;
		case ITEM_FACTORY:
			return itemFactory;
		case MEMOIRS:
			return memoirsExecutor;
		case MENU:
			return menu;
		case RACE_FACTORY:
			return raceFactory;
		case REST:
			return restExecutor;
		case ROLLS:
			return rollsExecutor;
		case STAT_FACTORY:
			return statFactory;
		case STUFF:
			return stuffExecutor;
		case TEXT_COMAND:
			return textComandExecutor;
		case ATTACK_MACHINE:
			return attackMachine;
		default:
			return null;

		}
	}
}

@Component
class PreRollManager implements Manager<PreRoll> {

	@Autowired
	private DefaultPreRollExecuter defaultPreRollExecuter;
	@Autowired
	private AttackMachinePreRoll attackMachinePreRoll;

	@Override
	public Executor<PreRoll> find(Location location) {
		
		if(location == Location.ATTACK_MACHINE) {
			return attackMachinePreRoll;
		} else {
			return defaultPreRollExecuter;
		}
	}

}

@Component
class RollActionManager implements Manager<RollAction> {

	@Autowired
	private HeroRolleExecutor heroRolleExecutor;
	
	@Override
	public Executor<RollAction> find(Location location) {
		return heroRolleExecutor;
	}

}
