package com.dnd.CharacterDndBot.dnd.service.factory;

import com.dnd.CharacterDndBot.dnd.dto.Informator;

public interface InformatorExecutor<T extends Informator> {

	public String getInformation(T informator);
}
