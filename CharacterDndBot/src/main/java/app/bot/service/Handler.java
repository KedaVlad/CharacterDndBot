package app.bot.service;

import app.bot.model.user.User;
import app.bot.model.wrapp.WrappForHandler;

public interface Handler<T extends WrappForHandler<?>, W extends WrappForHandler<?>> {
	W handle(T t);
}

interface StartHandler<T extends User, W extends WrappForHandler<?>> {
	W handle(T t);
}

interface EndHandler<T extends WrappForHandler<?>, W extends User> {
	W handle(T t);
}