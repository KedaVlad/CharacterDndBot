package app.dnd.service.logic.characteristic.saveroll;

import app.dnd.model.characteristics.SaveRoll;

public interface SaveRollInfo {

	public String menu();

	public String target(SaveRoll article);

}

class SaveRollInformator implements SaveRollInfo {

	@Override
	public String menu() {
		return "Choose Save Roll which you want to roll or change";
	}

	@Override
	public String target(SaveRoll article) {
		return article.getCore().toString();
	}
	
}