package persistence;

import model.MagicalBeast;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMagicalBeast
            (String beastName, String gender, String species, String ownerName,
             List<String> parents, List<String> siblings, List<String> offsprings, List<String> extraNotes,
             MagicalBeast beast) {
        assertEquals(beastName, beast.getBeastName());
        assertEquals(gender, beast.getGender());
        assertEquals(species, beast.getSpeciesName());
        assertEquals(parents, beast.getParents());
        assertEquals(siblings, beast.getSiblings());
        assertEquals(offsprings, beast.getOffsprings());
        assertEquals(extraNotes, beast.getExtraNotes());
    }
}
