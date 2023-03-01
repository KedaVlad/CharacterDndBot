package app.bot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.dnd.dto.CharacterDnd;
import lombok.Data;

@Data
@Document("hero")
public class ActualHero {
	@Id
	private Long id;
	private CharacterDnd character;
}
