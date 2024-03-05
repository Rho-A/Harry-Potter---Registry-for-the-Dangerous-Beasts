package persistence;

import model.MagicalBeast;
import model.MagicalBeastList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            MagicalBeastList list = new MagicalBeastList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyMagicalBeastList() {
        try {
            MagicalBeastList list = new MagicalBeastList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMagicalBeastList.json");
            writer.open();
            writer.write(list);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMagicalBeastList.json");
            list = reader.read();
            assertTrue(list.isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMagicalBeastList() {
        try {
            MagicalBeastList list = new MagicalBeastList();
            list.addMagicalBeast(new MagicalBeast("Will", "Male",
                    "Griffin", "Willborne"));
            list.addMagicalBeast(new MagicalBeast("Louis", "Female",
                    "Ghoul", "Nox"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMagicalBeastList.json");
            writer.open();
            writer.write(list);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMagicalBeastList.json");
            list = reader.read();
            List<String> emptyList = new ArrayList<>();

            List<MagicalBeast> beastList = list.getAllMagicalBeasts();
            assertEquals(2, beastList.size());

            checkMagicalBeast("Will", "Male", "Griffin", "Willborne",
                    emptyList, emptyList, emptyList, emptyList, beastList.get(0));
            checkMagicalBeast("Louis", "Female","Ghoul", "Nox",
                    emptyList, emptyList, emptyList, emptyList, beastList.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
