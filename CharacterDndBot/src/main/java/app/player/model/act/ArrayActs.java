package app.player.model.act;

import app.bot.model.ButtonModel;
import app.bot.model.MessageModel;
import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ArrayActs extends ActiveAct { 

	private SingleAct[] pool;
	private long[] keys;

	public static ArrayActsBuilder builder() {
		return new ArrayActsBuilder();
	}

	public SingleAct getTarget(long key) {
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] == key) {
				return pool[i];
			}
		}
		return null;
	}

	@Override
	public boolean hasAction() {
		return pool[0].getAction() != null;
	}

	@Override
	public boolean hasMediator() {
		return false;
	}

	@Override
	public boolean hasReply(String string) {
		return pool[0].getAction() != null && pool[0].getAction().replyContain(string);
	}

	@Override
	public boolean hasCloud() {
		return false;
	}

	@Override
	public BaseAction getAction() {
		return Action.builder().build();
	}

	@Override
	public MessageModel[] getMessage() {
		
		MessageModel[] answer = new MessageModel[pool.length];
		int i = 0;
		int key = 10001;
		for(SingleAct act: pool) {
			ButtonModel buttonModel = null;
			if(act.hasAction() && act.getAction().hasButtons()) {
				buttonModel = ButtonModel.builder()
						.button(act.getAction().buildButtons())
						.key(key)
						.build();

			} 

			answer[i] = MessageModel.builder()
					.name(getName())
					.text(act.getText())
					.button(buttonModel)
					.build();
			i++;
			key++;
		}
		return answer;
	}
}