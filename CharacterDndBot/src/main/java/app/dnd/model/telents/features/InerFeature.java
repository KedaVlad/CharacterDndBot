package app.dnd.model.telents.features;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.comands.InerComand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("iner_feature")
@Data
@EqualsAndHashCode(callSuper = false)
public class InerFeature extends Feature { 
	private InerComand[] comand;
	
	public static InerFeature create(String string, String string2, InerComand... create) {

		InerFeature answer = new InerFeature();
		answer.setName(string);
		answer.setDescription(string2);
		answer.comand = create;
		
		return answer;
	}
}
