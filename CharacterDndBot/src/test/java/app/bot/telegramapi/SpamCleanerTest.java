package app.bot.telegramapi;

import app.bot.model.event.CleanSpam;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.*;
@SpringBootTest
class SpamCleanerTest {
    @MockBean
    private SpamCleaner spamCleaner;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Test
    void cleanSpamTest() {
        publisher.publishEvent(mock(CleanSpam.class));
        verify(spamCleaner, times(1)).cleanSpam(Mockito.any());
    }
}