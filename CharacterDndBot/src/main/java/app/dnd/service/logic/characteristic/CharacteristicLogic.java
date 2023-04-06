package app.dnd.service.logic.characteristic;

import app.dnd.service.logic.characteristic.saveroll.SaveRollLogic;
import app.dnd.service.logic.characteristic.skill.SkillLogic;
import app.dnd.service.logic.characteristic.stat.StatLogic;

public interface CharacteristicLogic {

	public StatLogic stat();
	public SkillLogic skill();
	public SaveRollLogic saveRoll();
}
