package com.dnd.CharacterDndBot.service.bot.user;
 
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;

import lombok.Data;

@Data
public class CharactersPool implements Serializable {

	private static final long serialVersionUID = 1L;
	private CharacterDnd actual;
	private Clouds clouds;
	private Map<String, CharacterDnd> savedCharacters;

	public CharactersPool(List<Integer> trash) {
		clouds = new Clouds(trash);
		savedCharacters = new LinkedHashMap<>();
	}

	public int cloudsValue() {
		return clouds.getCloudsTarget().size() + clouds.getCloudsWorked().size();
	}
	
	void save() {
		savedCharacters.put(actual.getName(), actual);
	}

	void delete(String name) {
		savedCharacters.remove(name);
	}

	void download(String name) {
		if (actual != null) save();
		if (savedCharacters.containsKey(name)) {
			actual = savedCharacters.get(name);
			this.clouds.initialize(this.actual.getClouds());
		}
	}

	public void setActual(CharacterDnd actual) {
		this.actual = actual;
		this.clouds.initialize(actual.getClouds());
	}

	public boolean hasCurrentCloud() {
		return actual != null && clouds.getCloudsTarget().size() > 0 && actual.getHp().getNow() > 0;
	}

}