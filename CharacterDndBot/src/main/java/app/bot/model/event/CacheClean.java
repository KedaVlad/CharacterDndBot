package app.bot.model.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;
@Getter
public class CacheClean extends ApplicationEvent {

    private final List<Integer> toDelete;
    private final Long userId;

    public CacheClean(Object source, List<Integer> toDelete, Long userId) {
        super(source);
        this.toDelete = toDelete;
        this.userId = userId;
    }
}
