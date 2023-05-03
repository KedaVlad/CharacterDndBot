package app.bot.service;

import app.bot.model.user.ActualHero;
import app.bot.repository.ActualHeroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ActualHeroServiceTest {

    @Autowired
    private ActualHeroService actualHeroService;

    @Autowired
    private ActualHeroRepository actualHeroRepository;


    @Test
    public void testGetByIdWhenHeroExists() {
        // Given
        ActualHero actualHero = new ActualHero();
        actualHero.setId(1L);
        actualHeroRepository.save(actualHero);

        // When
        ActualHero result = actualHeroService.getById(1L);

        // Then
        assertEquals(result, actualHero);
    }

    @Test
    public void testGetByIdWhenHeroDoesNotExist() {

        if(actualHeroRepository.findById(1L).isPresent()) {
            actualHeroRepository.deleteById(1L);
        }
        // When
        ActualHero result = actualHeroService.getById(1L);

        // Then
        assertNotNull(result);
        assertEquals(result.getId(), 1L);
    }

    @Test
    public void testSave() {
        // Given
        ActualHero actualHero = new ActualHero();
        actualHero.setId(1L);
        actualHero.setCloudsWorked(new ArrayList<>());
        actualHero.setReadyToGame(false);

        // When
        actualHeroService.save(actualHero);

        // Then
        assertTrue(actualHeroRepository.findById(1L).isPresent());
        assertEquals(actualHeroRepository.findById(1L).get(), actualHero);
    }

}