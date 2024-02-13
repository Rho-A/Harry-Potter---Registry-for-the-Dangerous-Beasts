package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SpeciesTest {
    private Species flobberworm;
    private Species ghoul;
    private Species kneazle;
    private Species griffin;
    private Species quintaped;

    @BeforeEach
    void setUp() {
        flobberworm = new Species("Flobberworm");
        ghoul = new Species("Ghoul");
        kneazle = new Species("Kneazle");
        griffin = new Species("Griffin");
        quintaped = new Species("Quintaped");
    }

    @Test
    void testGetSpeciesName() {
        assertEquals("Flobberworm", flobberworm.getSpeciesName());
        assertEquals("Ghoul", ghoul.getSpeciesName());
        assertEquals("Kneazle", kneazle.getSpeciesName());
        assertEquals("Griffin", griffin.getSpeciesName());
        assertEquals("Quintaped", quintaped.getSpeciesName());
    }

    @Test
    void testGetWarning() {
        assertEquals("Highly intelligent", kneazle.getWarning());
        assertNull(ghoul.getWarning());
        assertEquals("Carnivorous, taste for human flesh[2]" + "\n" + "Extremely dangerous and hostile towards humans",
                quintaped.getWarning());
    }

    @Test
    void testGetClassification() {
        assertEquals(5, quintaped.getClassification());
        assertEquals(3,kneazle.getClassification());
    }
}
