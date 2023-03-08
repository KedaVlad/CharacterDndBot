package app.dnd.service.factory.comandreader;

import org.springframework.stereotype.Component;

import app.bot.model.act.SingleAct;
import app.bot.model.act.actions.Action;
import app.dnd.dto.CharacterDnd;
import app.dnd.dto.comands.CloudComand;

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
