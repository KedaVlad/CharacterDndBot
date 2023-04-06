package app.dnd.model.hero;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "race_dnd")
public class RaceDnd {
	
	@Id
	private Long id;
	private String raceName;
	private String subRace;
	private int speed;
	
	public static RaceDnd build(Long id) {
		RaceDnd race = new RaceDnd();
		race.id = id;
		return race;
	}
}
