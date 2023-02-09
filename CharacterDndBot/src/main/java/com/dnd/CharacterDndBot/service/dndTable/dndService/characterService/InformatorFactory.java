package com.dnd.CharacterDndBot.service.dndTable.dndService.characterService;

import com.dnd.CharacterDndBot.service.dndTable.dndDto.CharacterDnd;
import com.dnd.CharacterDndBot.service.dndTable.dndDto.ObjectDnd;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class InformatorFactory {

	public static Informator build(ObjectDnd object)
	{
		if(object instanceof CharacterDnd)
		{
			return new CharacterInformator((CharacterDnd) object);
		}
		else
		{
			log.error("InformatorFactory: This object is not supported by informant");
			return new DefaultInformator(object);
		}
	}
}

abstract class BaseInformator<T extends ObjectDnd> implements Informator {
	
	protected T object;
	
	public BaseInformator(T object) {
		this.object = object;
	}
}

class DefaultInformator extends BaseInformator<ObjectDnd> {

	public DefaultInformator(ObjectDnd object) {
		super(object);
	}

	@Override
	public String getInformation() {
		return "This object is not supported by informant";
	}
	
}

class CharacterInformator extends BaseInformator<CharacterDnd> {

	public CharacterInformator(CharacterDnd object) {
		super(object);
	}

	@Override
	public String getInformation() {
		return object.getName();
	}
	
	
}