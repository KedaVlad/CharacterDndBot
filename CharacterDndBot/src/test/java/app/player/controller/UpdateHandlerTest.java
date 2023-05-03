package app.player.controller;

import app.player.model.event.UserEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class UpdateHandlerTest {

    private UserEvent<Update> eventStart;
    private ApplicationEventPublisher publisher;
    private UpdateHandler handler;
    @BeforeEach
    void setUp() {
        eventStart = mock(UserEvent.class);
        handler = mock(UpdateHandler.class);
        publisher = mock(ApplicationEventPublisher.class);
    }

    @Test
    void handle() {
        publisher.publishEvent(eventStart);
        Mockito.verify(handler, times(1)).handle(Mockito.any());
    }
}