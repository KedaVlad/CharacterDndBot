package app.dnd.model.stuffs;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.dnd.model.Informator;
import lombok.Data;

@Data
@Document(collection = "wallet")
public class Wallet {

	@Id
	private Long id;
	private int bronze = 0;// CP
	private int silver = 0;// SP
	private int gold = 0;// GP
	private int plate = 0;// PP

	@Override
	public String getInformation() {
		return "CP(" + bronze + ") SP(" + silver + ") GP(" + gold + ") PP(" + plate + ")";
	}

	public static Wallet build(Long id) {
		Wallet wallet = new Wallet();
		wallet.id = id;
		return wallet;
	}
}
