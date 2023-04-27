package app.player.controller;

import app.player.event.StartGame;
import app.player.event.EndGame;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.*;

@SpringBootTest
class PlayerTest {

    @MockBean
    private Player player;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Test
    void start() {
        publisher.publishEvent(mock(StartGame.class));
        verify(player, times(1)).start(Mockito.any());
    }

    @Test
    void end() {
        publisher.publishEvent(mock(EndGame.class));
        verify(player, times(1)).end(Mockito.any());
    }
}