package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//Represent a list of Magical Beast
public class MagicalBeastList implements Writable {
    List<MagicalBeast> beastList;

    //EFFECTS: create an empty list of magical beast
    public MagicalBeastList() {
        this.beastList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add a magical beast to the beast list
    public void addMagicalBeast(MagicalBeast beast) {
        this.beastList.add(beast);
        EventLog.getInstance().logEvent(new Event("Beast " + beast.getBeastName()
                + " added to the registry."));
    }

    //MODIFIES: this
    //EFFECTS: remove a magical beast in the beast list
    public void removeMagicalBeast(MagicalBeast beast) {
        this.beastList.remove(beast);
        EventLog.getInstance().logEvent(new Event("Beast " + beast.getBeastName()
                + " removed from the registry."));
    }

    //EFFECTS: return true if the beastList is empty, otherwise false
    public boolean isEmpty() {
        return this.beastList.isEmpty();
    }

    //EFFECTS: return the full list of beastList
    public List<MagicalBeast> getAllMagicalBeasts() {
        EventLog.getInstance().logEvent(new Event("Displayed the list of beasts from registry."));
        return this.beastList;
    }

    //EFFECTS: return the full list of beast name
    public List<String> getAllMagicalBeastNames() {
        List<String> fullListName = new ArrayList<>();

        for (MagicalBeast b : this.beastList) {
            fullListName.add(b.getBeastName());
        }
        return fullListName;
    }

    //EFFECTS: return the magical beast at index position
    public MagicalBeast getMagicalBeast(int index) {
        return this.beastList.get(index);
    }

    //EFFECTS: return a list of magical beasts of the specific species
    public List<MagicalBeast> getFilteredMagicalBeastsBySpecies(String species) {
        List<MagicalBeast> specificSpecies = new ArrayList<>();

        for (MagicalBeast b : this.beastList) {
            if (species.equals(b.getSpeciesName())) {
                specificSpecies.add(b);
            }
        }
        EventLog.getInstance().logEvent(new Event("A list filtered by species, " + species
                + ", is displayed."));
        return specificSpecies;
    }

    //EFFECTS: return a list of magical beasts of the owner's
    public List<MagicalBeast> getFilteredMagicalBeastsByOwner(String ownerName) {
        List<MagicalBeast> specificOwner = new ArrayList<>();

        for (MagicalBeast magicalBeast : this.beastList) {
            if (ownerName.equals(magicalBeast.getOwnerName())) {
                specificOwner.add(magicalBeast);
            }
        }
        EventLog.getInstance().logEvent(new Event("A list filtered by owner's name, "
                + ownerName + ", is displayed."));
        return specificOwner;
    }

    //EFFECTS: return a list of magical beasts name of the specific beast name
    public List<String> getFilteredMagicalBeastsByName(String name) {
        List<String> specificName = new ArrayList<>();

        for (MagicalBeast b : this.beastList) {
            if (name.equals(b.getBeastName())) {
                specificName.add(b.getBeastName());
            }
        }
        EventLog.getInstance().logEvent(new Event("A list filtered by Beast's name, " + name
                + ", is displayed."));
        return specificName;
    }

    //EFFECTS: return a list of magical beasts name of the specific beast name
    public List<MagicalBeast> getFilteredMagicalBeastsByBeast(String name) {
        List<MagicalBeast> specificName = new ArrayList<>();

        for (MagicalBeast b : this.beastList) {
            if (name.equals(b.getBeastName())) {
                specificName.add(b);
            }
        }
        EventLog.getInstance().logEvent(new Event("A list filtered by Beast's name, " + name
                + ", is displayed."));
        return specificName;
    }

    //EFFECTS: return MagicalBeastList to JSON format
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("beastList", beastListToJson());
        return json;
    }

    // EFFECTS: returns Magical Beast in this Magical Beast List as a JSON array
    private JSONArray beastListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (MagicalBeast b : this.beastList) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }
}
