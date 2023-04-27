package app.dnd.model.telents.features;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.dnd.model.Refreshable;
import lombok.Data;

@Data
@Document(collection = "features")
public class Features implements Refreshable {

	@Id
	private String mongoId;
	private Long userId;
	private String ownerName;
	private List<Feature> byClasses;
	private List<Feature> byRace;
	private List<Feature> feats;

	public static Features build(Long id, String ownerName) {
		Features features = new Features();
		features.setUserId(id);
		features.setOwnerName(ownerName);
		features.byClasses = new ArrayList<>();
		features.feats = new ArrayList<>();
		features.byRace = new ArrayList<>();
		return features;
	}

	@Override
	public void refresh(Time time) {
		for (Feature feature : byClasses) {
			if (feature instanceof ActiveFeature target) {
				target.refresh(time);
			}
		}
	}

}
