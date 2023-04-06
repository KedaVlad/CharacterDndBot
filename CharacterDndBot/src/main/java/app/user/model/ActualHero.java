package app.user.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.dnd.model.hero.CharacterDnd;
import app.player.model.act.SingleAct;
import lombok.Data;

@Data
@Document("hero")
public class ActualHero {
	@Id
	private Long id;
	private CharacterDnd character;
	
	public boolean hasReadyHero() {
		return character != null && character.isReady();
	}
	
	public List<SingleAct> getClouds() {
		List<SingleAct> clouds = new ArrayList<>();
		if(character != null && character.isReady()) {
			clouds.addAll(character.getClouds());
			character.getClouds().clear();
		}
		return clouds;
	}
	
}
