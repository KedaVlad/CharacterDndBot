package app.dnd.repository;

import app.dnd.model.wrapp.RaceDndWrapp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RaceDndWrappRepositoryTest {

    @Autowired
    private RaceDndWrappRepository raceDndWrappRepository;
    private RaceDndWrapp race1;
    private RaceDndWrapp race2;

    @BeforeEach
    void setUp() {
        race1 = new RaceDndWrapp();
        race1.setRaceName("Race1");
        race1.setSubRace("SubRace1");
        race1.setInformation("Info1");
        race2 = new RaceDndWrapp();
        race2.setRaceName("Race2");
        race2.setSubRace("SubRace2");
        race2.setInformation("Info2");
        raceDndWrappRepository.saveAll(List.of(race1, race2));
    }
    @Test
    public void testFindDistinctRaceName() {
        List<String> raceNames = raceDndWrappRepository.findDistinctRaceName();
        assertNotNull(raceNames);
        assertTrue(raceNames.contains("Race1"));
        assertTrue(raceNames.contains("Race2"));
    }

    @Test
    public void testFindDistinctSubRaceByRaceName() {
        List<String> subRaces = raceDndWrappRepository.findDistinctSubRaceByRaceName("Race1");
        assertNotNull(subRaces);
        assertTrue(subRaces.contains("SubRace1"));
    }

    @Test
    public void testFindDistinctInformationByRaceNameAndSubRace() {
        String information = raceDndWrappRepository.findDistinctInformationByRaceNameAndSubRace("Race1", "SubRace1");
        assertNotNull(information);
        assertEquals("Information1", information);
    }

    @Test
    public void testFindByRaceNameAndSubRace() {
        RaceDndWrapp raceDndWrapp = raceDndWrappRepository.findByRaceNameAndSubRace("Race1", "SubRace1");
        assertNotNull(raceDndWrapp);
        assertEquals("Race1", raceDndWrapp.getRaceName());
        assertEquals("SubRace1", raceDndWrapp.getSubRace());
        assertEquals("Information1", raceDndWrapp.getInformation());
    }

    @AfterEach
    void tearDown() {
        raceDndWrappRepository.deleteAll(List.of(race1, race2));
    }
}