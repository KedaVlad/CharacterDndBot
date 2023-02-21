package com.dnd.CharacterDndBot.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.bot.model.act.SingleAct;
import app.bot.model.user.User;
import app.dnd.dto.CharacterDnd;

class UserTest {

	User user;
	CharacterDnd character;
	
	@BeforeEach
	void setUp() throws Exception 
	{
		user = new User(1L);
		character = new CharacterDnd("Some char");
		character.getClouds().add(SingleAct.builder().name("cloud").text("cloud text").build());
		character.getClouds().add(SingleAct.builder().name("cloud2").text("cloud text2").build());
		user.getCharactersPool().setActual(character);
	}
	
	@Test
	void testSetActual() 
	{
		assertEquals(2, user.getCharactersPool().getClouds().getCloudsTarget().size());
	}
	
	@Test
	void testMakeWork() 
	{
		SingleAct testAct = SingleAct.builder().name("cloud3").text("cloud text3").build();
		character.getClouds().add(testAct);
		user.targetAct(SingleAct.builder().name("target act").text("target text").build());
		assertEquals(4, user.makeSend().getReadyToSend().size());
	}

	@Test
	void testCheckTrash() 
	{
		user.getScript().getTrash().add(1);
		user.getCharactersPool().getClouds().getTrash().add(2);
		user.targetAct(SingleAct.builder().name("target act").text("target text").build());
		assertEquals(2, user.makeSend().getTrash().size());
	}
}
