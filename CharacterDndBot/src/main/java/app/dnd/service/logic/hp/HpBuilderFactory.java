package app.dnd.service.logic.hp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dnd.model.hero.CharacterDnd;
import app.dnd.model.hero.ClassDnd;

@Service
public class HpBuilderFactory {

	@Autowired
	private HpStableBuilder hpStableBuilder;
	@Autowired
	private HpRandomBuilder hpRandomBuilder;
	
	public HpStableBuilder stable() {
		return hpStableBuilder;
	}
	
	public HpRandomBuilder random() {
		return hpRandomBuilder;
	}
}

interface HpBuilder {
	public abstract int buildForLvlUp(CharacterDnd character, ClassDnd clazz);
	public abstract int buildBase(CharacterDnd character);
}

