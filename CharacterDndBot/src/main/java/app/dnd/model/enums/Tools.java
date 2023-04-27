package app.dnd.model.enums;

import lombok.Getter;

public enum Tools {
	SMITH("Smith`s Tools", """
			Inside:\r
			• hammers,\r
			• pincers,\r
			• coal,\r
			• rags,\r
			• grindstone.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			MAGIC and HISTORY.\r
			Your experience gives you additional knowledge when examining metal objects such as weapons.\r
			\r
			RESEARCH.\r
			You may discover clues that others may not see when the investigation is about armor, weapons, or other pieces of metalwork.\r
			\r
			And also you can:\r
			Repair. With access to tools and an open fire that is hot enough to ductile metal, you can repair 10 hit points of a damaged metal object for every hour of work.\r
			\r
			Additional abilities based on roll results:\r
			10 Sharpen a dull blade\r
			15 Repair a set of armor\r
			15 Dismantle non-magical metal object"""),
	NAVIGATOR("Navigator`s Tools", """
			Inside:\r
			• sextant,\r
			• compass,\r
			• caliper,\r
			• ruler,\r
			• parchment,\r
			• ink,\r
			• writing pen.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			SURVIVAL. Knowing how to use navigation tools helps you avoid getting lost and gives you knowledge of the most likely location of roads and settlements.\r
			\r
			\r
			And also you can:\r
			Position definition. With careful measurements, you can determine your position on the nautical chart and the time of day.\r
			\r
			Additional abilities based on roll results:\r
			10 Plot a course\r
			15 Determine your position on the marine navigation chart"""),
	THIEVES("Thieve`s Tools", """
			Inside:\r
			• a small file,\r
			• a set of master keys,\r
			• a small mirror with a long handle,\r
			• a set of scissors with narrow blades,\r
			• a pair of tweezers.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			HISTORY.\r
			Your knowledge of traps gives you additional knowledge when answering questions about places famous for their traps.\r
			RESEARCH and PERCEPTION.\r
			You gain additional knowledge when looking for traps, because you know various simple signs that give away their presence.\r
			\r
			And also you can:\r
			Setting up a trap.\r
			You can not only disable traps, but also set them. As part of a short rest, you can create a trap from improvised means. The result of your check becomes a challenge for anyone trying to detect or disable the trap. The trap deals damage equal to the materials used in its creation (such as poison or weapons), or damage equal to half the result of your check, whichever the DM deems more appropriate.\r
			\r
			Additional abilities based on roll results:\r
			Various Open the lock\r
			Disarm the trap"""),
	DRAGON_CHESS("Dragon Chess", """
			Inside:\r
			A game set includes everything needed for a particular game or group of games, such as a full deck of cards or a board and pieces.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			HISTORY.\r
			Your mastery of the game includes knowledge of its history, as well as knowledge of important events and famous historical figures associated with the game.\r
			INSIGHT.\r
			Playing with someone is a great way to understand their personality and what they are, which in turn allows you to better recognize lies and determine their mood.\r
			SLEIGHT OF HAND.\r
			Sleight of Hand is a useful skill for cheating in the game, as it allows you to swap pieces, hide cards, or reverse a bad die roll. On the other hand, captivating the target with a game that requires dexterous movements is a good distraction for pickpocketing.\r
			\r
			Additional abilities based on roll results:\r
			15 Catch a player on a cheat\r
			15 Understand the character of the opponent"""),
	ALCHEMIST("Alchemist`s Supplies", """
			Inside:\r
			• two glass beakers,\r
			• a metal frame that holds the beaker above an open flame,\r
			• glass stick for stirring,\r
			• small mortar\r
			• pestle,\r
			• a pouch of common alchemy ingredients, including\r
			     ◦ salt\r
			     ◦ crushed iron,\r
			     ◦ distilled water.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			Magic.\r
			Proficiency with alchemist's tools allows you to reveal more information on Magic checks involving potions and similar substances.\r
			\r
			Investigation.\r
			When you search an area for clues, wielding an alchemist's tools grants additional knowledge about what chemicals and other substances were used in the area.\r
			\r
			And also you can:\r
			\r
			Alchemical craft.\r
			You can use tool proficiency to craft alchemical items. The character can spend money to gather raw materials, which weigh 1 pound for every 50 gp used. The GM may allow the character to pass the Identification check with advantage.\r
			As part of a long rest, you can use the alchemist's tools to make one serving of\r
			• acids,\r
			• alchemical fire,\r
			• antidotes,\r
			• oils,\r
			• spirits\r
			• soap.\r
			Subtract half the cost of the crafted item from the total cost of the raw materials you collected.\r
			\r
			Additional abilities based on roll results:\r
			10 Create puffs of thick smoke\r
			10 Identify Poison\r
			15 Identify the substance\r
			15 Arson\r
			20 Neutralize Acid"""),
	POTTER("Potter`s Tools", """
			Inside:\r
			• pottery needles,\r
			• cycles,\r
			• scrapers,\r
			• knife,\r
			• caliper.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			History.\r
			Your knowledge and experience helps you to identify ceramic objects by knowing when they were made and from which places or cultures they come.\r
			\r
			Investigation, Perception.\r
			You gain additional knowledge when examining ceramics, finding clues that others may not notice on small bumps and chips.\r
			\r
			\r
			And also you can:\r
			\r
			Restoration of appearance.\r
			After examining ceramic shards, you can determine the origin of the object, its original shape and possible purpose.\r
			\r
			Additional abilities based on roll results:\r
			10 Determine what was in the vessel\r
			15 Create a durable pot\r
			20 Find the weak point of a ceramic object"""),
	CALLIGRAPHY("Calligrapher`s Supplies", """
			Inside:\r
			• ink,\r
			• a lot of parchment sheets,\r
			• three writing pens.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			Magic.\r
			While calligraphy is of little help in deciphering the contents of a magical text, mastery of these tools helps determine who wrote the magical text.\r
			\r
			History.\r
			Proficiency with these tools can increase the benefit of a successful check made to analyze or examine ancient writings, scrolls, or other text, including runes carved into stone, and inscriptions in frescoes or other images.\r
			\r
			\r
			And also you can:\r
			\r
			Treasure map decryption.\r
			Mastery of these tools allows you to conduct an examination of maps. You can make an Intelligence check to estimate the age of the card, determine if the card contains a hidden message, and so on.\r
			\r
			Additional abilities based on roll results:\r
			10 Identify the writer of the non-magical handwriting\r
			15 Determine the state of mind of the writer\r
			15 Recognize fake text\r
			20 Forge a signature"""),
	MASON("Mason`s Tools", """
			Inside:\r
			• Master OK,\r
			• hammer,\r
			• chisel,\r
			• brushes,\r
			• square.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			History.\r
			Your experience helps you determine the date and purpose of a stone building, and who might have built it.\r
			\r
			Investigation.\r
			You gain additional knowledge when examining rooms in a stone building.\r
			\r
			Perception.\r
			You notice irregularities in the stone walls and floors, making it easier to find traps and secret passages.\r
			\r
			And also you can:\r
			\r
			Demolition.\r
			Your knowledge of stone architecture allows you to find vulnerabilities in the masonry of stone walls. You deal double damage to such structures with your weapon attacks.\r
			\r
			Additional abilities based on roll results:\r
			10 Punch a small hole in the stone wall with a chisel\r
			15 Find a weak spot in the stone wall"""),
	CARTOGRAPH("Cartographer`s Tools", """
			Inside:\r
			• writing pen,\r
			• ink,\r
			• parchment,\r
			• compass,\r
			• caliper,\r
			• ruler.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			Magic, History, Religion.\r
			You can use your knowledge of maps and locations to extract more detailed information when using these skills. For example, you can find a hidden message on a map, find out when the map was made to determine what geographic features have changed since then, and so on.\r
			\r
			Nature.\r
			Your familiarity with physical geography makes it easier for you to find answers to questions and problems about your surroundings.\r
			\r
			Survival.\r
			Your understanding of geography makes it easier to find your way to civilization, allows you to predict where villages or towns may be found, and reduces the chance of you getting lost. You have studied so many maps that general trends, such as how trade routes develop or where settlements are usually built, are familiar to you.\r
			\r
			And also you can:\r
			\r
			Making a map.\r
			While traveling, you can draw a map in addition to participating in other activities.\r
			\r
			Additional abilities based on roll results:\r
			10 Determine the age and origin of the map\r
			15 Estimate the direction and distance to the landmark\r
			15 Recognize that the card is fake\r
			20 Draw the missing piece of the map"""),
	LEATHERWORK("Leatherworker`s Tools", """
			Inside:\r
			• leather cutter (knife),\r
			• a small mallet,\r
			• groove cutter,\r
			• punch,\r
			• a thread,\r
			• pieces of skin.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			Magic.\r
			Your knowledge and experience with leather gives you additional knowledge when examining magical items made from leather, such as boots or some cloaks.\r
			\r
			Investigation.\r
			You gain additional knowledge when you examine leather items or clues related to them, and you use your knowledge of leatherworking to find clues that others may not notice.\r
			\r
			And also you can:\r
			\r
			Skin identification.\r
			By looking at a piece of leather or a leather object, you can determine the source of the leather and any special techniques used in processing it. For example, you can tell the difference between leather crafted using dwarven techniques and leather crafted using halfling techniques.\r
			\r
			Additional abilities based on roll results:\r
			10 Change the appearance of a leather item\r
			20 Determine the past of a leather item"""),
	POISONER("Poisoner`s Kit", """
			Inside:\r
			• glass bottles,\r
			• mortar\r
			• pestle,\r
			• chemical reagents,\r
			• glass stick for stirring.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			History.\r
			Your training in the use of poisons can help you when trying to remember facts about infamous poisonings.\r
			\r
			Investigation, Perception.\r
			Your knowledge of poisons has taught you to be careful when dealing with these substances, thus aiding you when examining a poisoned item or getting leads on poison related events.\r
			\r
			The medicine.\r
			When you treat a victim of poison, your awareness can give you additional knowledge on how to properly care for the poisoned.\r
			\r
			Nature, Survival.\r
			Working with poisons allowed you to gain knowledge about which plants and animals are poisonous.\r
			\r
			And also you can:\r
			\r
			Handling poisons.\r
			Your proficiency allows you to carefully handle poisons and use them without risking exposure to them yourself.\r
			\r
			Additional abilities based on roll results:\r
			10 Identify the poisoned object\r
			20 Determine Poison Effect"""),
	BREWER("Brewer`s Supplies", """
			Inside:\r
			• a large glass bottle,\r
			• some hops,\r
			• siphon,\r
			• coil,\r
			• several feet of tubing.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			History.\r
			Proficiency with a brewer's tools gives you additional knowledge on Intelligence (History) checks regarding events in which alcohol was an important part.\r
			\r
			The medicine.\r
			Mastery of these tools provides additional knowledge when you alleviate alcohol poisoning, hangovers, or when using alcohol to dull pain.\r
			\r
			Belief.\r
			A strong drink can help soften even a stone heart. Your mastery of the brewer's tools can help you give someone just the right amount of alcohol to cheer them up.\r
			\r
			And also you can:\r
			\r
			Distillation.\r
			Your knowledge of brewing allows you to purify water that would otherwise be undrinkable. You can clear up to 6 gallons of water as part of a long rest, or 1 gallon as part of a short rest.\r
			\r
			Additional abilities based on roll results:\r
			10 Detect poison or impurity in drink\r
			15 Identify alcohol\r
			20 Ignore the effect of alcohol"""),
	CARPENTER("Carpenter`s Tools", """
			Inside:\r
			• saw,\r
			• hammer,\r
			• nails,\r
			• axe,\r
			• square,\r
			• ruler,\r
			• encoder,\r
			• planer,\r
			• chisel.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			Investigation.\r
			You gain additional knowledge when exploring an area in a wooden building because you know building tricks that can hide something in an area when you explore.\r
			\r
			Perception.\r
			You may notice bumps in the wooden walls and floors, making it easier to find traps and secret passages.\r
			\r
			Stealth.\r
			You can quickly find a weak spot in a hardwood floor, making it easier for you to spot areas that will break or squeak if stepped on.\r
			\r
			And also you can:\r
			\r
			Strengthening.\r
			In 1 minute, if you have materials, you can strengthen the door or window. The knockout roll's DC is increased by 5.\r
			\r
			Temporary shelter.\r
			As part of a long rest, you can build a canopy or similar shelter to keep your group out of the rain or the sun during the rest. Because it was built in haste from the first available timber, the shelter falls apart 1d3 days after construction.\r
			\r
			Additional abilities based on roll results:\r
			10 Build a simple wooden structure\r
			15 Design a complex wooden structure\r
			15 Find a weak spot in a wooden wall\r
			20 Remove door from hinges"""),
	COOK("Cook`s Utensils", """
			Inside:\r
			• knife,\r
			• chisel,\r
			• small saw.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			History.\r
			Your knowledge of culinary techniques allows you to evaluate the structure of social development based on the gastronomic customs of this culture.\r
			\r
			The medicine.\r
			By providing healing, you can turn bitter or sour medicine into a palatable concoction.\r
			\r
			Survival.\r
			By obtaining food, you can use the ingredients you have collected that others could not cook.\r
			\r
			And also you can:\r
			\r
			Cooking.\r
			As part of a short break, you can prepare a delicious meal to help your companions recuperate. You and up to five creatures of your choice regain 1 extra hit point for each Hit Dice spent during a short rest, provided you have access to your cook's tools and enough food.\r
			\r
			Additional abilities based on roll results:\r
			10 Cook regular food\r
			10 Repeat a dish10Detect a poison or impurity in food\r
			15 Detect poison or impurities in food\r
			15 Cook a gourmet meal"""),
	WOOD("Woodcarver`s Tools", """
			Inside:\r
			• knife,\r
			• chisel,\r
			• small saw.\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			Magic, History.\r
			Your experience gives you additional knowledge when examining wooden objects such as figurines or arrows.\r
			\r
			Nature.\r
			Your knowledge of wood gives you additional knowledge when you study trees.\r
			\r
			\r
			And also you can:\r
			\r
			Repair.\r
			As part of a short rest, you can repair one damaged piece of wood.\r
			\r
			Make arrows.\r
			As part of a short rest, you can make up to five arrows. As part of a long rest, you can do up to twelve. You must have wood on hand to make arrows.\r
			\r
			Additional abilities based on roll results:\r
			10 Make a small wooden figurine\r
			10 Carve an intricate pattern on wood"""),
	TINKER("Tinker`s Tools",
			"""
					Vnutri: • razlichnyye ruchnyye instrumenty, • nitki, • igolki, • tochil'nyy kamen', • kuski tkani i kozhi, • nebol'shuyu banku kleya (sinyuyu izolentu poka, uvy, ne pridumali). Kogda vy delayete proverku SKILOV vy mozhete poluchit' preimushchestva: Istoriya.  Vy mozhete opredelit' vozrast i proiskhozhdeniye predmeta, dazhe yesli u vas v nalichii vsego neskol'ko oskolkov, ostavshikhsya ot originala. Rassledovaniye.  Kogda Vy osmatrivayete povrezhdonnyy predmet, Vy uznayote, kak i naskol'ko davno on byl povrezhdon. A takzhe vy mozhet: Pochinka.  Vy mozhete vosstanovit' po 10 khitov povrezhdonnogo predmeta za kazhdyy chas raboty. Dlya pochinki lyubogo predmeta vam neobkhodim dostup k materialam, neobkhodimym dlya remonta. Dlya metallicheskikh predmetov vam nuzhen dostup k otkrytomu ognyu, zhara kotorogo dostatochno, chtoby sdelat' metall plastichnym. Dopolnitel'nyye vozmozhnosti po rezul'tatam broska: 10 Vremenno pochinit' povrezhdonnyy mekhanizm 15 Pochinit' predmet vdvoye bystreye 20 Smasterit' iz khlama vremennyy predmet\r
					Ещё\r
					979 / 5 000\r
					Результаты перевода\r
					Перевод\r
					Inside:\r
					• various hand tools,\r
					• threads,\r
					• needles,\r
					• grindstone,\r
					• pieces of cloth and leather,\r
					• a small jar of glue (blue duct tape, alas, has not yet been invented).\r
					\r
					When you make a SKILL check you can gain the following benefits:\r
					\r
					History.\r
					You can determine the age and origin of an item, even if you only have a few fragments left from the original.\r
					\r
					Investigation.\r
					When you inspect a damaged item, you will find out how and how long ago it was damaged.\r
					\r
					\r
					And also you can:\r
					\r
					Repair.\r
					You can restore 10 hit points of a damaged item for each hour of work. To repair any item, you need access to the materials needed for the repair. For metal objects, you need access to an open fire that is hot enough to make the metal malleable.\r
					\r
					Additional abilities based on roll results:\r
					10 Temporarily repair the damaged mechanism\r
					15 Repair an item twice as fast\r
					20 Craft a temporary item out of junk"""),
	COBBLER("Cobbler`s Tools", """
			Inside:\r
			• hammer,\r
			• awl,\r
			• knife,\r
			• shoe last,\r
			• scissors,\r
			• stock of leather,\r
			• stock of threads.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			Magic, History.\r
			Your knowledge of shoes helps you determine the magical properties of enchanted boots or learn the history of such items.\r
			\r
			Investigation.\r
			Shoes hide many secrets. You can find out where the person whose shoes you are inspecting for dirt and wear was recently. Your experience in repairing shoes makes it easier for you to determine how the shoe was damaged.\r
			\r
			And also you can:\r
			\r
			Shoe care.\r
			As part of a long rest, you can repair your companions' shoes. For the next 24 hours, up to 6 creatures of your choice wearing the shoes you've worked on can travel up to 10 hours per day without passing exhaustion tests.\r
			\r
			Creation of a secret compartment.\r
			For 8 hours of work, you can add a secret compartment to a pair of shoes. The compartment can hold an object up to 3 inches long and 1 inch wide and high. You make an Intelligence check, adding your proficiency bonus for these tools, to determine the DC of the Intelligence (Investigation) check required to locate the squad.\r
			\r
			Additional abilities based on roll results:\r
			10 Determine the age and origin of shoes\r
			15 Find the secret compartment in the heel"""),
	GLASS("Glassblower`s Tools", """
			You need a heat source to work with glass.\r
			\r
			Inside:\r
			• blowing tube,\r
			• a small glass-blowing run-in,\r
			• katalnik,\r
			• development\r
			• tongs.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			Magic, History.\r
			Your knowledge of glassblowing techniques will help you when you study a glass object, such as a potion bottle or a glass object found in treasure. For example, you can study how a glass potion flask is affected by its contents to help determine the effect of the potion. (The potion may leave a residue, warp the glass, or stain it.)\r
			\r
			Investigation.\r
			When you are exploring an area, your knowledge can help you if the evidence includes broken glass or glass objects.\r
			\r
			And also you can:\r
			\r
			Identification of a weak spot.\r
			After spending 1 minute, you can find the weak point of the glass object. Any damage dealt to an item when attacking a weak spot is doubled.\r
			\r
			Additional abilities based on roll results:\r
			10 Determine the origin of the glass\r
			20 Determine what a glass object once contained"""),
	WEAWER("Weaver`s Tools", """
			Inside:\r
			• threads,\r
			• needles\r
			• pieces of cloth.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			Magic, History.\r
			Your experience gives you additional knowledge when inspecting wardrobe items, including cloaks and other capes.\r
			\r
			Investigation.\r
			By using your knowledge of the tailoring process, when examining tapestries, upholstery, garments, and other woven items, you can find clues that others may not notice.\r
			\r
			And also you can:\r
			\r
			Repair.\r
			As part of a short rest, you can repair one damaged piece of clothing.\r
			Tailoring.\r
			As part of a long rest, you can create clothing for the creature, provided you have enough fabric and thread.\r
			\r
			Additional abilities based on roll results:\r
			10 Fabric reuse\r
			10 Fabric reuse\r
			15 Hem the outfit"""),
	PAINT("Painter`s Supplies", """
			Inside:\r
			• easel,\r
			• canvas,\r
			• paints,\r
			• brushes,\r
			• charcoal pencils,\r
			• palette.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			Magic, History, Religion.\r
			Your experience helps you uncover any kind of knowledge that is associated with works of art, such as the magical properties of a painting or the origin of a strange fresco found in a dungeon.\r
			\r
			Investigation, Perception.\r
			When you look at a painting or similar work of fine art, your awareness of how it was created can give you additional knowledge.\r
			\r
			And also you can:\r
			\r
			Writing and drawing.\r
			As part of a short or long stay, you can create a simple piece of art. While your creation may not be accurate enough, you can capture an image or scene, or make a quick copy of an artwork you've seen.\r
			\r
			Additional abilities based on roll results:\r
			10 Write an accurate portrait\r
			20 Create a painting with a hidden message"""),
	JEWELER("Jeweler`s Tools", """
			Inside:\r
			• small saw\r
			• hammer,\r
			• files,\r
			• tongs,\r
			• tweezers.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			Magic.\r
			Proficiency with jeweler's tools gives you knowledge of known magical uses for gemstones. This knowledge will come in handy on Magic checks involving gems or gem-encrusted items.\r
			\r
			Investigation.\r
			When examining jewelry, mastering a jeweler's tools helps you find clues that others would overlook.\r
			\r
			And also you can:\r
			Jewel identification.\r
			You can identify the jewel and determine its value by eye.\r
			\r
			Additional abilities based on roll results:\r
			15 Change the appearance of a jewel\r
			20 Identify past jewels"""),
	CARDS("Playing Card Set", """
			Inside:\r
			A game set includes everything needed for a particular game or group of games, such as a full deck of cards or a board and pieces.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			History.\r
			Your mastery of the game includes knowledge of its history, as well as knowledge of important events and famous historical figures associated with the game.\r
			\r
			Insight.\r
			Playing with someone is a great way to understand their personality and what they are, which in turn allows you to better recognize lies and determine their mood.\r
			\r
			Sleight of hand.\r
			Sleight of Hand is a useful skill for cheating in the game, as it allows you to swap pieces, hide cards, or reverse a bad die roll. On the other hand, captivating the target with a game that requires dexterous movements is a good distraction for pickpocketing.\r
			\r
			Additional abilities based on roll results:\r
			15 Catch a player on a cheat\r
			15 Understand the character of the opponent"""),
	DICE("Dice Set", """
			Inside:\r
			A game set includes everything needed for a particular game or group of games, such as a full deck of cards or a board and pieces.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			History.\r
			Your mastery of the game includes knowledge of its history, as well as knowledge of important events and famous historical figures associated with the game.\r
			\r
			Insight.\r
			Playing with someone is a great way to understand their personality and what they are, which in turn allows you to better recognize lies and determine their mood.\r
			\r
			Sleight of hand.\r
			Sleight of Hand is a useful skill for cheating in the game, as it allows you to swap pieces, hide cards, or reverse a bad die roll. On the other hand, captivating the target with a game that requires dexterous movements is a good distraction for pickpocketing.\r
			\r
			Additional abilities based on roll results:\r
			15 Catch a player on a cheat\r
			15 Understand the character of the opponent"""),
	DISGUISE("Disguise Kit", """
			Inside:\r
			• various cosmetics,\r
			• hair dye,\r
			• some props,\r
			• several outfits.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			Deception.\r
			In some cases, makeup can help you spin more convincing lies.\r
			\r
			Intimidation.\r
			The right image can make you more intimidating when you want to scare someone away by pretending to be infected with the plague, or when you intimidate a gang of bandits under the guise of a thug-thug.\r
			\r
			Performance.\r
			The art of disguise can increase audience satisfaction with a performance, provided the guise is properly designed to evoke the desired reaction.\r
			\r
			Belief.\r
			People tend to trust people in uniform. If you disguise yourself as an authority figure, your attempts to convince others are usually more effective.\r
			\r
			And also you can:\r
			Form creation.\r
			As part of a long rest, you can create a disguise. It takes 1 minute to put on the skin after it is created. You can only have one of these skins on you at a time without drawing undue attention, unless you have a Bag of holding or some other similar way to keep them undetected. Each such guise weighs 1 pound.\r
			In other cases, it takes 10 minutes to create a skin that slightly changes your appearance, and 30 minutes if more extensive changes are required.\r
			\r
			Additional abilities based on roll results:\r
			10 Hide injuries or special features\r
			15 Recognize someone else's use of disguises\r
			20 Copy humanoid appearance"""),
	THREE_DRAGON("Three-Dragon Ante", """
			Inside:\r
			Components. A game set includes everything needed for a particular game or group of games, such as a full deck of cards or a board and pieces.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			History.\r
			Your mastery of the game includes knowledge of its history, as well as knowledge of important events and famous historical figures associated with the game.\r
			\r
			Insight.\r
			Playing with someone is a great way to understand their personality and what they are, which in turn allows you to better recognize lies and determine their mood.\r
			\r
			Sleight of hand.\r
			Sleight of Hand is a useful skill for cheating in the game, as it allows you to swap pieces, hide cards, or reverse a bad die roll. On the other hand, captivating the target with a game that requires dexterous movements is a good distraction for pickpocketing.\r
			\r
			Additional abilities based on roll results:\r
			15 Catch a player on a cheat\r
			15 Understand the character of the opponent"""),
	HERBAlISM("Herbalism Kit", """
			Inside:\r
			• bags for storing herbs,\r
			• secateurs,\r
			• leather gloves for collecting plants,\r
			• several glass jars,\r
			• mortar,\r
			• pestle.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			Magic.\r
			Your familiarity with the nature and use of plants can give you additional knowledge of magical research when it comes to plants and trying to identify a potion.\r
			\r
			Investigation.\r
			When you're exploring a planted area, owning this set helps you find details and clues that others might overlook.\r
			\r
			The medicine.\r
			Your skill as an herbalist enhances your ability to heal diseases and wounds, expanding the methods available through the use of medicinal plants.\r
			\r
			Nature and Survival.\r
			When you travel in the wilderness, your herbalism skills make it easier to identify plants and find food sources that others may not notice.\r
			\r
			And also you can:\r
			Plant identification. You can quickly recognize most plants by smell and appearance.\r
			\r
			Additional abilities based on roll results:\r
			15 Find plants\r
			20 Identify Poison"""),
	FROGERY("Forgery Kit", """
			Inside:\r
			• a small file,\r
			• a set of master keys,\r
			• a small mirror with a long handle,\r
			• a set of scissors with narrow blades,\r
			• a pair of tweezers.\r
			\r
			When you make a SKILL check you can gain the following benefits:\r
			\r
			Magic.\r
			The Forgery Kit can be used in conjunction with the Magic skill to determine if a magic item is real or fake.\r
			\r
			Deception.\r
			A well-executed forgery, such as a document certifying your nobility or a warrant guaranteeing you safe passage, can lend credibility to your lies.\r
			\r
			History.\r
			The Forgery Kit, along with your knowledge of history, improves your ability to forge historical documents or determine their authenticity.\r
			\r
			Investigation.\r
			When you inspect an item, owning a forgery kit helps determine how the item was made and whether it is genuine.\r
			\r
			And also you can:\r
			Other tools. Knowing how to use other tools makes your forgeries more believable. For example, you can combine ownership of a falsification kit and ownership of cartographer's tools to make a fake map.\r
			Quick fake. As part of a short break, you can create a fake document no longer than one page. As part of a long break, you can create a document up to four pages long. Your Intelligence check in using a fake kit determines the strength of your Intelligence (Investigation) check to determine if it's a fake.\r
			\r
			Additional abilities based on roll results:\r
			15 Fake Underline\r
			20 Copy Wax Seal""");

	Tools(String name, String text) {
		this.name = name;
		this.text = text;
	}

	@Getter
	private final String text;
	@Getter
	private final String name;

	public String toString() {
		return name;
	}

}