package app.player.service.stage.event.factory.comandreader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dnd.model.ObjectDnd;
import app.dnd.model.ability.features.Feature;
import app.dnd.model.ability.features.InerFeature;
import app.dnd.model.ability.proficiency.Possession;
import app.dnd.model.comands.AddComand;
import app.dnd.model.comands.CloudComand;
import app.dnd.model.comands.InerComand;
import app.dnd.model.comands.UpComand;
import app.dnd.model.hero.CharacterDnd;

@Service
public class RaceComandReader {

	@Autowired
	private CloudExecutor cloudExecutor;
	@Autowired
	private UpComandExecutor upComandExecutor;
	

	public void execute(CharacterDnd character, InerComand comand) {
		if (comand instanceof AddComand) {
			add(character, (AddComand) comand);
		} else if (comand instanceof UpComand) {
			upComandExecutor.up(character, (UpComand) comand);
		} else if (comand instanceof CloudComand) {
			cloudExecutor.cloud(character, (CloudComand) comand);
		}
	}

	private void add(CharacterDnd character, AddComand comand) {
		ObjectDnd[] objects = comand.getTargets();
		for (ObjectDnd object : objects) {
			if(object instanceof InerComand) {
				execute(character, (InerComand) object);
			} else if (object instanceof Feature) {
				character.getAbility().getTraits().add((Feature) object);
				if (object instanceof InerFeature) {
					InerFeature target = (InerFeature) object;
					for (InerComand inerComand : target.getComand()) {
						execute(character, inerComand);
					}
				}
			} else if (object instanceof Possession) {
				Possession target = (Possession) object;
				character.getAbility().getProficiencies().getPossessions().add(target);
			}
		}
	}

}
