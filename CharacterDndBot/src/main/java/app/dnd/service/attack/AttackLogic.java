package app.dnd.service.attack;

import java.util.List;

import app.dnd.model.enums.Proficiency;
import app.dnd.model.stuffs.items.Weapon;
import app.dnd.model.telents.attacks.AttackModification;
import app.bot.model.user.ActualHero;

public interface AttackLogic {

	public Proficiency prof(ActualHero hero, AttackModification attackModification);
	public void setAttack(ActualHero hero, AttackModification attackModification);
	public List<AttackModification> buildAttack(ActualHero hero, Weapon weapon);
	public List<AttackModification> hit(ActualHero hero);
	public List<AttackModification> crit(ActualHero hero);
	
}
