package app.bot.model.user;
 
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import app.dnd.dto.CharacterDnd;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
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
	
	public void save() {
		savedCharacters.put(actual.getName(), actual);
	}

	public void delete(String name) {
		savedCharacters.remove(name);
	}

	public void download(String name) {
		if (actual != null) save();
		if (savedCharacters.containsKey(name)) {
			actual = savedCharacters.get(name);
			this.clouds.initialize(this.actual.getClouds());
		} else {
			log.error("CharactersPool (dovnload): " + savedCharacters.containsKey(name) + " - " + name);
		}
	}

	public void setActual(CharacterDnd actual) {
		this.actual = actual;
		this.clouds.initialize(actual.getClouds());
	}
	
	public boolean hasReadyHero() {
		return actual != null && actual.getHp().getMax() > 0;
	}

	public boolean hasCurrentCloud() {
		return  hasReadyHero() && clouds.getCloudsTarget().size() > 0;
	}

}