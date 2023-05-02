package app.player.controller;

import app.player.model.event.ActEvent;
import app.player.model.event.StageEvent;
import app.player.model.act.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import app.player.model.event.EndGame;
import app.player.model.EventHandler;
import app.bot.model.user.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventHandler("actHandler")
public class ActHandler {

	private final ApplicationEventPublisher eventPublisher;
	public ActHandler(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@EventListener
	public void handle(ActEvent event) {

		Act act = event.getTusk();
		if (act instanceof ReturnAct) {
			returnAct((ReturnAct) event.getTusk(), event.getUser());
		} else if (act instanceof ActiveAct) {
			activeAct((ActiveAct) event.getTusk(), event.getUser());
		} else {
			log.error("ActHandler: Unattended type act");
		}
	}

	private void returnAct(ReturnAct act, User user) {

		if (act.getTarget() != null && user.getScript().targeting(act.getTarget(), user.getTrash())) {
			if (act.getAct() != null) {
				activeAct(act.getAct(), user);
			} else if (act.getAction() != null) {
				eventPublisher.publishEvent(new StageEvent(this, user, act.getAction()));
			} else if (act.getCall() != null && !act.getCall().equals(act.getTarget())) {
				eventPublisher.publishEvent(new StageEvent(this, user, user.getScript().getMainTree().getLast().continueAct(act.getCall())));
			} else {
				eventPublisher.publishEvent(new EndGame(this, user));
			}
		}
	}

	private void activeAct(ActiveAct act, User user) {
		if(act instanceof CloudAct) {
			if(	user.getActualHero().getHeroCloud() != null) {
				user.getActualHero().getHeroCloud().getClouds().add((CloudAct) act);
			}
			user.setMessage(null);
			eventPublisher.publishEvent(new EndGame(this, user));
		} else if(act instanceof TreeAct){
			user.getScript().prepareScript((TreeAct) act, user.getTrash());
			user.setMessage(act);
			eventPublisher.publishEvent(new EndGame(this, user));
		}
	}


}

