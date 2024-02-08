package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MagicalBeastListTest {
    private MagicalBeast flobberworm;
    private MagicalBeast ghoul;
    private MagicalBeast kneazle;
    private MagicalBeast griffin;
    private MagicalBeast quintaped;

    private MagicalBeastList list;

    @BeforeEach
    void setUp() {
        flobberworm = new MagicalBeast("Flobby", "Male", "Flobberworm", "Winsor");
        ghoul = new MagicalBeast("gouty", "Female", "Ghoul", "Black");
        kneazle = new MagicalBeast("Dr. McMeowMeoow", "Female",
                "Kneazle", "Crazy Cat Lady");
        griffin = new MagicalBeast("Sam", "Male", "Griffin", "Winsor");
        quintaped = new MagicalBeast("Windpour", "Non gendered", "Quintaped", "Mande");

        list = new MagicalBeastList();
    }

    @Test
    void testAddMagicalBeast() {
        assertTrue(list.getAllMagicalBeasts().isEmpty());
        list.addMagicalBeast(flobberworm);
        assertEquals(flobberworm, list.getAllMagicalBeasts().get(0));
        list.addMagicalBeast(griffin);
        assertEquals(griffin, list.getAllMagicalBeasts().get(1));
        assertEquals(2, list.getAllMagicalBeasts().size());
    }

    @Test
    void testRemoveMagicalBeast() {
        list.addMagicalBeast(flobberworm);
        list.addMagicalBeast(kneazle);
        list.addMagicalBeast(griffin);
        assertEquals(flobberworm, list.getAllMagicalBeasts().get(0));
        assertEquals(3, list.getAllMagicalBeasts().size());

        list.removeMagicalBeast(flobberworm);
        assertEquals(kneazle, list.getAllMagicalBeasts().get(0));
        assertEquals(2, list.getAllMagicalBeasts().size());

        list.removeMagicalBeast(griffin);
        assertEquals(kneazle, list.getAllMagicalBeasts().get(0));
        assertEquals(1, list.getAllMagicalBeasts().size());
    }

}
