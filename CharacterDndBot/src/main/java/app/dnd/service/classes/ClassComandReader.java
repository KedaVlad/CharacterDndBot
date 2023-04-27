package app.dnd.service.classes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dnd.model.ObjectDnd;
import app.dnd.model.commands.AddCommand;
import app.dnd.model.commands.CloudCommand;
import app.dnd.model.commands.CommandContainer;
import app.dnd.model.commands.InnerCommand;
import app.dnd.model.commands.UpCommand;
import app.dnd.model.stuffs.items.Items;
import app.dnd.model.telents.attacks.AttackModification;
import app.dnd.model.telents.features.Feature;
import app.dnd.model.telents.features.Features;
import app.dnd.model.telents.features.InnerFeature;
import app.dnd.model.telents.proficiency.Possession;
import app.dnd.model.telents.spells.MagicSoul;
import app.dnd.model.telents.spells.Spell;
import app.dnd.service.inerComand.InerComandExecutor;
import app.dnd.service.talants.FeaturesService;
import app.bot.model.user.ActualHero;

@Service
public class ClassComandReader {

	@Autowired
	private FeaturesService featuresService;
	@Autowired
	private InerComandExecutor inerComandExecutor;


	public void execute(ActualHero actualHero, InnerCommand[][] innerCommands) {
		
		CommandContainer commandContainer = new CommandContainer();
		List<Feature> feature = new ArrayList<>();
		for(InnerCommand[] innerCommandArr : innerCommands) {
			for(InnerCommand innerCommand : innerCommandArr) {
				handle(commandContainer, feature, innerCommand);
			}
		}
		Features features = featuresService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		features.getByClasses().addAll(feature);
		featuresService.save(features);
		inerComandExecutor.execute(actualHero, commandContainer);
	}

	public void handle(CommandContainer commandContainer, List<Feature> trait, InnerCommand inerComands) {

		if (inerComands instanceof AddCommand) {
			add(commandContainer, trait, (AddCommand) inerComands);
		} else if (inerComands instanceof UpCommand) {
			commandContainer.getUp().add(((UpCommand) inerComands).getObjectDnd());
		} else if (inerComands instanceof CloudCommand) {
			commandContainer.getCloud().add((CloudCommand) inerComands);
		}
	}


	private void add(CommandContainer commandContainer, List<Feature> feature, AddCommand comand) {

		ObjectDnd[] objects = comand.getTargets();
		for (ObjectDnd object : objects) {
			if(object instanceof InnerCommand) {
				handle(commandContainer, feature, (InnerCommand) object);
			} else if (object instanceof Feature) {
				feature.add((Feature) object);
				if (object instanceof InnerFeature target) {
					for (InnerCommand innerCommand : target.getCommand()) {
						handle(commandContainer, feature, innerCommand);
					}
				}
			} else if (object instanceof Possession
					|| object instanceof MagicSoul
					|| object instanceof Spell
					|| object instanceof AttackModification
					|| object instanceof Items) {
				commandContainer.getAdd().add(object);
			}
		}
	}
}
