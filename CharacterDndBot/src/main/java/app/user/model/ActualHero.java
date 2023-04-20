package app.user.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.dnd.model.hero.HeroCloud;
import app.player.model.act.CloudAct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Document("actual_hero")
public class ActualHero {
	@Id
	private Long id;
	private String name;
	private boolean readyToGame;
	private HeroCloud heroCloud;
	private List<CloudAct> cloudsWorked;
	
	public List<CloudAct> getCloudsForSend() {
		List<CloudAct> clouds = new ArrayList<>();
		if(readyToGame) {
			clouds.addAll(heroCloud.getClouds());
			heroCloud.getClouds().clear();
		}
		return clouds;
	}
	
	public CloudAct findActInCloud(String target) {
		for (int i = 0; i < cloudsWorked.size(); i++) {
			if (cloudsWorked.get(i).getName().equals(target)) {
				return cloudsWorked.get(i);
			}
		}
		log.error("CloudsService (findActInCloud) : " + id + " - " + target);
		return null;
	}

	public void clear(Trash trash) {
		
		for(CloudAct act: cloudsWorked) {
			trash.getCircle().addAll(act.getActCircle());
			heroCloud.getClouds().add(act);
		}
		cloudsWorked.clear();
		readyToGame = false;
	}
	
}
