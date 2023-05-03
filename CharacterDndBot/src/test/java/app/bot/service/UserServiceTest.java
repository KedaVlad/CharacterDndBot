package app.bot.service;

import app.bot.model.user.User;
import app.bot.repository.ActualHeroRepository;
import app.bot.repository.ScriptRepository;
import app.bot.repository.TrashRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private ActualHeroRepository actualHeroRepository;
    @Autowired
    private ScriptRepository scriptRepository;
    @Autowired
    private TrashRepository trashRepository;

    @Test
    public void testGetById() {
        // When
        User user = userService.getById(1L);

        // Then
        assertNotNull(user);
        assertNotNull(user.getActualHero());
        assertNotNull(user.getScript());
        assertNotNull(user.getTrash());
    }
    @Test
    public void testSave() {
        // Given
        User user = userService.getById(1L);
        actualHeroRepository.deleteById(1L);
        scriptRepository.deleteById(1L);
        trashRepository.deleteById(1L);
        // When
        userService.save(user);

        // Then
        assertTrue(actualHeroRepository.findById(1L).isPresent());
        assertTrue(scriptRepository.findById(1L).isPresent());
        assertTrue(trashRepository.findById(1L).isPresent());
    }
}