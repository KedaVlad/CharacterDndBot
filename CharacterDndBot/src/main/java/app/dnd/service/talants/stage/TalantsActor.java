package app.dnd.service.talants.stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.BaseAction;
import app.dnd.model.telents.features.Features;
import app.dnd.model.telents.spells.MagicSoul;
import app.dnd.service.talants.FeaturesService;
import app.dnd.service.talants.MagicSoulService;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.user.model.ActualHero;

@Component
public class TalantsActor implements TalantsAction {

	@Autowired
	private FeatureAction featureAction;
	@Autowired
	private MagicSoulAction magicSoulAction;
	@Autowired
	private ProfAction profAction;
	@Autowired
	private TalantsButtonBuilder talantsButtonBuilder;
	
	
	@Override
	public BaseAction menu(ActualHero hero) {
		return Action.builder()
				.buttons(talantsButtonBuilder.menu(hero))
				.text("Here, you can access and manage your character's ta talents, including " 
						+ Button.TRAIT.NAME + ", " + Button.FEATURE.NAME + ", " + Button.FEAT.NAME + ", " + Button.POSSESSION.NAME 
						+ " and " + Button.SPELL.NAME)
				.location(Location.TELENT)
				.build();
	}


	@Override
	public MagicSoulAction magic() {
		return magicSoulAction;
	}

	@Override
	public ProfAction prof() {
		return profAction;
	}


	@Override
	public FeatureAction feature() {
		return featureAction;
	}
	
}

@Component
class TalantsButtonBuilder {
	
	@Autowired
	public FeaturesService abilityService;
	@Autowired
	public MagicSoulService magicSoulService;
	

	public String[][] menu(ActualHero hero) {
		
		Features ability = abilityService.findByIdAndOwnerName(hero.getId(), hero.getName());
		MagicSoul magicSoul = magicSoulService.findByIdAndOwnerName(hero.getId(), hero.getName());
		
		if (magicSoul.getCells() == null && ability.getFeats().size() == 0) {
			return new String[][] {{Button.TRAIT.NAME, Button.FEATURE.NAME, Button.POSSESSION.NAME }, { Button.RETURN_TO_MENU.NAME }};
		} else if(magicSoul.getCells() == null) {
			return new String[][] {{ Button.FEATURE.NAME, Button.POSSESSION.NAME}, {Button.TRAIT.NAME, Button.FEAT.NAME}, { Button.RETURN_TO_MENU.NAME }};
		} else if(ability.getFeats().size() == 0) {
			return new String[][] {{ Button.TRAIT.NAME, Button.POSSESSION.NAME }, {Button.FEATURE.NAME, Button.SPELL.NAME}, { Button.RETURN_TO_MENU.NAME }};
		} else {
			return new String[][] {{ Button.TRAIT.NAME, Button.FEATURE.NAME, Button.FEAT.NAME}, { Button.POSSESSION.NAME, Button.SPELL.NAME}, { Button.RETURN_TO_MENU.NAME }};
		}
	}

}
