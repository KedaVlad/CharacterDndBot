package app.bot.model;

public class ButtonModelBuilder {

	private String[][] button;
	private Integer key;
	
	public ButtonModelBuilder button(String[][] button) {
		this.button = button;
		return this;
	}
	
	public ButtonModelBuilder key(Integer key) {
		this.key = key;
		return this;
	}
	
	public ButtonModel build() { 
	
		ButtonModel buttonModel = new ButtonModel();
		buttonModel.setButton(button);
		buttonModel.setKey(key);
		return buttonModel;
	}
}
