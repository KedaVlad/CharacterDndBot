package app.dnd.dto.ability.proficiency;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
 
import lombok.Data;

@Data
public class Proficiencies implements Serializable{ 
	
	private static final long serialVersionUID = 1L;
	private int proficiency;
	private List<Possession> possessions;

	public enum Proficiency {
		BASE, HALF, COMPETENSE
	}
	
	{
		possessions = new ArrayList<>();
		proficiency = 2;
	}
}