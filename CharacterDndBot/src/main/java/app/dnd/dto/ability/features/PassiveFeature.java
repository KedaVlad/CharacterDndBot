package app.dnd.dto.ability.features;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeName("passive_feature")
@Data
@EqualsAndHashCode(callSuper=false)
public class PassiveFeature extends Feature {
  
	private String passive;

}
