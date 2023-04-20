package app.player.model.act;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.bot.model.ButtonModel;
import app.bot.model.MessageModel;
import app.player.model.Key;
import app.player.model.Stage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonTypeName("array_acts")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class ArrayActs extends TreeAct { 

	private Stage[] stages;

	public static ArrayActsBuilder builder() {
		return new ArrayActsBuilder();
	}

	@Override
	public MessageModel[] getMessage() {
		
		MessageModel[] answer = new MessageModel[stages.length];
		int i = 0;
		int key = Key.ArrayAct.KEY;
		for(Stage stage: stages) {
			ButtonModel buttonModel = null;
			
			if(stage.hasButtons()) {
				buttonModel = ButtonModel.builder()
						.button(stage.buildButton())
						.key(key)
						.build();
			}
			answer[i] = MessageModel.builder()
					.name(getName())
					.text(stage.getText())
					.button(buttonModel)
					.build();
			i++;
			key++;
		}
		return answer;
	}

	@Override
	public Stage continueAct(String string) {
		if(!string.contains("^\\d{4}.*") && this.isReply()) {
			return stages[0].continueStage(string);
		} else {
		int key = Integer.parseInt(string.substring(0, 4)) - Key.ArrayAct.KEY;
		return stages[key].continueStage(string.substring(5));
		}
	}

	@Override
	public boolean replyContain(String target) {
		return isReply() && stages[0].containButton(target);
	}
}