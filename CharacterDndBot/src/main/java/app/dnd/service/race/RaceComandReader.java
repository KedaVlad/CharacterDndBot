package app.dnd.service.race;

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
import app.dnd.model.telents.features.Feature;
import app.dnd.model.telents.features.Features;
import app.dnd.model.telents.features.InnerFeature;
import app.dnd.model.telents.proficiency.Possession;
import app.dnd.service.inerComand.InerComandExecutor;
import app.dnd.service.talants.FeaturesService;
import app.bot.model.user.ActualHero;

@Service
public class RaceComandReader {

	@Autowired
	private FeaturesService featuresService;
	@Autowired
	private InerComandExecutor inerComandExecutor;

	public void execute(ActualHero actualHero, InnerCommand[] innerCommands) {

		CommandContainer commandContainer = new CommandContainer();
		List<Feature> trait = new ArrayList<>();
		for(InnerCommand innerCommand : innerCommands) {
			handle(commandContainer, trait, innerCommand);
		}
		Features features = featuresService.findByIdAndOwnerName(actualHero.getId(), actualHero.getName());
		features.getByRace().addAll(trait);
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


	private void add(CommandContainer commandContainer, List<Feature> trait, AddCommand comand) {
		
		ObjectDnd[] objects = comand.getTargets();
		for (ObjectDnd object : objects) {
			if(object instanceof InnerCommand) {
				handle(commandContainer, trait, (InnerCommand) object);
			} else if (object instanceof Feature) {
				trait.add((Feature) object);
				if (object instanceof InnerFeature) {
					InnerFeature target = (InnerFeature) object;
					for (InnerCommand innerCommand : target.getCommand()) {
						handle(commandContainer, trait, innerCommand);
					}
				}
			} else if (object instanceof Possession) {
				commandContainer.getAdd().add((Possession) object);
			}
		}
	}
}
