package app.dnd.dto.ability.casts;

import app.dnd.util.math.Dice;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Damage extends Cast {
	private Dice[] damage;
}
