package app.bot.controller;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class MyCustomEvent extends ApplicationEvent {

	public MyCustomEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
   
}
