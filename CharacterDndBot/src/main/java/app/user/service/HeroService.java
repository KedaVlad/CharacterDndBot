package app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dnd.model.hero.Hero;
import app.dnd.service.characterpool.CharactersPoolService;
import app.dnd.service.data.ClassesDndService;
import app.dnd.service.data.HpService;
import app.dnd.service.data.LvlService;
import app.dnd.service.data.MemoirsService;
import app.dnd.service.data.RaceDndService;
import app.dnd.service.data.StuffService;
import app.dnd.service.data.WalletService;
import app.dnd.service.logic.attack.AttackAbilityService;
import app.dnd.service.logic.characteristic.CharacteristicsService;
import app.dnd.service.logic.talants.feature.AbilityService;
import app.dnd.service.logic.talants.magicsoul.MagicSoulService;
import app.dnd.service.logic.talants.prof.ProficienciesService;
import app.player.model.act.ActiveAct;
import app.user.model.ActualHero;
import app.user.model.CharactersPool;
import app.user.model.Clouds;
import app.user.model.Trash;
import app.user.model.User;

@Service
public class HeroService {
	
	@Autowired
	private CharactersPoolService charactersPoolService;
	@Autowired
	private ActualHeroService actualHeroService;
	@Autowired
	private CharacteristicsService characteristicsService;
	@Autowired
	private LvlService lvlService;
	@Autowired
	private HpService hpService;
	@Autowired
	private AttackAbilityService attackAbilityService;
	@Autowired
	private ProficienciesService proficienciesService;
	@Autowired
	private AbilityService abilityService;
	@Autowired
	private StuffService stuffService;
	@Autowired
	private WalletService walletService;
	@Autowired
	private RaceDndService raceDndService;
	@Autowired
	private ClassesDndService classesDndService;
	@Autowired
	private MemoirsService memoirsService;
	@Autowired
	private MagicSoulService magicSoulService;
	
	private Hero buildHero(ActualHero actualHero) {
		Hero hero = new Hero();
		hero.setCharacter(actualHero.getCharacter());
		hero.setAbility(abilityService.getById(actualHero.getId()));
		hero.setAttackAbility(attackAbilityService.getById(actualHero.getId()));
		hero.setCharacteristics(characteristicsService.getById(actualHero.getId()));
		hero.setClassesDnd(classesDndService.getById(actualHero.getId()));
		hero.setHp(hpService.getById(actualHero.getId()));
		hero.setLvl(lvlService.getById(actualHero.getId()));
		hero.setMagicSoul(magicSoulService.getById(actualHero.getId()));
		hero.setMemoirs(memoirsService.getById(actualHero.getId()));
		hero.setProficiencies(proficienciesService.getById(actualHero.getId()));
		hero.setRace(raceDndService.getById(actualHero.getId()));
		hero.setStuff(stuffService.getById(actualHero.getId()));
		hero.setWallet(walletService.getById(actualHero.getId()));
		return hero;
	}
	
	public void save(ActualHero actualHero) {
		CharactersPool charactersPool = charactersPoolService.getById(actualHero.getId());
		charactersPool.getSavedCharacters().put(actualHero.getCharacter().getName(), buildHero(actualHero));
		charactersPoolService.save(charactersPool);
	}

	public void delete(Long id, String name) {
		
		CharactersPool charactersPool = charactersPoolService.getById(id);
		ActualHero actualHero = actualHeroService.getById(id);
		if(actualHero.getCharacter() != null && actualHero.getCharacter().getName().equals(name)) {
			actualHero.setCharacter(null);
			actualHeroService.save(actualHero);
		} 
		if(charactersPool.getSavedCharacters().containsKey(name)) {
			charactersPool.getSavedCharacters().remove(name);
			charactersPoolService.save(charactersPool);
		}
	}

	public void download(User user, String name) {

		CharactersPool charactersPool = charactersPoolService.getById(user.getId());
		ActualHero actualHero = user.getActualHero();
		Clouds clouds = user.getClouds();
		Trash trash = user.getTrash();

		if (actualHero.getCharacter() != null) {		
			for(ActiveAct act: clouds.getCloudsWorked()) {
				trash.getCircle().addAll(act.getActCircle());
				act.getActCircle().clear();
			}
			clouds.getCloudsTarget().addAll(clouds.getCloudsWorked());
			clouds.getCloudsWorked().clear();
			actualHero.getCharacter().setClouds(clouds.getCloudsTarget());
			charactersPool.getSavedCharacters().put(actualHero.getCharacter().getName(), buildHero(actualHero));
			charactersPoolService.save(charactersPool);
		}
		
		if (charactersPool.getSavedCharacters().containsKey(name)) {
			Hero hero = charactersPool.getSavedCharacters().get(name);
			actualHero.setCharacter(hero.getCharacter());
			clouds.setCloudsTarget(actualHero.getCharacter().getClouds());
			abilityService.save(hero.getAbility());
			attackAbilityService.save(hero.getAttackAbility());
			characteristicsService.save(hero.getCharacteristics());
			classesDndService.save(hero.getClassesDnd());
			hpService.save(hero.getHp());
			lvlService.save(hero.getLvl());
			memoirsService.save(hero.getMemoirs());
			proficienciesService.save(hero.getProficiencies());
			raceDndService.save(hero.getRace());
			stuffService.save(hero.getStuff());
			walletService.save(hero.getWallet());
			magicSoulService.save(hero.getMagicSoul());
		} 
	}
	
}
