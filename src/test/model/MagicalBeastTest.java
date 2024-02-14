package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MagicalBeastTest {
    private MagicalBeast flobberworm;
    private MagicalBeast ghoul;
    private MagicalBeast kneazle;
    private MagicalBeast griffin;
    private MagicalBeast quintaped;

    @BeforeEach
    void setUp() {
        flobberworm = new MagicalBeast("Flobby", "Male", "Flobberworm", "Winsor");
        ghoul = new MagicalBeast("gouty", "Female", "Ghoul", "Black");
        kneazle = new MagicalBeast("Dr. McMeowMeoow", "Female",
                "Kneazle", "Crazy Cat Lady");
        griffin = new MagicalBeast("Sam", "Male", "Griffin", "Winsor");
        quintaped = new MagicalBeast("Windpour", "Non gendered", "Quintaped", "Mande");

    }

    @Test
    void testConstructor() {
        assertEquals("Flobby", flobberworm.getBeastName());
        assertEquals("Male", flobberworm.getGender());
        assertEquals("Flobberworm", flobberworm.getSpeciesName());
        assertEquals("Winsor", flobberworm.getOwnerName());
    }

    @Test
    void testRenameBeastName() {
        assertEquals("Dr. McMeowMeoow", kneazle.getBeastName());
        kneazle.setBeastName("myNewName");
        assertEquals("myNewName", kneazle.getBeastName());
    }

    @Test
    void testRenameOwnerName() {
        assertEquals("Black", ghoul.getOwnerName());
        ghoul.setOwnerName("White");
        assertEquals("White", ghoul.getOwnerName());
    }

    @Test
    void testAddParents() {
        assertTrue(flobberworm.getParents().isEmpty());
        flobberworm.addParents(ghoul);
        flobberworm.addParents(kneazle);
        flobberworm.addParents(griffin);
        assertEquals(3, flobberworm.getParents().size());
        assertEquals(ghoul, flobberworm.getParents().get(0));
        assertEquals(kneazle, flobberworm.getParents().get(1));
        assertEquals(griffin, flobberworm.getParents().get(2));
    }

    @Test
    void testAddSiblings() {
        assertTrue(ghoul.getSiblings().isEmpty());
        ghoul.addSiblings(flobberworm);
        ghoul.addSiblings(kneazle);
        assertEquals(2, ghoul.getSiblings().size());
        assertEquals(flobberworm, ghoul.getSiblings().get(0));
        assertEquals(kneazle, ghoul.getSiblings().get(1));
    }

    @Test
    void testAddOffspring() {
        assertTrue(flobberworm.getOffsprings().isEmpty());
        flobberworm.addOffsprings(ghoul);
        flobberworm.addOffsprings(kneazle);
        flobberworm.addOffsprings(quintaped);
        assertEquals(3, flobberworm.getOffsprings().size());
        assertEquals(ghoul, flobberworm.getOffsprings().get(0));
        assertEquals(kneazle, flobberworm.getOffsprings().get(1));
        assertEquals(quintaped, flobberworm.getOffsprings().get(2));
    }

    @Test
    void testGetSpeciesName() {
        assertEquals("Kneazle", kneazle.getSpeciesName());
        assertEquals("Ghoul", ghoul.getSpeciesName());
    }

    @Test
    void testGetSpeciesSpecificWarning() {
        assertNull(ghoul.getSpeciesSpecificWarning());
        assertEquals("Carnivorous, taste for human flesh[2]" + "\n" + "Extremely dangerous and hostile towards humans",
                quintaped.getSpeciesSpecificWarning());
    }

    @Test
    void testGetClassification() {
        assertEquals(2, ghoul.getClassification());
        assertEquals(5, quintaped.getClassification());
    }

    @Test
    void testAddExtraNotes() {
        quintaped.addExtraNotes("Highly skittish.");
        quintaped.addExtraNotes("Caution recommended.");
        assertEquals("Highly skittish.", quintaped.getExtraNotes().get(0));
        assertEquals("Caution recommended.", quintaped.getExtraNotes().get(1));
    }

    @Test
    void testGetClassificationInX() {
        assertEquals("X", flobberworm.getClassificationInX());
        assertEquals("XXXXX", quintaped.getClassificationInX());
    }

    @Test
    void testGetUniqueId() {
        assertEquals(40, quintaped.getUniqueId());
    }

}