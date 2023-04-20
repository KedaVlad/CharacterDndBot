package app.dnd.service.memoirs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.Memoirs;
import app.user.model.ActualHero;

@Component
public class MemoirsFacade implements MemoirsLogic {

	@Autowired
	private MemoirsService memoirsService;

	@Override
	public String memoirsText(ActualHero actualHero) {
		String text = "MY MEMOIRS\n";
		int i = 1;
		for(String string: memoirsService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName()).getMyMemoirs()) {
			text += i + ". " + string + "\n";
			i++;
		}
		return text;
	}

	@Override
	public void addMemoirs(ActualHero actualHero, String text) {
		Memoirs memoirs = memoirsService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		memoirs.getMyMemoirs().add(text);
		memoirsService.save(memoirs);
	}
}
