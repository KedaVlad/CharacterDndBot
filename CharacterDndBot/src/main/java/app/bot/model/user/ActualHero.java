package app.bot.model.user;

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
        List<CloudAct> acts = new ArrayList<>();
		if(readyToGame) {
            acts.addAll(heroCloud.getClouds());
			cloudsWorked.addAll(heroCloud.getClouds());
			heroCloud.getClouds().clear();
		}
		return acts;
	}
	
	public CloudAct findActInCloud(String target) {
		for (CloudAct cloudAct : cloudsWorked) {
			if (cloudAct.getName().equals(target)) {
                log.info(target + " == " + cloudAct.getName());
				return cloudAct;
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
