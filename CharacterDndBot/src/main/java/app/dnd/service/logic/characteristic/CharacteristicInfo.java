package app.dnd.service.logic.characteristic;

import org.springframework.beans.factory.annotation.Autowired;

import app.dnd.service.logic.characteristic.saveroll.SaveRollInfo;
import app.dnd.service.logic.characteristic.skill.SkillInfo;
import app.dnd.service.logic.characteristic.stat.StatInfo;

public interface CharacteristicInfo {
	
	public String menu();
	public StatInfo stat();
	public SkillInfo skill();
	public SaveRollInfo saveRoll();
}

class CharacteristicInformator implements CharacteristicInfo {

	@Autowired
	private StatInfo statInfo;
	@Autowired
	private SkillInfo skillInfo;
	@Autowired
	private SaveRollInfo saveRollInfo;
	
	@Override
	public String menu() {
		return "Here you can operate your abilities, rollig and changing value";
	}
	@Override
	public StatInfo stat() {
		return statInfo;
	}
	@Override
	public SkillInfo skill() {
		return skillInfo;
	}
	@Override
	public SaveRollInfo saveRoll() {
		return saveRollInfo;
	}
	
}
