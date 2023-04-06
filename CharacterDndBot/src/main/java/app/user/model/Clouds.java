package app.user.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.player.model.act.ActiveAct;
import app.player.model.act.SingleAct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
@Data
@Slf4j
@Document(collection = "clouds")
public class Clouds {
	
	@Id
	private Long id;
	private List<SingleAct> cloudsTarget = new ArrayList<>();
	private List<SingleAct> cloudsWorked = new ArrayList<>();

	public int cloudsValue() {
		return cloudsTarget.size() + cloudsWorked.size();
	}
	
	public ActiveAct findActInCloud(String target) {
		for (int i = 0; i < cloudsWorked.size(); i++) {
			if (cloudsWorked.get(i).getName().equals(target)) {
				return cloudsWorked.get(i);
			}
		}
		log.error("CloudsService (findActInCloud) : " + id + " - " + target);
		return null;
	}
}
