package app.bot.service;

import app.bot.model.user.Trash;
import app.bot.repository.TrashRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TrashServiceTest {

    @Autowired
    private TrashService trashService;

    @Autowired
    private TrashRepository trashRepository;


    @Test
    public void testGetByIdWhenTrashExists() {
        // Given
        Trash trash = new Trash();
        trash.setId(1L);
        trashRepository.save(trash);

        // When
        Trash result = trashService.getById(1L);

        // Then
        assertEquals(result, trash);
    }

    @Test
    public void testGetByIdWhenTrashDoesNotExist() {

        if(trashRepository.findById(1L).isPresent()) {
            trashRepository.deleteById(1L);
        }
        // When
        Trash result = trashService.getById(1L);

        // Then
        assertNotNull(result);
        assertEquals(result.getId(), 1L);
    }

    @Test
    public void testSave() {
        // Given
        Trash trash = new Trash();
        trash.setId(1L);

        // When
        trashService.save(trash);

        // Then
        assertTrue(trashRepository.findById(1L).isPresent());
        assertEquals(trashRepository.findById(1L).get(), trash);
    }

}