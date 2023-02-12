package com.dnd.CharacterDndBot.service.bot.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dnd.CharacterDndBot.service.acts.ActiveAct;

import lombok.Data;

@Data
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final long id;
	private final CharactersPool charactersPool;
	private final Script script;

	public User(long id) {
		this.id = id;
		script = new Script(id);
		charactersPool = new CharactersPool(script.getTrash());
	}

	public ReadyToSend makeSend(ActiveAct act) {
		List<ActiveAct> toWork = new ArrayList<>();
		if (act != null) toWork.add(act);
		if (charactersPool.hasCurrentCloud()) {
			toWork.addAll(charactersPool.getClouds().compleatClouds());
		}
		return new ReadyToSend(id, toWork, script.trashThrowOut());
	}
}


