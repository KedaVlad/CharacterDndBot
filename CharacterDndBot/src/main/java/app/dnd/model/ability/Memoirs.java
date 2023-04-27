package app.dnd.model.ability;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "memoirs")
public class Memoirs {

	@Id
	private String mongoId;
	private Long userId;
	private String ownerName;
	private List<String> myMemoirs;

	public static Memoirs build(Long id, String ownerName) {
		Memoirs memoirs = new Memoirs();
		memoirs.userId = id;
		memoirs.ownerName = ownerName;
		memoirs.myMemoirs = new ArrayList<>();
		return memoirs;
	}
}
