package com.dnd.CharacterDndBot.service.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.dnd.CharacterDndBot.service.acts.Act;
import com.dnd.CharacterDndBot.service.acts.ActiveAct;
import com.dnd.CharacterDndBot.service.acts.ReturnAct;
import com.dnd.CharacterDndBot.service.acts.SingleAct;
import com.dnd.CharacterDndBot.service.acts.actions.BaseAction;
import com.dnd.CharacterDndBot.service.bot.user.User;
import com.dnd.CharacterDndBot.service.dndTable.dndService.ActionHandler;

import lombok.extern.slf4j.Slf4j;

@Service
public class Player { 
	
	@Autowired
	private UpdateHandler updateHandler;
	@Autowired
	private ActionHandler actionHandler;
	@Autowired
	private ActHandler actHandler;
	
	public ActiveAct playFor(Update update, User user)
	{
		return actHandler.handle(actionHandler.handle(updateHandler.handle(update,user), user), user);
	}
}

@Slf4j
@Service
class ActHandler {
	@Autowired
	private ReturnActInitializer returnActInitializer;
	@Autowired
	private ActiveActInitializer activeActInitializer;
	
	public ActiveAct handle(Act act, User user) {
		if (act instanceof ReturnAct) {
			return returnActInitializer.initFor((ReturnAct)act, user);
		} else if (act instanceof ActiveAct) {
			return activeActInitializer.initFor((ActiveAct) act, user);
		} else {
			log.error("InitializerFactory: Unattended type act");
			return null;
		}
	}
	
	@Component
	class ReturnActInitializer implements ActInitializer<ReturnAct> {
		@Autowired
		private ActionHandler actionHandler;
		@Override
		public ActiveAct initFor(ReturnAct act, User user) {
			if (act.getTarget() != null && user.getScript().targeting(act.getTarget())) {
				if (act.getAct() != null) {
					return activeActInitializer.initFor(act.getAct(), user);
				} else if (act.getCall() != null) {
					BaseAction action = user.getScript().getMainTree().getLast().getAction().continueAction(act.getCall());
					return handle(actionHandler.handle(action, user), user);
				} else {
					return null;
				}
			} else {
				log.error("ReturnActInitializer: Target is null or MainTree has no target from return act");
				return null;
			}
		}
	}
	
}

interface ActInitializer<T extends Act> {
	public abstract ActiveAct initFor(T t, User user);
}


@Component
class ActiveActInitializer implements ActInitializer<ActiveAct> {
	
	@Override
	public ActiveAct initFor(ActiveAct act, User user) {
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

