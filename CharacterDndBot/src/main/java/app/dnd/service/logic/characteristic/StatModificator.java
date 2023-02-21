package app.dnd.service.logic.characteristic;

import org.springframework.stereotype.Component;

import app.dnd.dto.characteristics.Stat;

@Component
public class StatModificator {

	public int modificate(Stat stat)
	{
		return (stat.getValue() - 10) / 2;
	}
	
}
