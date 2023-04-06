package app.dnd.service.logic.talants.feature;

import org.springframework.stereotype.Component;

import app.dnd.model.ability.features.Feature;

public interface AbilityInfo {
	
    public String feature();
    public String feat();
    public String trait();
    public String descriptionOfFeature(Feature feature);
}

@Component
class AbilityInformator implements AbilityInfo {
	
	@Override
	public String feature() {
		return "Here, you can find the features. Talents that are bestowed upon you by your class.";
	}

	@Override
	public String feat() {
		return "Here, you can find the feats.";
	}

	@Override
	public String trait() {
		return "Here, you can find the traits. Talents that are bestowed upon you by your race.";
	}

	@Override
	public String descriptionOfFeature(Feature feature) {
		return feature.getName() + "\n" + feature.getDescription();
	}

}

