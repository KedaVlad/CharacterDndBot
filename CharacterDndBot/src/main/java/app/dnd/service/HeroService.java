package app.dnd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.dnd.model.Refreshable.Time;
import app.dnd.model.ability.Ability;
import app.dnd.model.ability.Hp;
import app.dnd.model.ability.Lvl;
import app.dnd.model.enums.Armors;
import app.dnd.model.enums.Stats;
import app.dnd.model.hero.ClassDnd;
import app.dnd.model.hero.ClassesDnd;
import app.dnd.model.hero.RaceDnd;
import app.dnd.model.stuffs.Stuff;
import app.dnd.model.telents.proficiency.Proficiencies;
import app.dnd.service.ability.AbilityService;
import app.dnd.service.attack.AttackAbilityService;
import app.dnd.service.classes.ClassesDndService;
import app.dnd.service.herocloud.HeroCloudService;
import app.dnd.service.hp.HpService;
import app.dnd.service.lvl.LvlService;
import app.dnd.service.memoirs.MemoirsService;
import app.dnd.service.race.RaceDndService;
import app.dnd.service.stuff.StuffService;
import app.dnd.service.stuff.WalletService;
import app.dnd.service.talants.FeaturesService;
import app.dnd.service.talants.MagicSoulService;
import app.dnd.service.talants.ProficienciesService;
import app.bot.model.user.ActualHero;

@Service
public class HeroService {

	@Autowired
	private HeroCloudService heroCloudService;
	@Autowired
	private AbilityService abilityService;
	@Autowired
	private LvlService lvlService;
	@Autowired
	private HpService hpService;
	@Autowired
	private AttackAbilityService attackAbilityService;
	@Autowired
	private ProficienciesService proficienciesService;
	@Autowired
	private FeaturesService featuresService;
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
	@Autowired
	private AcBuilder acBuilder;

	public void delete(ActualHero actualHero, String name) {

		if(actualHero.getName().equals(name)) {
			actualHero.getCloudsWorked().clear();
			actualHero.setHeroCloud(null);
			actualHero.setReadyToGame(false);
		}

		heroCloudService.deleteByIdAndOwnerName(actualHero.getId(), name);
		featuresService.deleteByIdAndOwnerName(actualHero.getId(), name);
		abilityService.deleteByIdAndOwnerName(actualHero.getId(), name);
		attackAbilityService.deleteByIdAndOwnerName(actualHero.getId(), name);
		classesDndService.deleteByIdAndOwnerName(actualHero.getId(), name);
		hpService.deleteByIdAndOwnerName(actualHero.getId(), name);
		lvlService.deleteByIdAndOwnerName(actualHero.getId(), name);
		memoirsService.deleteByIdAndOwnerName(actualHero.getId(), name);
		proficienciesService.deleteByIdAndOwnerName(actualHero.getId(), name);
		raceDndService.deleteByIdAndOwnerName(actualHero.getId(), name);
		stuffService.deleteByIdAndOwnerName(actualHero.getId(), name);
		walletService.deleteByIdAndOwnerName(actualHero.getId(), name);
		magicSoulService.deleteByIdAndOwnerName(actualHero.getId(), name);
	}

	public void download(ActualHero actualHero, String name) {

		if(actualHero.getHeroCloud() != null) {
			heroCloudService.save(actualHero.getHeroCloud());
		}
		actualHero.setName(name);
		actualHero.setHeroCloud(heroCloudService.findByIdAndOwnerName(actualHero.getId(), name));
	}

	public boolean isEmpty(Long id) {
		return heroCloudService.countHeroesById(id) == 0;	
	}

	public List<String> heroList(Long id) {
		return heroCloudService.findDistinctOwnerNameById(id);
	}

	public void refresh(ActualHero actualHero, Time time) {
		hpService.refresh(actualHero.getId(), actualHero.getName(), time);
		
	} 
	
	public String mainMenu(ActualHero actualHero) {
		
		RaceDnd race = raceDndService.findByUserIdAndOwnerName(actualHero.getId(), actualHero.getName());
		ClassesDnd classes = classesDndService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		Proficiencies proficiencies = proficienciesService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		Lvl lvl = lvlService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		Hp hp = hpService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		Stuff stuff = stuffService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		Ability ability = abilityService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());

		return new StringBuilder().append(actualHero.getName())
				.append("\n\n")
				.append("Race: ").append(race.getRaceName()).append(" (").append(race.getSubRace()).append(")\r\n")
				.append("Class: ").append(classes.getDndClass().get(0).getClassName()).append(" (").append(classes.getDndClass().get(0).getArchetype()).append(")\r\n")
				.append("PROFICIENCY BONUS : ").append(proficiencies.getProficiency()).append("\r\n").append(lvl.getShortInfo()).append("\r\n").append(hp.shortInfo()).append("\r\n").append(acBuilder.getAC(stuff, ability, classes)).append("\r\n")
				.append("SPEED: ").append(race.getSpeed()).toString();

	}
}

@Component
class AcBuilder {

	public String getAC(Stuff stuff, Ability ability, ClassesDnd classes) {
		
		StringBuilder answer = new StringBuilder();
		if (stuff.getWeared()[0] == null) {
			if (barbarianCheck(classes)) {
				answer.append(10 + ability.modification(Stats.DEXTERITY) + ability.modification(Stats.CONSTITUTION));
			} else {
				answer.append(10 + ability.modification(Stats.DEXTERITY));
			}
		} else {
			int armor;
			Armors type = stuff.getWeared()[0].getType();
			armor = type.getBaseArmor() + ability.modification(Stats.DEXTERITY);
			answer.append(Math.min(armor, type.getStatDependBuff()));

			if (stuff.getWeared()[1] != null) {
				answer.append("(+").append(stuff.getWeared()[1].getType().getBaseArmor()).append(")");
			}
		}
		return answer.toString();
	}

	private boolean barbarianCheck(ClassesDnd classes) {
		for (ClassDnd clazz : classes.getDndClass()) {
			if (clazz.getClassName().equalsIgnoreCase("barbarian")) {
				return true;
			}
		}
		return false;
	}
}

