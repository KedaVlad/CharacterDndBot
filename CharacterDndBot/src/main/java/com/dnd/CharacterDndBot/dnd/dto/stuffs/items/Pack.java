package com.dnd.CharacterDndBot.dnd.dto.stuffs.items;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("PACK")
public class Pack extends Items {
 
	private static final long serialVersionUID = 1L;

	public Pack() {}

	public Pack(Packs type) {
		this.setName(type.name);
		this.setDescription(type.description);
	}

	public enum Packs {
		
		ENTERTAINER("Entertainer`s Pack",
				"Inside:\r\n" + "• backpack,\r\n" + "• sleeping bag,\r\n" + "• 2 suits,\r\n" + "• 5 candles,\r\n"
						+ "• rations for 5 days,\r\n" + "• wineskin\r\n" + "• makeup kit."),
		BURGLAR("Burglar`s Pack",
				"Inside:\r\n" + "• backpack,\r\n" + "• a bag with 1,000 metal balls,\r\n"
						+ "• 10 feet of fishing line,\r\n" + "• bell,\r\n" + "• 5 candles,\r\n" + "• crowbar,\r\n"
						+ "• hammer,\r\n" + "• 10 bolts,\r\n" + "• closed lantern,\r\n" + "• 2 flasks of oil,\r\n"
						+ "• rations for 5 days,\r\n" + "• tinderbox\r\n" + "• wineskin.\r\n"
						+ "The set also includes a 50-foot hemp rope attached to the side."),
		DIPLOMAT("Diplomat`s Pack",
				"Inside:\r\n" + "• box,\r\n" + "• 2 containers for cards and scrolls,\r\n"
						+ "• a set of excellent clothes,\r\n" + "• a bottle of ink,\r\n" + "• writing pen,\r\n"
						+ "• lamp,\r\n" + "• 2 flasks of oil,\r\n" + "• 5 sheets of paper,\r\n"
						+ "• bottle of perfume,\r\n" + "• wax,\r\n" + "• soap."),
		DUNGEON("Dungeoneer`s Pack",
				"Inside:\r\n" + "• backpack,\r\n" + "• crowbar,\r\n" + "• hammer,\r\n" + "• 10 bolts,\r\n"
						+ "• 10 torches,\r\n" + "• tinderbox,\r\n" + "• rations for 10 days\r\n" + "• wineskin.\r\n"
						+ "The set also includes a 50-foot hemp rope attached to the side."),
		EXPLORER("Explorer`s Pack",
				"Inside:\r\n" + "• backpack,\r\n" + "• sleeping bag,\r\n" + "• tableware,\r\n" + "• tinderbox,\r\n"
						+ "• 10 torches,\r\n" + "• rations for 10 days\r\n" + "• wineskin.\r\n"
						+ "The set also includes a 50-foot hemp rope attached to the side."),
		PRIEST("Priest`s Pack",
				"Inside:\r\n" + "• backpack,\r\n" + "• a blanket,\r\n" + "• 10 candles,\r\n" + "• tinderbox,\r\n"
						+ "• donation box,\r\n" + "• 2 packs of incense,\r\n" + "• censer,\r\n" + "• vestments,\r\n"
						+ "• rations for 2 days,\r\n" + "• wineskin."),
		SCHOLAR("Scholar`s Pack", "Inside:\r\n" + "• backpack\r\n" + "• scientific book\r\n" + "• bottle of ink\r\n"
				+ "• writing pen\r\n" + "• 10 sheets of parchment\r\n" + "• small bag with sand\r\n" + "• small knife");

		private String name;
		private String description;

		Packs(String name, String description) {
			this.name = name;
			this.description = description;
		}

		public String toString() {
			return name;
		}
	}

}
