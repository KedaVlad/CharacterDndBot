package com.dnd.CharacterDndBot.dnd.service.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dnd.CharacterDndBot.dnd.dto.CharacterDnd;
import com.dnd.CharacterDndBot.dnd.dto.Informator;
import com.dnd.CharacterDndBot.dnd.service.factory.InformatorExecutor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InformatorHandler {

	@Autowired
	private DefaultInformator defaultInformator;
	@Autowired
	private CharacterInformator characterInformator;

	public String handle(Informator informator) {
		if (informator instanceof CharacterDnd) {
			return characterInformator.getInformation((CharacterDnd) informator);
		} else {
			log.error("InformatorFactory: This object is not supported by informant");
			return defaultInformator.getInformation(informator);
		}
	}
}

@Component
class DefaultInformator implements InformatorExecutor<Informator> {

	@Override
	public String getInformation(Informator informator) {
		return "This object is not supported by informantor";
	}

}

@Component
class CharacterInformator implements InformatorExecutor<CharacterDnd> {

	@Override
	public String getInformation(CharacterDnd informator) {
		return informator.getName();
	}
}