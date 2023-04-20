package app.dnd.service.ability.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AbilityFacade implements AbilityLogic {

	@Autowired
	private StatLogic statLogic;
	@Autowired
	private SkillLogic skillLogic;
	@Autowired
	private SaveRollLogic saveRollLogic;
	
	@Override
	public StatLogic stat() {
		return statLogic;
	}

	@Override
	public SkillLogic skill() {
		return skillLogic;
	}

	@Override
	public SaveRollLogic saveRoll() {
		return saveRollLogic;
	}
	
}