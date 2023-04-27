package app.dnd.model.enums;

import lombok.Getter;

public enum Packs {
	
	ENTERTAINER("Entertainer`s Pack",
			"""
					Inside:\r
					• backpack,\r
					• sleeping bag,\r
					• 2 suits,\r
					• 5 candles,\r
					• rations for 5 days,\r
					• wineskin\r
					• makeup kit."""),
	BURGLAR("Burglar`s Pack",
			"""
					Inside:\r
					• backpack,\r
					• a bag with 1,000 metal balls,\r
					• 10 feet of fishing line,\r
					• bell,\r
					• 5 candles,\r
					• crowbar,\r
					• hammer,\r
					• 10 bolts,\r
					• closed lantern,\r
					• 2 flasks of oil,\r
					• rations for 5 days,\r
					• tinderbox\r
					• wineskin.\r
					The set also includes a 50-foot hemp rope attached to the side."""),
	DIPLOMAT("Diplomat`s Pack",
			"""
					Inside:\r
					• box,\r
					• 2 containers for cards and scrolls,\r
					• a set of excellent clothes,\r
					• a bottle of ink,\r
					• writing pen,\r
					• lamp,\r
					• 2 flasks of oil,\r
					• 5 sheets of paper,\r
					• bottle of perfume,\r
					• wax,\r
					• soap."""),
	DUNGEON("Dungeon`s Pack",
			"""
					Inside:\r
					• backpack,\r
					• crowbar,\r
					• hammer,\r
					• 10 bolts,\r
					• 10 torches,\r
					• tinderbox,\r
					• rations for 10 days\r
					• wineskin.\r
					The set also includes a 50-foot hemp rope attached to the side."""),
	EXPLORER("Explorer`s Pack",
			"""
					Inside:\r
					• backpack,\r
					• sleeping bag,\r
					• tableware,\r
					• tinderbox,\r
					• 10 torches,\r
					• rations for 10 days\r
					• wineskin.\r
					The set also includes a 50-foot hemp rope attached to the side."""),
	PRIEST("Priest`s Pack",
			"""
					Inside:\r
					• backpack,\r
					• a blanket,\r
					• 10 candles,\r
					• tinderbox,\r
					• donation box,\r
					• 2 packs of incense,\r
					• censer,\r
					• vestments,\r
					• rations for 2 days,\r
					• wineskin."""),
	SCHOLAR("Scholar`s Pack", """
			Inside:\r
			• backpack\r
			• scientific book\r
			• bottle of ink\r
			• writing pen\r
			• 10 sheets of parchment\r
			• small bag with sand\r
			• small knife""");

	@Getter
	private final String name;
	@Getter
	private final String description;

	Packs(String name, String description) {
		this.name = name;
		this.description = description;
	}

}
