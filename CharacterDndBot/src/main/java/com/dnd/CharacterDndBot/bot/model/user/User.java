package com.dnd.CharacterDndBot.bot.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.CharacterDndBot.bot.model.act.ActiveAct;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final Long id;
	private final CharactersPool charactersPool;
	private final Script script;
	private ActiveAct targetAct;

	public void targetAct(ActiveAct activeAct) {
		script.getMainTree().add(activeAct);
		targetAct = activeAct;
	}
	
	public User(long id) {
		this.id = id;
		script = new Script(id);
		charactersPool = new CharactersPool(script.getTrash());
	}

	public ReadyToSend makeSend() {
		
		log.info("User (makeSend) : " + targetAct);
		
		List<ActiveAct> toWork = new ArrayList<>();
		if (targetAct != null) toWork.add(targetAct);
		if (charactersPool.hasCurrentCloud()) {
			toWork.addAll(charactersPool.getClouds().compleatClouds());
		}
		return new ReadyToSend(id, toWork, script.trashThrowOut());
	}
}


