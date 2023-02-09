package com.dnd.CharacterDndBot.service;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ActiveAct;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.dndTable.dndService.ExecutorFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitializerFactory { 
	
	public static Initializer<?> build(Act act) {
		if (act instanceof ReturnAct) {
			return new ReturnActInitializer((ReturnAct) act);
		} else if (act instanceof ActiveAct) {
			return new ActiveActInitializer((ActiveAct) act);
		} else {
			log.error("InitializerFactory: Unattended type act");
			return new DefaultInitializer(act);
		}
	}
}

abstract class Initializer<T extends Act> {
	
	protected T act;

	Initializer(T act) {
		this.act = act;
	}

	public abstract ActiveAct initFor(User user);
}

@Slf4j
class ReturnActInitializer extends Initializer<ReturnAct> {
	
	ReturnActInitializer(ReturnAct act) {
		super(act);
	}

	@Override
	public ActiveAct initFor(User user) {
		if (act.getTarget() != null && user.getScript().targeting(act.getTarget())) {
			if (act.getAct() != null) {
				return new ActiveActInitializer(act.getAct()).initFor(user);
			} else if (act.getCall() != null) {
				return InitializerFactory.build(ExecutorFactory
						.build(user.getScript().getMainTree().getLast().getAction().continueAction(act.getCall()))
						.executeFor(user)).initFor(user);
			} else {
				return null;
			}
		} else {
			log.error("ReturnActInitializer: Target is null or MainTree has no target from return act");
			return null;
		}
	}
}

class ActiveActInitializer extends Initializer<ActiveAct> {
	
	ActiveActInitializer(ActiveAct act) {
		super(act);
	}

	@Override
	public ActiveAct initFor(User user) {
		if (act instanceof SingleAct && act.hasCloud()) {
			user.getCharactersPool().getClouds().getCloudsTarget().add((SingleAct) act);
			return null;
		} else {
			if (user.getScript().getMainTree().getLast().getName().equals(act.getName())) {
				user.getScript().getTrash().addAll(user.getScript().getMainTree().getLast().getActCircle());
				user.getScript().getMainTree().removeLast();
			}
			user.getScript().getMainTree().add(act);
			return act;
		}
	}
}

class DefaultInitializer extends Initializer<Act> {
	
	DefaultInitializer(Act act) {
		super(act);
	}

	@Override
	public ActiveAct initFor(User user) {
		return null;
	}
}
