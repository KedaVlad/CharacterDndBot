package app.player.service.stage;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import app.bot.model.UserCore;
import app.player.model.enums.Location;

@Service
public class StageManager<T extends UserCore> {

	private Map<Location, Executor<T>> stages;
	
	@Autowired
	public StageManager(ListableBeanFactory beanFactory, Class<Executor<T>> type) {
		
		Map<String, Executor<T>> beansOfType = beanFactory.getBeansOfType(type);
		
		stages = beansOfType.entrySet().stream()
        .filter(entry -> entry.getValue().getClass().isAnnotationPresent(EventExecutor.class))
        .collect(Collectors.toMap(
                entry -> entry.getValue().getClass().getAnnotation(EventExecutor.class).value(),
                entry -> (Executor<T>) entry.getValue(),
                (v1, v2) -> v1, 
                HashMap::new ));
		}
	
	public Executor<T> find(Location location) {
		return stages.get(location);
	}
	
}
