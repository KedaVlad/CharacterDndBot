package app.dnd.model.wrapp;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import app.dnd.model.comands.InerComand;
import app.dnd.util.math.Formalizer.Roll;
import lombok.Data;

@Data
@Document(collection = "classes")
public class ClassDndWrapp {
	
	@Id
	private String id;
	public String className;
	public String archetype;
	public String information;
	public Roll diceHp;
	public int firstHp;
	@Field("iner_comand")
	public InerComand[][] growMap;

}
