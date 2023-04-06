package app.player.model.act;

import app.bot.model.ButtonModel;
import app.bot.model.MessageModel;
import app.dnd.model.actions.BaseAction;
import app.player.model.Key;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SingleAct extends ActiveAct {

	private BaseAction action;
	private String text;

	public static SingleActBuilder builder() {
		return new SingleActBuilder();
	}

	@Override
	public boolean hasAction() {
		return action != null;
	}

	@Override
	public boolean hasMediator() {
		return action != null && action.isMediator();
	}

	@Override
	public boolean hasReply(String string) {
		return action != null && action.replyContain(string);
	}

	@Override
	public boolean hasCloud() {
		return action != null && action.isCloud();
	}

	@Override
	public BaseAction getAction() {
		return action;
	}

	@Override
	public MessageModel[] getMessage() {

		ButtonModel buttonModel = null;
		if(hasAction() && action.hasButtons()) {
			buttonModel = ButtonModel.builder()
					.button(action.buildButtons())
					.build();
			if(action.isCloud()) {
				buttonModel.setKey(Key.CLOUD.KEY);
			} else if(!action.isReplyButtons()) {
				buttonModel.setKey(Key.TREE.KEY);
			}
		} 

		return new MessageModel[] { MessageModel.builder()
				.name(getName())
				.text(text)
				.button(buttonModel)
				.build()};
	}

}
