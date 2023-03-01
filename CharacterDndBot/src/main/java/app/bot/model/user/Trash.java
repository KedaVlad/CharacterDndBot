package app.bot.model.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("trash")
public class Trash {

	@Id
	private Long id;
	private List<Integer> circle = new ArrayList<>();
}
