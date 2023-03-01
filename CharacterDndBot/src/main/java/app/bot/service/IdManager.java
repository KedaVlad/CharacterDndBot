package app.bot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class IdManager {

	private final List<Long> inSession = new ArrayList<>();
}
