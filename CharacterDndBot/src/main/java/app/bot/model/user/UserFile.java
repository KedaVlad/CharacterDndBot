package app.bot.model.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Entity(name = "users_data_table")
@Data
public class UserFile {

	@Id
	private Long id;

	@Lob
	private byte[] data;

}
