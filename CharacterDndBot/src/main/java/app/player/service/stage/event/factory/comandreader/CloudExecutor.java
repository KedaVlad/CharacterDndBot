package app.player.service.stage.event.factory.comandreader;

import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.comands.CloudComand;
import app.dnd.model.hero.CharacterDnd;
import app.player.model.act.SingleAct;

@Component
class CloudExecutor {

	public void cloud(CharacterDnd character, CloudComand comand) {
		character.getClouds().add(SingleAct.builder()
				.name(comand.getName())
				.text(comand.getText())
				.action(Action.builder()
						.cloud()
						.build())
				.build());
	}
	
}
