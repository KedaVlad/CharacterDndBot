package app.dnd.service.factory;

import app.dnd.dto.Informator;

public interface InformatorExecutor<T extends Informator> {

	public String getInformation(T informator);
}
