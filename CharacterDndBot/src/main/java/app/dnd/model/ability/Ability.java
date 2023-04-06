package app.dnd.model.ability;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.dnd.model.Refreshable;
import app.dnd.model.ability.features.ActiveFeature;
import app.dnd.model.ability.features.Feature;
import lombok.Data;

@Data
@Document(collection = "ability")
public class Ability implements Refreshable {

	@Id
	private Long id;
	private List<Feature> features;
	private List<Feature> traits;
	private List<Feature> feats;

	public static Ability build(Long id) {
		Ability ability = new Ability();
		ability.traits = new ArrayList<>();
		ability.feats =	new ArrayList<>();	
		ability.features = new ArrayList<>();
		return ability;
	}

	@Override
	public void refresh(Time time) {
		for (Feature feature : features) {
			if (feature instanceof ActiveFeature) {
				ActiveFeature target = (ActiveFeature) feature;
				target.refresh(time);
			}
		}
	}

}
