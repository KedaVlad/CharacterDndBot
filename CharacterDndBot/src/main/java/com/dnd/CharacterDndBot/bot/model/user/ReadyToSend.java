package com.dnd.CharacterDndBot.bot.model.user;

import java.util.List;

import com.dnd.CharacterDndBot.bot.model.act.ActiveAct;

import lombok.Data;

@Data
public class ReadyToSend { 
	
	private List<ActiveAct> readyToSend;
	private List<Integer> trash;
	private final long id;

	ReadyToSend(long id, List<ActiveAct> readyToSend, List<Integer> trash) {
		this.id = id;
		this.readyToSend = readyToSend;
		this.trash = trash;
	}

}
