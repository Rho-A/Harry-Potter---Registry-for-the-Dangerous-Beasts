package persistence;

import model.MagicalBeast;
import model.MagicalBeastList;
import model.Species;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// A reader that reads registry from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs JsonReader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    public MagicalBeastList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMagicalBeastList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses MagicalBeastList from JSON object and returns it
    private MagicalBeastList parseMagicalBeastList(JSONObject jsonObject) {
        //String name = jsonObject.getString("name");

        MagicalBeastList registry = new MagicalBeastList();
        addBeastList(registry, jsonObject);
        return registry;
    }

    // MODIFIES: registry
    // EFFECTS: parses beast list from JSON object and adds them to registry
    private void addBeastList(MagicalBeastList registry, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("beastList");
        for (Object json : jsonArray) {
            JSONObject nextBeast = (JSONObject) json;
            addBeast(registry, nextBeast);
        }
    }

    // MODIFIES: beastList
    // EFFECTS: parses Magical Beast from JSON object and adds it to beastList
    private void addBeast(MagicalBeastList beastList, JSONObject jsonObject) {
        //int nextUniqueId = jsonObject.getInt("nextUniqueId");
        String uniqueId = jsonObject.getString("uniqueId");
        String beastName = jsonObject.getString("beastName");
        String gender = jsonObject.getString("gender");

        String speciesName = jsonObject.getString("speciesName");

        String ownerName = jsonObject.getString("ownerName");

        JSONArray jsonParents = jsonObject.getJSONArray("parents");
        List<String> parents = modifyParents(jsonParents);

        JSONArray jsonSiblings = jsonObject.getJSONArray("siblings");
        List<String> siblings = modifySiblings(jsonSiblings);

        JSONArray jsonOffsprings = jsonObject.getJSONArray("offsprings");
        List<String> offsprings = modifyOffsprings(jsonOffsprings);

        JSONArray jsonExtraNotes = jsonObject.getJSONArray("extraNotes");
        List<String> extraNotes = modifyExtraNotes(jsonExtraNotes);

        MagicalBeast beast = new MagicalBeast(beastName, gender, speciesName, ownerName);
        beast.setUniqueId(uniqueId);
        beast.setParents(parents);
        beast.setSiblings(siblings);
        beast.setOffsprings(offsprings);
        beast.setExtraNotes(extraNotes);

        beastList.addMagicalBeast(beast);
    }

    //EFFECTS: return a list of parents names from jsonParents
    private List<String> modifyParents(JSONArray jsonParents) {
        List<String> parents = new ArrayList<>();
        for (int i = 0; i < jsonParents.length(); i++) {
            parents.add(jsonParents.getString(i));
        }
        return parents;
    }

    //EFFECTS: return a list of siblings names from jsonSiblings
    private List<String> modifySiblings(JSONArray jsonSiblings) {
        List<String> siblings = new ArrayList<>();
        for (int i = 0; i < jsonSiblings.length(); i++) {
            siblings.add(jsonSiblings.getString(i));
        }
        return siblings;
    }

    //EFFECTS: return a list of offsprings names from jsonOffsprings
    private List<String> modifyOffsprings(JSONArray jsonOffsprings) {
        List<String> offsprings = new ArrayList<>();
        for (int i = 0; i < jsonOffsprings.length(); i++) {
            offsprings.add(jsonOffsprings.getString(i));
        }

        return offsprings;
    }

    //EFFECTS: return a list of extra notes names from jsonExtraNotes
    private List<String> modifyExtraNotes(JSONArray jsonExtraNotes) {
        List<String> extraNotes = new ArrayList<>();
        for (int i = 0; i < jsonExtraNotes.length(); i++) {
            extraNotes.add(jsonExtraNotes.getString(i));
        }
        return extraNotes;
    }

}
