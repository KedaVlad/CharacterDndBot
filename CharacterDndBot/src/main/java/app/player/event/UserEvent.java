package app.player.event;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

import app.user.model.User;
import lombok.Data;

@Data
public class UserEvent<T> implements ResolvableTypeProvider {

	private final User user;
	private final T task;
	
	public UserEvent(User user, T tusk) {
		this.user = user;
		this.task = tusk;
	}

	@Override
	public ResolvableType getResolvableType() {
		return ResolvableType.forClassWithGenerics(
                getClass(),
                ResolvableType.forInstance(this.task)
        );
	}
	
}
