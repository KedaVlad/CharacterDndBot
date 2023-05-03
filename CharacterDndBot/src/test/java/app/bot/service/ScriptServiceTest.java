package app.bot.service;

import app.bot.model.user.Script;
import app.bot.repository.ScriptRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ScriptServiceTest {

    @Autowired
    private ScriptService scriptService;
    @Autowired
    private ScriptRepository scriptRepository;


    @Test
    public void testGetByIdWhenScriptExists() {
        // Given
        Script script = new Script();
        script.setId(1L);
        scriptRepository.save(script);

        // When
        Script result = scriptService.getById(1L);

        // Then
        assertEquals(result, script);
    }

    @Test
    public void testGetByIdWhenScriptDoesNotExist() {

        if(scriptRepository.findById(1L).isPresent()) {
            scriptRepository.deleteById(1L);
        }
        // When
        Script result = scriptService.getById(1L);

        // Then
        assertNotNull(result);
        assertEquals(result.getId(), 1L);
    }

    @Test
    public void testSave() {
        // Given
        Script script = new Script();
        script.setId(1L);

        // When
        scriptService.save(script);

        // Then
        assertTrue(scriptRepository.findById(1L).isPresent());
        assertEquals(scriptRepository.findById(1L).get(), script);
    }

}