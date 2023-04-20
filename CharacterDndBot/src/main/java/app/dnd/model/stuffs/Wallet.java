package app.dnd.model.stuffs;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "wallet")
public class Wallet {

	@Id
	private Long id;
	private String ownerName;
	
	private int bronze = 0;// CP
	private int silver = 0;// SP
	private int gold = 0;// GP
	private int plate = 0;// PP

	public String shortInformation() {
		return "[CP: " + bronze + "][SP: " + silver + "][GP: " + gold + "][PP: " + plate + "]";
	}

	public static Wallet build(Long id, String ownerName) {
		Wallet wallet = new Wallet();
		wallet.id = id;
		wallet.ownerName = ownerName;
		return wallet;
	}

	public String longInformation() {
		return "[CP] Copper : " + bronze + "\n"
				+ "[SP] Silver : " + silver + "\n"
				+ "[GP] Gold : " + gold  + "\n"
				+ "[PP] Platinum : " + plate;
	}
}
