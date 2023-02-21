package app.dnd.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RaceDnd implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String raceName;
	private String subRace;
	private int speed;
}
