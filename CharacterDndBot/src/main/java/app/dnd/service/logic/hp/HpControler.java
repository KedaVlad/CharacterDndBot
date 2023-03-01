package app.dnd.service.logic.hp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bot.model.ActualHero;
import app.bot.service.ActualHeroService;

@Service
public class HpControler {

	@Autowired
	private ActualHeroService actualHeroService;
	@Autowired
	private HpBuilderFactory hpBuilderFactory;
	@Autowired
	private HpDamage hpDamage;
	@Autowired
	private HpGrow hpGrow;
	@Autowired
	private HpHeal hpHeal;
	@Autowired
	private TimeHp timeHp;
	
	public void grow(Long id, int value) {
		ActualHero actualHero = actualHeroService.getById(id);
		hpGrow.grow(actualHero.getCharacter().getHp(), value);
		actualHeroService.save(actualHero);
	}
	
	public void damage(Long id, int value) {
		ActualHero actualHero = actualHeroService.getById(id);
		hpDamage.damaged(actualHero.getCharacter().getHp(), value);
		actualHeroService.save(actualHero);
	}
	
	public void heal(Long id, int value) {
		ActualHero actualHero = actualHeroService.getById(id);
		hpHeal.heal(actualHero.getCharacter().getHp(), value);
		actualHeroService.save(actualHero);
	}
	
	
	public void bonusHp(Long id, int value) {
		ActualHero actualHero = actualHeroService.getById(id);
		timeHp.add(actualHero.getCharacter().getHp(), value);
		actualHeroService.save(actualHero);
	}
	
	public HpBuilderFactory buildHp() {
		return hpBuilderFactory;
	}
}
