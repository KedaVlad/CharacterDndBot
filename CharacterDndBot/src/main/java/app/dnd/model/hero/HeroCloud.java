package app.dnd.model.hero;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.player.model.act.CloudAct;
import lombok.Data;

@Document("heroCloud")
@Data
public class HeroCloud {

	@Id
	private String mongoId;
	private Long userId;
	private String ownerName;
	private List<CloudAct> clouds;
	
	public static HeroCloud build(Long id, String ownerName) {
		HeroCloud target = new HeroCloud();
		target.setUserId(id);
		target.setOwnerName(ownerName);
		target.setClouds(new ArrayList<>());
		return target;
	}
	
}
