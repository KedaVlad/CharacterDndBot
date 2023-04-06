package app.dnd.service.logic.talants.feature;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.features.ActiveFeature;
import app.dnd.model.ability.features.Feature;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.util.ArrayToColumns;
import app.player.model.enums.Location;

public interface AbilityButtons {

	public BaseAction[][] feature(Long id);
	public BaseAction[][] feat(Long id);
	public BaseAction[][] trait(Long id);
	public String[][] singleFeature(Feature feature);
	
}

@Component
class AbilityButtonBuilder implements AbilityButtons {

	@Autowired
	private AbilityService abilityService;
	@Autowired
	private ArrayToColumns arrayToColumns;

	private BaseAction[][] featuresToColumns(List<Feature> list) {
		BaseAction[] all = new BaseAction[list.size()];
		for(int i = 0; i < list.size(); i++) {
			all[i] = Action.builder()
					.name(list.get(i).getName())
					.objectDnd(list.get(i))
					.location(Location.TALANT)
					.build();
		}
		return arrayToColumns.rebuild(all, 1, BaseAction.class);
	}

	@Override
	public BaseAction[][] feature(Long id) {
		return featuresToColumns(abilityService.getById(id).getFeatures());
	}

	@Override
	public BaseAction[][] feat(Long id) {
		return featuresToColumns(abilityService.getById(id).getFeats());
	}

	@Override
	public BaseAction[][] trait(Long id) {
		return featuresToColumns(abilityService.getById(id).getTraits());
	}

	@Override
	public String[][] singleFeature(Feature feature) {
		
		if (feature instanceof ActiveFeature) {
			return new String[][] {{"Cast"}};
		} else {
			return null;
		}
	}

}