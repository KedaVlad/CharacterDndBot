package app.dnd.service.characterpool;

import org.springframework.beans.factory.annotation.Autowired;

import app.user.model.CharactersPool;

public interface CharacterPoolButton {
	String[][] download(Long id);
}

class CharacterPoolButtonBuilder implements CharacterPoolButton {

	@Autowired
	private CharactersPoolService charactersPoolService;
	
	@Override
	public String[][] download(Long id) {
		CharactersPool charactersPool = charactersPoolService.getById(id);
		String[][] buttons = new String[charactersPool.getSavedCharacters().size()][1];
		int i = 0;
		for (String character : charactersPool.getSavedCharacters().keySet()) {
			buttons[i][0] = charactersPool.getSavedCharacters().get(character).getCharacter().getName();
			i++;
		}
		return buttons;
	}

}
