package app.dnd.dto.characteristics;
 
import app.dnd.dto.Informator;
import lombok.Data;

@Data
public class Lvl implements Informator {
	private int lvl;
	private int experience;
}
