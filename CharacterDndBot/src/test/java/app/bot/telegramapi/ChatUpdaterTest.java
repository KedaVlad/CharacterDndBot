package app.bot.telegramapi;

import app.bot.model.event.ChatUpdate;
import app.player.controller.Player;
import app.player.model.event.EndGame;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.*;

@SpringBootTest
class ChatUpdaterTest {

    @MockBean
    private ChatUpdater chatUpdater;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private Player player;

    @Test
    void completeTest() {
        publisher.publishEvent(mock(ChatUpdate.class));
        verify(chatUpdater, times(1)).complete(Mockito.any());
    }
    @Test
    void connectionWithPlayer() {
        player.end(mock(EndGame.class));
        verify(chatUpdater, times(1)).complete(Mockito.any());
    }

}