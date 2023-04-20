package app.user.model;

import java.util.LinkedList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import app.player.model.act.ActiveAct;
import app.player.model.act.TreeAct;
import lombok.Data;

@Data
@Document("script_test")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class Script { 
	
	@Id
	private Long id;
	private LinkedList<TreeAct> mainTree = new LinkedList<>();

	public ActiveAct getActByMassageTargeting(Message message, Trash trash) {

		if (findReply(message.getText(), trash) || findMediator(trash)) {
			mainTree.getLast().getActCircle().add(message.getMessageId());
			return mainTree.getLast();
		} else {
			return null;
		}
	}

	public ActiveAct getActByTargeting(String target, Trash trash) {
		targeting(target, trash);
		return mainTree.getLast();
	}

	private boolean findReply(String target, Trash trash) {
	
		for (int i = mainTree.size() - 1; i > 0; i--) {
			if (mainTree.get(i).replyContain(target)) {
				backTo(i, trash);
				return true;
			}
		}
		return false;
	}

	private boolean findMediator(Trash trash) {

		if (mainTree.getLast().isMediator()) {
			return true;
		} else if (mainTree.size() > 1
				&& mainTree.get(mainTree.size() - 2).isMediator()) {
			trash.getCircle().addAll(mainTree.getLast().getActCircle());
			mainTree.removeLast();
			return true;
		} else {
			return false;
		}
	}

	public void clear(Trash trash) {
		backTo(0, trash);
	}
	
	public boolean targeting(String target, Trash trash) {		
		for (int i = 0; i < mainTree.size(); i++) {
			if (mainTree.get(i).getName().equals(target)) {
				backTo(i, trash);
				return true;
			}
		}
		return false;
	}

	public void backTo(int target, Trash trash) {
		for (int j = mainTree.size() - 1; j > target; j--) {
			trash.getCircle().addAll(mainTree.getLast().getActCircle());
			mainTree.removeLast();
		}
	}
	
	public void prepareScript(String actName, Trash trash) {
		if(mainTree.getLast().getName().equals(actName)) {
			trash.getCircle().addAll(mainTree.getLast().getActCircle());
			mainTree.removeLast();
		}
	}
}


