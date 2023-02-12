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
import com.dnd.CharacterDndBot.service.dndTable.dndService.factoryService.CharacterFactory;
import com.dnd.CharacterDndBot.service.dndTable.dndService.userService.CharacterCaseExecutor;
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
			break;
		case DEBUFF:
			break;
		case DOWNLOAD:
			break;
		case HP_FACTORY:
			break;
		case ITEM_FACTORY:
			break;
		case MEMOIRS:
			break;
		case MENU:
			break;
		case RACE_FACTORY:
			break;
		case REST:
			break;
		case ROLLS:
			break;
		case STAT_FACTORY:
			break;
		case STUFF:
			break;
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
