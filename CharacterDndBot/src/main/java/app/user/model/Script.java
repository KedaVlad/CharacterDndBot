package app.user.model;

import java.util.LinkedList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.telegram.telegrambots.meta.api.objects.Message;

import app.player.model.act.ActiveAct;
import lombok.Data;

@Data
@Document("script")
public class Script { 
	
	@Id
	private Long id;
	private LinkedList<ActiveAct> mainTree = new LinkedList<>();

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

	public ActiveAct getActByTargeting(String target, Trash trash) {
		targeting(target, trash);
		return mainTree.getLast();
	}

	public ActiveAct getActByMassageTargeting(Message message, Trash trash) {

		if (findReply(message.getText(), trash) || findMediator(trash)) {
			mainTree.getLast().getActCircle().add(message.getMessageId());
			return mainTree.getLast();
		} else {
			return null;
		}
	}

	private boolean findReply(String target, Trash trash) {
		for (int i = mainTree.size() - 1; i > 0; i--) {
			if (mainTree.get(i).hasReply(target)) {
				backTo(i, trash);
				return true;
			}
		}
		return false;
	}

	private boolean findMediator(Trash trash) {

		if (mainTree.getLast().hasMediator()) {
			return true;
		} else if (mainTree.size() > 1
				&& mainTree.get(mainTree.size() - 2).hasMediator()) {
			trash.getCircle().addAll(mainTree.getLast().getActCircle());
			mainTree.removeLast();
			return true;
		} else {
			return false;
		}
	}
	
}


