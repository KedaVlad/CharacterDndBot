package app.dnd.model.characteristics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "memoirs")
public class Memoirs {

	@Id
	private Long id;
	private List<String> myMemoirs;

	public static Memoirs build(Long id) {
		Memoirs memoirs = new Memoirs();
		memoirs.id = id;
		memoirs.myMemoirs = new ArrayList<>();
		return memoirs;
	}
}
