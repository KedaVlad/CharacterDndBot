package app.bot.event;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;
import org.telegram.telegrambots.meta.api.objects.Update;

import app.bot.model.UserCore;
import lombok.Data;

@Data
public class StartGame<T extends UserCore> implements ResolvableTypeProvider {

	private final T user;
	private final Update update;
	
	public StartGame(T user, Update update) {
		this.user = user;
		this.update = update;
	}

	@Override
	public ResolvableType getResolvableType() {
		return ResolvableType.forClassWithGenerics(
                getClass(),
                ResolvableType.forInstance(this.user)
        );
	}
}
