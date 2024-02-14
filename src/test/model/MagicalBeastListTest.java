package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MagicalBeastListTest {
    private MagicalBeast flobberworm;
    private MagicalBeast ghoul;
    private MagicalBeast ghoul2;
    private MagicalBeast ghoul3;
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

        ghoul2 = new MagicalBeast("gouty2", "Female", "Ghoul", "Black");
        ghoul3 = new MagicalBeast("gouty", "Female", "Ghoul", "Black");
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

    @Test
    void testisEmpty() {
        assertEquals(0, list.getAllMagicalBeasts().size());
        assertTrue(list.isEmpty());
        list.addMagicalBeast(griffin);
        assertFalse(list.isEmpty());
        list.addMagicalBeast(quintaped);
        assertFalse(list.isEmpty());
    }

    @Test
    void testGetFilteredMagicalBeastsBySpecies() {
        list.addMagicalBeast(flobberworm);
        list.addMagicalBeast(kneazle);
        list.addMagicalBeast(griffin);
        list.addMagicalBeast(quintaped);
        list.addMagicalBeast(ghoul);

        List<MagicalBeast> newGhoulList = list.getFilteredMagicalBeastsBySpecies("Ghoul");
        assertEquals(ghoul, newGhoulList.get(0));
        assertEquals(1, newGhoulList.size());

        list.addMagicalBeast(ghoul2);
        List<MagicalBeast> secondGhoulList = list.getFilteredMagicalBeastsBySpecies("Ghoul");
        assertEquals(ghoul, secondGhoulList.get(0));
        assertEquals(ghoul2, secondGhoulList.get(1));
        assertEquals(2, secondGhoulList.size());

        List<MagicalBeast> newGriffenList = list.getFilteredMagicalBeastsBySpecies("Griffin");
        assertEquals(griffin, newGriffenList.get(0));
        assertEquals(1, newGriffenList.size());
    }

    @Test
    void testGetFilteredMagicalBeastsByOwner() {
        list.addMagicalBeast(flobberworm);
        list.addMagicalBeast(kneazle);
        list.addMagicalBeast(griffin);
        list.addMagicalBeast(quintaped);
        list.addMagicalBeast(ghoul);

        List<MagicalBeast> newBlackOwnerList = list.getFilteredMagicalBeastsByOwner("Black");
        assertEquals(1, newBlackOwnerList.size());
        assertEquals(ghoul, newBlackOwnerList.get(0));

        List<MagicalBeast> newWinsorOwnerList = list.getFilteredMagicalBeastsByOwner("Winsor");
        assertEquals(2, newWinsorOwnerList.size());
        assertEquals(flobberworm, newWinsorOwnerList.get(0));
        assertEquals(griffin, newWinsorOwnerList.get(1));
    }

    @Test
    void testGtFilteredMagicalBeastsByName() {
        list.addMagicalBeast(flobberworm);
        list.addMagicalBeast(kneazle);
        list.addMagicalBeast(griffin);
        list.addMagicalBeast(quintaped);
        list.addMagicalBeast(ghoul);
        list.addMagicalBeast(ghoul3);

        List<String> newList = list.getFilteredMagicalBeastsByName("gouty");
        assertEquals(2, newList.size());
        assertEquals(newList.get(1), newList.get(0));
    }

    @Test
    void testGetFilteredMagicalBeastsByBeast() {
        list.addMagicalBeast(flobberworm);
        list.addMagicalBeast(kneazle);
        list.addMagicalBeast(griffin);
        list.addMagicalBeast(quintaped);
        list.addMagicalBeast(ghoul);
        list.addMagicalBeast(ghoul3);

        List<MagicalBeast> afterFilter = new ArrayList<>();
        afterFilter.add(ghoul);
        afterFilter.add(ghoul3);

        assertEquals(afterFilter, list.getFilteredMagicalBeastsByBeast("gouty"));

        List<MagicalBeast> newList = list.getFilteredMagicalBeastsByBeast("gouty");
        assertEquals(2, newList.size());
        assertEquals(ghoul, newList.get(0));
        assertEquals(ghoul3, newList.get(1));
    }

    @Test
    void testGetMagicalBeast() {
        list.addMagicalBeast(flobberworm);
        list.addMagicalBeast(kneazle);
        list.addMagicalBeast(griffin);
        list.addMagicalBeast(quintaped);
        list.addMagicalBeast(ghoul);

        assertEquals(flobberworm, list.getMagicalBeast(0));
        assertEquals(ghoul, list.getMagicalBeast(4));
    }

    @Test
    void testGetAllMagicalBeastNames() {
        list.addMagicalBeast(flobberworm);
        list.addMagicalBeast(kneazle);

        List<String> fullNameList = new ArrayList<>();
        fullNameList.add("Flobby");
        fullNameList.add("Dr. McMeowMeoow");

        assertEquals(fullNameList, list.getAllMagicalBeastNames());
        assertEquals(2, list.getAllMagicalBeastNames().size());
        assertEquals("Flobby", list.getAllMagicalBeastNames().get(0));
        assertEquals("Dr. McMeowMeoow", list.getAllMagicalBeastNames().get(1));
    }

}
