package app.dnd.dto;
 
import java.io.Serializable;

import app.dnd.util.math.Formalizer.Roll;
import lombok.Data;

@Data
public class ClassDnd implements Serializable{ 
	
	private static final long serialVersionUID = 1L;
	private String className;
	private String archetype;
	private int lvl;
	private Roll diceHp;
	private int firstHp;
	
}
