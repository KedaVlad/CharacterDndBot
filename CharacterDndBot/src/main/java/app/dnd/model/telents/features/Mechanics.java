package app.dnd.model.telents.features;

import app.dnd.model.telents.attacks.AttackModification;
import app.dnd.util.math.Dice;
import app.dnd.util.pools.Matrix;
import app.dnd.util.pools.SimplePool;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Mechanics extends Feature {
 
	private SimplePool<Feature> pool;
	private Matrix matrix;
	private Dice dice;
	private long key;
	private AttackModification[] modification;

}
