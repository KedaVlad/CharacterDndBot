package com.dnd.CharacterDndBot.service.dndTable.dndService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.acts.actions.BaseAction;
import com.dnd.CharacterDndBot.service.acts.actions.PreRoll;
import com.dnd.CharacterDndBot.service.acts.actions.RollAction;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndService.characterService.AbilityMenu;
import com.dnd.CharacterDndBot.service.dndTable.dndService.characterService.CharacterisricExecutor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.characterService.DebuffExecutor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.characterService.MemoirsExecutor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.characterService.RestExecutor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.characterService.RollsExecutor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.characterService.StuffMenu;
import com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService.CharacterFactory;
import com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService.ClassFactory;
import com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService.HpFactory;
import com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService.ItemFactory;
import com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService.RaceFactory;
import com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService.StatFactory;
import com.dnd.CharacterDndBot.service.dndTable.dndService.userService.CharacterCaseExecutor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.userService.Menu;
import com.dnd.CharacterDndBot.service.dndTable.dndService.userService.Start;

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
			log.error("Unattended type action in InitializerFactory");
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
	private AbilityMenu abilityMenu;
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
	private StuffMenu stuffMenu;
	
	@Override
	public Executor<Action> find(Location location) {
		switch (location) {
		case START:
			return start;
		case ABILITY:
			return abilityMenu; //!!!
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
		case DOWNLOAD:
			break;
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
			return stuffMenu; //!!!!!!!!!!
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
