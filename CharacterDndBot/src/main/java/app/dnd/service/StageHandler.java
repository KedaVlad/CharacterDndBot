package app.dnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.PoolActions;
import app.dnd.model.actions.SingleAction;
import app.dnd.service.ability.stage.AbilityAction;
import app.dnd.service.attack.AttackAction;
import app.dnd.service.classes.ClassAction;
import app.dnd.service.hp.HpAction;
import app.dnd.service.memoirs.MemoirsAction;
import app.dnd.service.race.RaceAction;
import app.dnd.service.stuff.stage.StuffAction;
import app.dnd.service.talants.stage.TalantsAction;
import app.dnd.util.ArrayToColumns;
import app.player.model.Stage;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.bot.model.user.ActualHero;

public interface StageHandler {

	AbilityAction ability();
	TalantsAction talents();
	AttackAction attack();
	Stage create(Long id);
	Stage heroList(Long id);
	MemoirsAction memoirs();
	StuffAction stuff();
	Stage menu(ActualHero actualHero);
	RaceAction race();
	ClassAction classes();
	HpAction hp();


}


@Component
class StageController implements StageHandler {

	@Autowired
	public AbilityAction abilityAction;
	@Autowired
	public TalantsAction talantsAction;
	@Autowired
	public AttackAction attackAction;
	@Autowired
	public HeroLogic heroLogic;
	@Autowired
	public MemoirsAction memoirsAction;
	@Autowired
	public StuffAction stuffAction;
	@Autowired
	public RaceAction raceAction;
	@Autowired
	public ClassAction classAction;
	@Autowired
	public HpAction hpAction;
	@Autowired
	public ArrayToColumns arrayToColumns;


	@Override
	public TalantsAction talents() {
		return talantsAction;
	}

	@Override
	public AttackAction attack() {
		return attackAction;
	}

	@Override
	public AbilityAction ability() {
		return abilityAction;
	}

	@Override
	public MemoirsAction memoirs() {
		return memoirsAction;
	}
	
	@Override
	public RaceAction race() {
		return raceAction;
	}
	
	@Override
	public Stage create(Long id) {

		String text;
		if(heroLogic.isEmpty(id)) {
			text = "You don't have a Hero yet, my friend. But after you " + Button.CREATE.NAME
					+ " them, you can find them here.";
		} else {
			text = "Choose the Hero or " + Button.CREATE.NAME + " new one.";
		}
		return PoolActions.builder()
				.actionsPool(new SingleAction[][]{{Action.builder()
					.name(Button.CREATE.NAME)
					.location(Location.CHARACTER_FACTORY)
					.build()}})
				.text(text)
				.build();
	}

	@Override
	public Stage heroList(Long id) {
		return Action.builder()
				.location(Location.DOWNLOAD_OR_DELETE)
				.text("Your Heroes")
				.buttons(arrayToColumns.rebuild(heroLogic.heroList(id).toArray(String[]::new), 1, String.class))
				.build();
	}

	@Override
	public StuffAction stuff() {
		return stuffAction;
	}

	@Override
	public Stage menu(ActualHero actualHero) {
		Action[][] pool = new Action[][] {
			{ 
				Action.builder().name(Button.TALENTS.NAME).location(Location.TALENT).build(),
				Action.builder().name(Button.ABILITY.NAME).location(Location.ABILITY).build(),
				Action.builder().name(Button.STUFF.NAME).location(Location.STUFF).build()
			},
			{ 
				Action.builder().name(Button.ROLLS.NAME).location(Location.ROLLS).build(),
				Action.builder().name(Button.DEBUFF.NAME).location(Location.DEBUFF).build()
			},
			{ 
				Action.builder().name(Button.REST.NAME).location(Location.REST).build(),
				Action.builder().name(Button.MEMOIRS.NAME).location(Location.MEMOIRS).build() 
			}};


			return PoolActions.builder()
					.actionsPool(pool)
					.text(heroLogic.menu(actualHero))
					.build();

	}

	@Override
	public ClassAction classes() {
		return classAction;
	}

	@Override
	public HpAction hp() {
		return hpAction;
	}

	

}




