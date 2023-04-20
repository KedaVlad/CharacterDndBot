package app.player.service;

import app.player.event.UserEvent;

public interface Handler<W> {

	public void handle(UserEvent<W> event);
}
