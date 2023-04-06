package app.dnd.service.logic.characteristic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.service.logic.characteristic.saveroll.SaveRollButton;
import app.dnd.service.logic.characteristic.skill.SkillButton;
import app.dnd.service.logic.characteristic.stat.StatButton;
import app.dnd.util.ButtonName;

public interface CharacteristicButton {

	public String[][] menu();
	public StatButton stat();
	public SkillButton skill();
	public SaveRollButton saveRoll();
	
	
}
 
@Component
class CharacteristicButtonBuilder implements CharacteristicButton {

	@Autowired
	private StatButton statButton;
	@Autowired
	private SkillButton skillButton;
	@Autowired
	private SaveRollButton saveRollButton;
	
	@Override
	public String[][] menu() {
		return new String[][]{{ButtonName.STAT_B, ButtonName.SAVE_ROLL_B, ButtonName.SKILL_B},{ButtonName.RETURN_TO_MENU}};
	}

	@Override
	public StatButton stat() {
		return statButton;
	}

	@Override
	public SkillButton skill() {
		return skillButton;
	}

	@Override
	public SaveRollButton saveRoll() {
		return saveRollButton;
	}
	
}