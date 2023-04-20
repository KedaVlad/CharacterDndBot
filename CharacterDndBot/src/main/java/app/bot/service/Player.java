package app.bot.service;


import app.bot.event.EndGame;
import app.bot.event.StartGame;
import app.bot.model.UserCore;

public interface Player<T extends UserCore> {

	public void start(StartGame<T> startSession);
	public void end(EndGame endSession);
}
