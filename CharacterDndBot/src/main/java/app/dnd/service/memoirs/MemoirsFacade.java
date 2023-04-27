package app.dnd.service.memoirs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.ability.Memoirs;
import app.bot.model.user.ActualHero;

@Component
public class MemoirsFacade implements MemoirsLogic {

	@Autowired
	private MemoirsService memoirsService;

	@Override
	public String memoirsText(ActualHero actualHero) {
		StringBuilder text = new StringBuilder("MY MEMOIRS\n");
		int i = 1;
		for(String string: memoirsService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName()).getMyMemoirs()) {
			text.append(i).append(". ").append(string).append("\n");
			i++;
		}
		return text.toString();
	}

	@Override
	public void addMemoirs(ActualHero actualHero, String text) {
		Memoirs memoirs = memoirsService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		memoirs.getMyMemoirs().add(text);
		memoirsService.save(memoirs);
	}
}
