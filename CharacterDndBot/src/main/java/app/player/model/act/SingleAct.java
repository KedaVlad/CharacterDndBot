package app.player.model.act;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.bot.model.button.ButtonModel;
import app.bot.model.message.MessageModel;
import app.player.model.Key;
import app.player.model.Stage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonTypeName("single_act")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public class SingleAct extends TreeAct {

	private Stage stage;

	public static SingleActBuilder builder() {
		return new SingleActBuilder();
	}

	@Override
	public MessageModel[] getMessage() {

		ButtonModel buttonModel = null;
		if(stage.hasButtons()) {
			buttonModel = new ButtonModel();
			buttonModel.setButton(stage.buildButton());
			if(!isReply()) {
				buttonModel.setKey(Key.TREE.KEY);
			}
		} 

		return new MessageModel[] { MessageModel.builder()
				.name(getName())
				.text(stage.getText())
				.button(buttonModel)
				.build()};
	}


	@Override
	public Stage continueAct(String string) {
		return stage.continueStage(string);
	}

	@Override
	public boolean replyContain(String target) {
		return isReply() && stage.containButton(target);
	}

}
