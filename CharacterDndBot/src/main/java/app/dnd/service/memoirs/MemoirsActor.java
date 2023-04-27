package app.dnd.service.memoirs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.player.model.Stage;
import app.player.model.enums.Button;
import app.bot.model.user.ActualHero;

@Component
public class MemoirsActor implements MemoirsAction {

	@Autowired
	private MemoirsLogic memoirsLogic;
	
	@Override
	public Stage menu(ActualHero actualHero) {
		
		return Action.builder()
				.text(memoirsLogic.memoirsText(actualHero))
				.buttons(new String[][]{{Button.RETURN_TO_MENU.NAME}})
				.build();
	}
	
}