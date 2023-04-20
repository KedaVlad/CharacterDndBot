package app.dnd.service.talants.stage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dnd.model.actions.Action;
import app.dnd.model.actions.PoolActions;
import app.dnd.model.actions.SingleAction;
import app.dnd.model.telents.features.ActiveFeature;
import app.dnd.model.telents.features.Feature;
import app.dnd.service.talants.FeaturesService;
import app.dnd.util.ArrayToColumns;
import app.player.model.Stage;
import app.player.model.enums.Button;
import app.player.model.enums.Location;
import app.user.model.ActualHero;

public interface FeatureAction {

	Stage menu(ActualHero hero, Stage stage);

	Stage targetFeature(Stage stage);

}

@Component
class FeatureActor implements FeatureAction {
	
	@Autowired
	private FeaturesButtonBuilder featuresButtonBuilder;

	@Override
	public Stage menu(ActualHero hero, Stage stage) {
		
		Action action = (Action) stage;
		if(action.getAnswers()[0].equals(Button.FEATURE.NAME)) {
			return PoolActions.builder()
					.actionsPool(featuresButtonBuilder.byClasses(hero))
					.text("Here, you can find the features. Talents that are bestowed upon you by your class.")
					.build();
		} else if(action.getAnswers()[0].equals(Button.TRAIT.NAME)) {
			return PoolActions.builder()
					.actionsPool(featuresButtonBuilder.byRace(hero))
					.text("Here, you can find the traits. Talents that are bestowed upon you by your race.")
					.build();
		} else if(action.getAnswers()[0].equals(Button.FEAT.NAME)) {
			return PoolActions.builder()
					.actionsPool(featuresButtonBuilder.feats(hero))
					.text("Here, you can find the feats.")
					.build();
		}
		
		return Action.builder().text("Hello error").build();
	}

	@Override
	public Stage targetFeature(Stage stage) {
		Action action = (Action) stage;
		Feature feature = (Feature) action.getObjectDnd();
		if(feature instanceof ActiveFeature) {			
			action.setButtons(new String[][] {{"Cast"}});
		}
		action.setText(feature.getName() + "\n" + feature.getDescription());
		return action;
	}
	
	
}

@Component
class FeaturesButtonBuilder {

	@Autowired
	private FeaturesService featuresService;
	@Autowired
	private ArrayToColumns arrayToColumns;

	private SingleAction[][] featuresToColumns(List<Feature> list) {
		SingleAction[] all = new SingleAction[list.size()];
		for(int i = 0; i < list.size(); i++) {
			all[i] = Action.builder()
					.name(list.get(i).getName())
					.objectDnd(list.get(i))
					.location(Location.TELENT)
					.build();
		}
		return arrayToColumns.rebuild(all, 1, SingleAction.class);
	}

	public SingleAction[][] byClasses(ActualHero hero) {
		return featuresToColumns(featuresService.findByIdAndOwnerName(hero.getId(), hero.getName()).getByClasses());
	}

	public SingleAction[][] feats(ActualHero hero) {
		return featuresToColumns(featuresService.findByIdAndOwnerName(hero.getId(), hero.getName()).getFeats());
	}

	public SingleAction[][] byRace(ActualHero hero) {
		return featuresToColumns(featuresService.findByIdAndOwnerName(hero.getId(), hero.getName()).getByRace());
	}

	public String[][] singleFeature(Feature feature) {
		
		if (feature instanceof ActiveFeature) {
			return new String[][] {{"Cast"}};
		} else {
			return null;
		}
	}

}