package app.dnd.service.logic.talants.prof;

import app.dnd.util.ButtonName;

public interface ProficienciesButton {

	public String[][] possession(Long id);
	public String[][] create();
}

class ProficienciesButtonBuilder implements ProficienciesButton {

	@Override
	public String[][] possession(Long id) {
		return new String[][]{{"Add possession"},{ButtonName.RETURN_TO_MENU}};
	}

	@Override
	public String[][] create() {
		return new String[][] {{"Hint list"},{"Return to abylity"}};
	}
	
}
