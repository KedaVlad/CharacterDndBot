package app.dnd.model.telents.features;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import app.dnd.model.ObjectDnd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("feature")
@JsonTypeInfo(include = JsonTypeInfo.As.EXTERNAL_PROPERTY, use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({ 
	@JsonSubTypes.Type(value = Feature.class, name = "feature"),
	@JsonSubTypes.Type(value = ActiveFeature.class, name = "active_feature"),
	@JsonSubTypes.Type(value = InerFeature.class, name = "iner_feature"),
	@JsonSubTypes.Type(value = PassiveFeature.class, name = "passive_feature") })
@Data
@EqualsAndHashCode(callSuper=false)
public class Feature implements ObjectDnd {

	private String name;
	private String description;

	public String toString() {
		return getName() + "\n" + getDescription();
	}

	public static Feature create(String name, String description) {
		Feature answer = new Feature();
		answer.name = name;
		answer.description = description;
		return answer;
	}


}
