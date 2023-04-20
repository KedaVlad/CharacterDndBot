package app.dnd.model.telents.features;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.dnd.model.Refreshable;
import lombok.Data;

@Data
@Document(collection = "ability")
public class Features implements Refreshable {

	@Id
	private Long id;
	private String ownerName;
	private List<Feature> byClasses;
	private List<Feature> byRace;
	private List<Feature> feats;

	public static Features build(Long id) {
		Features ability = new Features();
		ability.byRace = new ArrayList<>();
		ability.feats =	new ArrayList<>();	
		ability.byRace = new ArrayList<>();
		return ability;
	}

	@Override
	public void refresh(Time time) {
		for (Feature feature : byClasses) {
			if (feature instanceof ActiveFeature) {
				ActiveFeature target = (ActiveFeature) feature;
				target.refresh(time);
			}
		}
	}

}
