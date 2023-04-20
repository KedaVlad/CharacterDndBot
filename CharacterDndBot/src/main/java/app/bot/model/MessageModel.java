package app.bot.model;

import lombok.Data;

@Data
public class MessageModel {

	private String name;
	private String text;
	private ButtonModel buttonModel;
	
	public static MessageModelBuilder builder() {
		return new MessageModelBuilder();
	}
	
	public boolean hasButton() {
		return buttonModel != null && buttonModel.getButton() != null; 
	}
}
