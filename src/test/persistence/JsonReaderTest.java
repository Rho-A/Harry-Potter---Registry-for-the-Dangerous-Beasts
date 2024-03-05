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
        ashSiblings.add("Niff");
        List<String> niffSiblings = new ArrayList<>();
        niffSiblings.add("Ash");

        try {
            MagicalBeastList list = reader.read();

            List<MagicalBeast> beastList = list.getAllMagicalBeasts();
            assertEquals(3, beastList.size());
            checkMagicalBeast("Will", "Female", "Griffin", "Stoneborne",
                    emptyList, emptyList, emptyList, emptyList, beastList.get(0));
            checkMagicalBeast("Ash", "Male", "Kneazle", "Von Azie",
                    emptyList, ashSiblings, emptyList, emptyList, beastList.get(1));
            checkMagicalBeast("Niff", "Male", "Kneazle", "Von Azie",
                    emptyList, niffSiblings, emptyList, emptyList, beastList.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
