package app.bot.model;

public class MessageModelBuilder {

	private String name;
	private String text;
	private ButtonModel buttonModel;
	
	public MessageModelBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public MessageModelBuilder text(String text) {
		this.text = text;
		return this;
	}
	
	public MessageModelBuilder button(ButtonModel buttonModel) {
		this.buttonModel = buttonModel;
		return this;
	}
	
	public MessageModel build() {
		MessageModel messageModel = new MessageModel();
		messageModel.setName(name);
		messageModel.setText(text);
		messageModel.setButtonModel(buttonModel);
		return messageModel;
	}
}
