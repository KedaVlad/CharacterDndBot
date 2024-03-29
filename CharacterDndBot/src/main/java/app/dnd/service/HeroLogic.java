package app.dnd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.Refreshable.Time;
import app.dnd.service.ability.logic.AbilityLogic;
import app.dnd.service.attack.AttackLogic;
import app.dnd.service.classes.ClassesLogic;
import app.dnd.service.hp.HpLogic;
import app.dnd.service.lvl.LvlLogic;
import app.dnd.service.memoirs.MemoirsLogic;
import app.dnd.service.race.RaceLogic;
import app.dnd.service.stuff.logic.StuffLogic;
import app.dnd.service.talants.logic.TalantsLogic;
import app.bot.model.user.ActualHero;

public interface HeroLogic {

	AbilityLogic ability();
	TalantsLogic talants();
	AttackLogic attack();
	StuffLogic stuff();
	HpLogic hp();
	MemoirsLogic memoirs();
	LvlLogic lvl();
	RaceLogic race();
	ClassesLogic classes();
	void download(ActualHero actualHero, String heroName);
	void delete(ActualHero actualHero, String string);
	boolean isEmpty(Long id);
	void refresh(ActualHero actualHero, Time time);
	List<String> heroList(Long id);
	String menu(ActualHero actualHero);
}

@Component
class HeroFacade implements HeroLogic {
	
	@Autowired
	private HeroService heroService;
	@Autowired
	private AbilityLogic characteristicLogic;
	@Autowired
	private TalantsLogic talantsLogic;
	@Autowired
	private AttackLogic attackLogic;
	@Autowired
	private StuffLogic stuffLogic;
	@Autowired
	private HpLogic hpLogic;
	@Autowired
	private MemoirsLogic memoirsLogic;
	@Autowired
	private LvlLogic lvlLogic;
	@Autowired
	private RaceLogic raceLogic;
	@Autowired
	private ClassesLogic classesLogic;
	
	@Override
	public AbilityLogic ability() {
		return characteristicLogic;
	}
	
	@Override
	public TalantsLogic talants() {
		return talantsLogic;
	}

	@Override
	public AttackLogic attack() {
		return attackLogic;
	}

	@Override
	public void download(ActualHero actualHero, String heroName) {
		heroService.download(actualHero, heroName);
	}

	@Override
	public void delete(ActualHero actualHero, String heroName) {
		heroService.delete(actualHero, heroName);
	}

	@Override
	public boolean isEmpty(Long id) {
		return heroService.isEmpty(id);
	}

	@Override
	public List<String> heroList(Long id) {
		return heroService.heroList(id);
	}

	@Override
	public void refresh(ActualHero actualHero, Time time) {
		heroService.refresh(actualHero, time);
		
	}

	@Override
	public StuffLogic stuff() {
		return stuffLogic;
	}

	@Override
	public String menu(ActualHero actualHero) {
		return heroService.mainMenu(actualHero);
	}

	@Override
	public HpLogic hp() {
		return hpLogic;
	}

	@Override
	public MemoirsLogic memoirs() {
		return memoirsLogic;
	}

	@Override
	public LvlLogic lvl() {
		return lvlLogic;
	}

	@Override
	public RaceLogic race() {
		return raceLogic;
	}

	@Override
	public ClassesLogic classes() {
		return classesLogic;
	}
}


