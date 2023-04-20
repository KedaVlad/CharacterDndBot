package app.dnd.model.hero;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "classes_dnd")
public class ClassesDnd {

	@Id
	private Long id;
	private String ownerName;
	private List<ClassDnd> dndClass;

	public static ClassesDnd build(Long id, String ownerName) {
		ClassesDnd classesDnd = new ClassesDnd();
		classesDnd.ownerName = ownerName;
		classesDnd.id = id;
		classesDnd.dndClass = new ArrayList<>();
		return classesDnd;
	}
}
