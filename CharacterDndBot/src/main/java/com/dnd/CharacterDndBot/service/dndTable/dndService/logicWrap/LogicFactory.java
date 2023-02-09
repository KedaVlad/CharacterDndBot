package com.dnd.CharacterDndBot.service.dndTable.dndService.logicWrap;

public abstract class LogicFactory {

	public static LogicConteiner<LvlLogic> lvl() {
		return new LogicConteiner<LvlLogic>(new LvlLogic());
	}
	
	public static LogicConteiner<HpLogic> hp() {
		return new LogicConteiner<HpLogic>(new HpLogic());
	}
	
	public static LogicConteiner<StuffLogic> stuff() {
		return new LogicConteiner<StuffLogic>(new StuffLogic());
	}
	
	public static LogicConteiner<CharacteristicLogic> characteristic() {
		return new LogicConteiner<CharacteristicLogic>(new CharacteristicLogic());
	}
	
	public static LogicConteiner<ProficiencyLogic> proficiency() {
		return new LogicConteiner<ProficiencyLogic>(new ProficiencyLogic());
	}
}
