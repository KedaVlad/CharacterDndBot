package com.dnd.CharacterDndBot.service.dndTable.dndService;

import com.dnd.CharacterDndBot.service.User;
import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.actions.Action;
import com.dnd.CharacterDndBot.service.acts.actions.BaseAction;
import com.dnd.CharacterDndBot.service.acts.actions.PreRoll;
import com.dnd.CharacterDndBot.service.acts.actions.RollAction;
import com.dnd.CharacterDndBot.service.dndTable.dndService.userService.CharacterCaseExecutor;
import com.dnd.CharacterDndBot.service.dndTable.dndService.userService.StartExecutor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ExecutorFactory {
	
	public static Executor<?> build(BaseAction action) {
		if (action instanceof Action) {
			return new ActionManager().find((Action) action);
		} else if (action instanceof PreRoll) {
			return new PreRollManager().find((PreRoll) action);
		} else if (action instanceof RollAction) {
			return new RollActionManager().find((RollAction) action);
		} else {
			log.error("Unattended type action in InitializerFactory");
			return new DefaultExecutor(action);
		}
	}
}

interface Manager<T extends BaseAction> {
	abstract Executor<?> find(T t);
}

class ActionManager implements Manager<Action> {
	@Override
	public Executor<?> find(Action action) {
		switch (action.getLocation()) {
		case START:
			return new StartExecutor(action);
		case CHARACTER_CASE:
			return new CharacterCaseExecutor(action);
		case MENU:
			return null;
		case TEXT_COMAND:
			return null;
		default:
			return new DefaultExecutor(action);
		}
	}

}

class PreRollManager implements Manager<PreRoll> {
	@Override
	public Executor<?> find(PreRoll action) {
		return null;
	}

}

class RollActionManager implements Manager<RollAction> {
	@Override
	public Executor<?> find(RollAction action) {
		return null;
	}

}

class DefaultExecutor extends Executor<BaseAction> {
	public DefaultExecutor(BaseAction action) {
		super(action);
	}

	@Override
	public Act executeFor(User user) {
		return null;
	}
}