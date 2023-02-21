package app.dnd.service.logic.characteristic;

import org.springframework.stereotype.Component;

import app.dnd.dto.characteristics.Stat;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StatUp {

	public void up(Stat stat, int value) {
		log.info("StatUp : " + stat.getName() + " value " + stat.getValue() + " target value " + value + " max " + stat.getMaxValue() + " min " + stat.getMinValue());
		stat.setValue(stat.getValue() + value);
		log.info("AfterUp : " + stat.getName() + " value " + stat.getValue() + " target value " + value + " max " + stat.getMaxValue() + " min " + stat.getMinValue());
		System.out.println(value > stat.getMaxValue());
		System.out.println(value < stat.getMinValue());
		if (stat.getValue() > stat.getMaxValue()) {
			stat.setValue(stat.getMaxValue());
		} else if(stat.getValue() < stat.getMinValue()) {
			stat.setValue(stat.getMinValue());
		}
	}
}
