package app.bot.model.user;
 
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.dnd.dto.CharacterDnd;
import lombok.Data;

@Data
@Document("character_conteiner")
public class CharactersPool {

	@Id
	private Long id;
	private Map<String, CharacterDnd> savedCharacters = new LinkedHashMap<>();
	
}