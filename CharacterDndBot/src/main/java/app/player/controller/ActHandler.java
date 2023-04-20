package app.player.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import app.bot.event.EndGame;
import app.player.event.UserEvent;
import app.player.model.EventHandler;
import app.player.model.Stage;
import app.player.model.act.Act;
import app.player.model.act.ActiveAct;
import app.player.model.act.CloudAct;
import app.player.model.act.ReturnAct;
import app.player.service.Handler;
import app.user.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventHandler("actHandler")
public class ActHandler implements Handler<Act> {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Override
	@EventListener
	public void handle(UserEvent<Act> event) {

		Act act = event.getTask();
		if (act instanceof ReturnAct) {
			returnAct((ReturnAct) event.getTask(), event.getUser());
		} else if (act instanceof CloudAct) {
			activeAct((ActiveAct) event.getTask(), event.getUser());
		} else {
			log.error("ActHandler: Unattended type act");
		}
	}

	private void returnAct(ReturnAct act, User user) {

		if (act.getTarget() != null && user.getScript().targeting(act.getTarget(), user.getTrash())) {
			if (act.getAct() != null) {
				activeAct(act.getAct(), user);
			} else if (act.getAction() != null) {
				eventPublisher.publishEvent(new UserEvent<Stage>(user, act.getAction()));
			} else if (act.getCall() != null && !act.getCall().equals(act.getTarget())) {
				eventPublisher.publishEvent(new UserEvent<Stage>(user, user.getScript().getMainTree().getLast().continueAct(act.getCall())));
			} else {
				log.error("ReturnActInitializer: Target is null or MainTree has no target from return act");
			}
		}
	}

	private void activeAct(ActiveAct act, User user) {

		if(act instanceof CloudAct) {
			if(	user.getActualHero().getHeroCloud() != null) {
				user.getActualHero().getHeroCloud().getClouds().add((CloudAct) act);
			}
			user.setMessage(null);
			eventPublisher.publishEvent(new EndGame(user));
		} else {
			user.getScript().prepareScript(act.getName(), user.getTrash());
			user.setMessage(act);
			eventPublisher.publishEvent(new EndGame(user));
		}
	}


}

