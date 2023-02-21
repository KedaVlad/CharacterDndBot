package app.dnd.dto.ability.features;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Trait extends Feature {
 
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;

}
