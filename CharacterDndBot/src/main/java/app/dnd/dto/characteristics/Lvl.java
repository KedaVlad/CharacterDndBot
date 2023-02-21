package app.dnd.dto.characteristics;
 
import java.io.Serializable;

import lombok.Data;

@Data
public class Lvl implements Serializable {

	private static final long serialVersionUID = 1L;
	private int lvl;
	private int experience;
	private final int[] expPerLvl = { 0, 300, 900, 2700, 6500, 14000, 23000, 34000, 48000, 64000, 85000, 100000, 120000,
			140000, 165000, 195000, 225000, 265000, 305000, 355000 };

}
