package app.bot.model.button;

import lombok.Data;

@Data
public class ButtonModel {

	private String[][] button;
	private Integer key;
	
	public static ButtonModelBuilder builder() {
		return new ButtonModelBuilder();
	}

	public boolean isReply() {
		return key == null;
	}
}
