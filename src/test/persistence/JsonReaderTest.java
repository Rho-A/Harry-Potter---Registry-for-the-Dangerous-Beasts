package persistence;

import model.MagicalBeast;
import model.MagicalBeastList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MagicalBeastList list = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // Pass
        }
    }

    @Test
    void testReaderEmptyMagicalBeastList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMagicalBeastList.json");
        try {
            MagicalBeastList list = reader.read();
            assertTrue(list.isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMagicalBeastList() {
        JsonReader reader = new JsonReader("./data/testReaderMagicalBeastList.json");
        List<String> emptyList = new ArrayList<>();

        List<String> ashSiblings = new ArrayList<>();
        List<String> ashParents = new ArrayList<>();
        List<String> ashOffsprings = new ArrayList<>();
        List<String> ashExtraNotes = new ArrayList<>();
        ashSiblings.add("Niff");
        ashParents.add("John");
        ashOffsprings.add("West");
        ashExtraNotes.add("Danger");

        List<String> niffSiblings = new ArrayList<>();
        List<String> niffParents = new ArrayList<>();
        List<String> niffOffsprings = new ArrayList<>();
        List<String> niffExtraNotes = new ArrayList<>();
        niffSiblings.add("Ash");
        niffParents.add("Timmy");
        niffOffsprings.add("East");
        niffExtraNotes.add("Danger level 5");

        try {
            MagicalBeastList list = reader.read();

            List<MagicalBeast> beastList = list.getAllMagicalBeasts();
            assertEquals(3, beastList.size());
            checkMagicalBeast("Will", "Female", "Griffin", "Stoneborne",
                    emptyList, emptyList, emptyList, emptyList, beastList.get(0));
            checkMagicalBeast("Ash", "Male", "Kneazle", "Von Azie",
                    ashParents, ashSiblings, ashOffsprings, ashExtraNotes, beastList.get(1));
            checkMagicalBeast("Niff", "Male", "Kneazle", "Von Azie",
                    niffParents, niffSiblings, niffOffsprings, niffExtraNotes, beastList.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
