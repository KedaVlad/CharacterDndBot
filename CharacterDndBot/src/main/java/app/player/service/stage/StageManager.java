package app.player.service.stage;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.player.model.EventExecutor;
import app.player.model.enums.Location;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StageManager {

	private final Map<Location, Executor> stages;
	
	@Autowired
	public StageManager(ListableBeanFactory beanFactory) {
		
		Map<String, Executor> beansOfType = beanFactory.getBeansOfType(Executor.class);
		
		stages = beansOfType.entrySet().stream()
        .filter(entry -> entry.getValue().getClass().isAnnotationPresent(EventExecutor.class))
        .collect(Collectors.toMap(
                entry -> entry.getValue().getClass().getAnnotation(EventExecutor.class).value(),
				Map.Entry::getValue,
                (v1, v2) -> v1, 
                HashMap::new ));
		}
	
	public Executor find(Location location) {

		return stages.get(location);
	}
	
}
