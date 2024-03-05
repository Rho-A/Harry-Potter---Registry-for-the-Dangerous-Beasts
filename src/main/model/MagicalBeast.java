package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a magical beast with the following:
//      unique id, beast's name, gender, species, species specific warning
//      classification, owner's name, familial relation, extra notes (free text)
public class MagicalBeast implements Writable {
    private static int nextUniqueId = 1; //unique id of next beast
    private final int uniqueId; //unique id of the beast
    private String beastName; //beast's name
    private final String gender; //beast's gender

    private final Species species; //beast's species
    private final String speciesName;
    private final String speciesSpecificWarning; //warning specific to the species
    private final int classification; //beast's classification, from 1 to 5

    private String ownerName; //name of the beast's owner

    private List<String> parents; //familial relation - parents
    private List<String> siblings; //familial relation - siblings
    private List<String> offsprings; //familial relation - offsprings

    private List<String> extraNotes; //extra notes - free text

    //REQUIRES: beastName, species and ownerName have non-zero length
    //          gender must be one of the three: Male, Female, Non-gendered
    //EFFECTS: create a magical beast with a unique id
    public MagicalBeast(String beastName, String gender, String species, String ownerName) {
        this.uniqueId = nextUniqueId++;
        this.beastName = beastName;
        this.gender = gender;
        this.species = new Species(species);
        this.speciesName = species;
        this.speciesSpecificWarning = this.species.getWarning();
        this.classification = this.species.getClassification();
        this.ownerName = ownerName;
        this.parents = new ArrayList<>();
        this.siblings = new ArrayList<>();
        this.offsprings = new ArrayList<>();
        this.extraNotes = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: set list of parents
    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    //MODIFIES: this
    //EFFECTS: set list of siblings
    public void setSiblings(List<String> siblings) {
        this.siblings = siblings;
    }

    //MODIFIES: this
    //EFFECTS: set list of offsprings
    public void setOffsprings(List<String> offsprings) {
        this.offsprings = offsprings;
    }

    //MODIFIES: this
    //EFFECTS: rename beast name
    public void setBeastName(String newName) {
        this.beastName = newName;
    }

    //MODIFIES: this
    //EFFECTS: change to a new owner
    public void setOwnerName(String newOwner) {
        this.ownerName = newOwner;
    }

    //MODIFIES: this
    //EFFECTS: add a magical beast to the parent list
    public void addParents(MagicalBeast id) {
        this.parents.add(id.getBeastName());
    }

    //MODIFIES: this
    //EFFECTS: add a magical beast to the siblings list
    public void addSiblings(MagicalBeast id) {
        this.siblings.add(id.getBeastName());
    }

    //MODIFIES: this
    //EFFECTS: add a magical beast to the offsprings list
    public void addOffsprings(MagicalBeast id) {
        this.offsprings.add(id.getBeastName());
    }

    //MODIFIES: this
    //EFFECTS: add new notes to the extra note list
    public void addExtraNotes(String newNotes) {
        this.extraNotes.add(newNotes);
    }

    //EFFECTS: return the unique id
    public int getUniqueId() {
        return this.uniqueId;
    }

    //EFFECTS: return beast's name
    public String getBeastName() {
        return this.beastName;
    }

    //EFFECTS: return beast gender
    public String getGender() {
        return this.gender;
    }

    //EFFECTS: return beast species' name
    public String getSpeciesName() {
        return this.speciesName;
    }

    //EFFECTS: return species warning
    public String getSpeciesSpecificWarning() {
        return this.speciesSpecificWarning;
    }

    //EFFECTS: return species classification
    public int getClassification() {
        return this.classification;
    }

    //EFFECTS: return species classification in X format
    public String getClassificationInX() {
        String classification = "";

        for (int i = 0; i < this.classification; i++) {
            classification = classification + "X";
        }
        return classification;
    }

    //EFFECTS: return owner's name
    public String getOwnerName() {
        return this.ownerName;
    }

    //EFFECTS: return parent list
    public List<String> getParents() {
        return this.parents;
    }

    //EFFECTS: return siblings list
    public List<String> getSiblings() {
        return this.siblings;
    }

    //EFFECTS: return offsprings list
    public List<String> getOffsprings() {
        return this.offsprings;
    }

    //EFFECTS: return list of extra notes
    public List<String> getExtraNotes() {
        return this.extraNotes;
    }

    //EFFECTS: return MagicalBeast in Json format
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        //json.put("uniqueId", uniqueId);
        json.put("beastName", beastName);
        json.put("gender", gender);

        //json.put("species", species);
        json.put("speciesName", speciesName);
        //json.put("speciesSpecificWarning", speciesSpecificWarning);
        //json.put("classification", classification);

        json.put("ownerName", ownerName);
        json.put("parents", parents);
        json.put("siblings", siblings);
        json.put("offsprings", offsprings);
        json.put("extraNotes", extraNotes);
        return json;
    }

}
