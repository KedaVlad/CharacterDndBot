package app.dnd.service.characterpool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.player.model.enums.Button;
import app.player.model.enums.Location;

public interface CharacterPoolAction {

	BaseAction create();

	BaseAction download(Long id);
	
}

@Component
class CharacterPoolActor implements CharacterPoolAction {

	@Autowired
	public CharacterPoolButton characterPoolButton;
	
	@Override
	public BaseAction create() {
		return Action.builder()
				.location(Location.CHARACTER_FACTORY)
				.buttons(new String[][] { { Button.CREATE.NAME } })
				.replyButtons()
				.build();
	}

	@Override
	public BaseAction download(Long id) {
		return Action.builder()
				.location(Location.DOWNLOAD_OR_DELETE)
				.buttons(characterPoolButton.download(id))
				.build();
	}

}
