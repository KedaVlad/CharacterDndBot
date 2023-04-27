package app.player.model.act;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.bot.model.button.ButtonModel;
import app.bot.model.message.MessageModel;
import app.player.model.Key;
import app.player.model.Stage;
import app.player.model.enums.Button;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeName("cloud_act")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public class CloudAct extends ActiveAct {

	private Stage stage;
	private static final String[] elimination = new String[] {Button.ELIMINATION.NAME};
	
	public static CloudActBuilder builder() {
		return new CloudActBuilder();
	}
	
	@Override
	public MessageModel[] getMessage() {
		
		ButtonModel buttonModel;
		if(stage.hasButtons()) {
			String[][] buttons = stage.buildButton();
			String[][] newButtons = Arrays.copyOf(buttons, buttons.length + 1);
			newButtons[buttons.length] = Arrays.copyOf(elimination, elimination.length);
			
			buttonModel = ButtonModel.builder()
					.key(Key.CLOUD.KEY)
					.button(newButtons)
					.build();
		} else {
			buttonModel = ButtonModel.builder()
					.key(Key.CLOUD.KEY)
					.button(new String[][]{elimination})
					.build();
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


}
