package app.dnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.bot.model.act.Act;
import app.bot.model.act.actions.Action;
import app.bot.model.act.actions.BaseAction;
import app.bot.model.act.actions.PreRoll;
import app.bot.model.act.actions.RollAction;
import app.bot.model.user.User;
import app.dnd.service.character.AbilityExecutor;
import app.dnd.service.character.CharacterisricExecutor;
import app.dnd.service.character.DebuffExecutor;
import app.dnd.service.character.MemoirsExecutor;
import app.dnd.service.character.RestExecutor;
import app.dnd.service.character.RollsExecutor;
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
import app.dnd.service.gamer.Start;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActionHandler {
	
	@Autowired
	private ActionManager actionManager;
	@Autowired
	private PreRollManager preRollManager;
	@Autowired
	private RollActionManager rollActionManager;
	
	public Act handle(BaseAction action, User user) {
		
		if (action instanceof Action) {
			return actionManager.find(action.getLocation()).executeFor((Action)action, user);
		} else if (action instanceof PreRoll) {
			return preRollManager.find(action.getLocation()).executeFor((PreRoll)action, user);
		} else if (action instanceof RollAction) {
			return rollActionManager.find(action.getLocation()).executeFor((RollAction)action, user);
		} else {
			log.error("ActionHandler : Unattended type action");
			return null;
		}
	}
}

interface Manager<T extends BaseAction> {
	abstract Executor<?> find(Location location);
}

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
	
	@Override
	public Executor<Action> find(Location location) {
		
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
			break;
		default:
			break;
		
		}
		return null;
	}

}

@Component
class PreRollManager implements Manager<PreRoll> {
	
	@Override
	public Executor<PreRoll> find(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

}

@Component
class RollActionManager implements Manager<RollAction> {
	
	@Override
	public Executor<RollAction> find(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

}
