package com.dnd.CharacterDndBot.service.bot.user;

import java.util.List;

import com.dnd.CharacterDndBot.service.acts.ActiveAct;

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
