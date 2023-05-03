package app.dnd.repository;

import app.dnd.model.wrapp.ClassDndWrapp;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ClassDndWrappRepositoryTest {

    @Autowired
    private ClassDndWrappRepository classDndWrappRepository;
    private ClassDndWrapp class1;
    private ClassDndWrapp class2;
    @BeforeEach
    void setUp() {
        class1 = new ClassDndWrapp();
        class1.setClassName("ClassName1");
        class1.setArchetype("ClassArchetype1");
        class1.setInformation("ClassInformation1");
        class2 = new ClassDndWrapp();
        class2.setClassName("ClassName2");
        class2.setArchetype("ClassArchetype2");
        class2.setInformation("ClassInformation2");
        classDndWrappRepository.saveAll(List.of(class1, class2));
    }
    @Test
    public void testFindDistinctClassName() {
        List<String> classNames = classDndWrappRepository.findDistinctClassName();
        assertTrue(classNames.contains("ClassName1"));
        assertTrue(classNames.contains("ClassName2"));
    }

    @Test
    public void testFindDistinctArchetypeByClassName() {
        List<String> archetypes = classDndWrappRepository.findDistinctArchetypeByClassName("ClassName1");
        assertEquals(1, archetypes.size());
        assertTrue(archetypes.contains("ClassArchetype1"));
    }

    @Test
    public void testFindDistinctInformationByClassNameAndArchetype() {
        String information = classDndWrappRepository.findDistinctInformationByClassNameAndArchetype("ClassName1", "ClassArchetype1");
        assertEquals("ClassInformation1", information);
    }

    @Test
    public void testFindByClassNameAndArchetype() {
        ClassDndWrapp class1 = classDndWrappRepository.findByClassNameAndArchetype("ClassName1", "ClassArchetype1");
        assertNotNull(class1);
        assertEquals("ClassName1", class1.getClassName());
        assertEquals("ClassArchetype1", class1.getArchetype());
        assertEquals("ClassInformation1", class1.getInformation());
    }
    @AfterEach
    void tearDown() {
        classDndWrappRepository.deleteAll(List.of(class1, class2));
    }
}