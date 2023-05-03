package app.bot.controller;

import static org.junit.jupiter.api.Assertions.*;

import app.bot.model.event.CleanSpam;
import app.bot.model.event.EndSession;
import app.bot.model.event.StartSession;
import app.bot.model.user.User;
import app.bot.service.UserCacheService;
import app.player.model.event.StartGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import static org.mockito.Mockito.*;

class SessionControllerTest {

    private SessionController sessionController;

    @Mock
    private ApplicationEventPublisher mockEventPublisher;

    @Mock
    private UserCacheService mockUserCacheService;

    private Chat chat;

    private Long chatId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sessionController = new SessionController(mockEventPublisher, mockUserCacheService);
        chatId = 12345L;
        chat = new Chat();
        chat.setId(chatId);
    }

    @Test
    void keyByUpdateForMessage() {
        // given
        Update update = new Update();
        Message message = new Message();
        message.setChat(chat);
        update.setMessage(message);

        // when
        Long result = sessionController.keyByUpdate(update);

        // then
        assertEquals(chatId, result);
    }

    @Test
    void keyByUpdateForCallbackQuery() {
        // given

        Update update = new Update();
        CallbackQuery callbackQuery = new CallbackQuery();
        Message message = new Message();
        message.setChat(chat);
        callbackQuery.setMessage(message);
        update.setCallbackQuery(callbackQuery);

        // when
        Long result = sessionController.keyByUpdate(update);

        // then
        assertEquals(chatId, result);
    }


    @Test
    void keyByUpdateForOtherUpdates() {
        // given
        Update update = new Update();

        // when
        Long result = sessionController.keyByUpdate(update);

        // then
        assertEquals(1L, result);
    }

    @Test
    void testStartIfIdNotInSession() {
        // given
        Update update = new Update();
        Message message = new Message();
        message.setChat(chat);
        update.setMessage(message);
        StartSession startSession = new StartSession(this, update);
        User user = new User();
        user.setId(chatId);
        when(mockUserCacheService.getById(chatId)).thenReturn(user);

        // when
        sessionController.start(startSession);

        // then
        verify(mockEventPublisher, times(1)).publishEvent(any(StartGame.class));
        assertTrue(sessionController.getIdInSession().contains(chatId));
    }
    @Test
    void testStartIfIdInSession() {
        // given
        Update update = new Update();
        Message message = new Message();
        message.setChat(chat);
        update.setMessage(message);
        StartSession startSession1 = new StartSession(this, update);
        StartSession startSession2 = new StartSession(this, update);
        User user = new User();
        user.setId(chatId);
        when(mockUserCacheService.getById(chatId)).thenReturn(user);

        // when
        sessionController.start(startSession1);
        sessionController.start(startSession2);

        // then
        verify(mockEventPublisher, times(1)).publishEvent(any(CleanSpam.class));
    }

    @Test
    void testEnd() {
        // given
        Update update = new Update();
        Message message = new Message();
        message.setChat(chat);
        update.setMessage(message);
        StartSession startSession = new StartSession(this, update);
        User user = new User();
        user.setId(chatId);
        when(mockUserCacheService.getById(chatId)).thenReturn(user);
        sessionController.start(startSession);
        EndSession endSession = new EndSession(this, chatId);

        // when
        sessionController.end(endSession);

        // then
        assertFalse(sessionController.getIdInSession().contains(chatId));
    }
}
