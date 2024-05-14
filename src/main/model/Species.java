package model;

import java.util.ArrayList;
import java.util.Arrays;

// Represent a species and its associated danger warning and classification
public class Species {
    private String species;
    private String warning;
    private int index;
    private int classification;

    //EFFECTS: create a species with predetermined index, warning, classification related to its species
    public Species(String speciesName) {
        this.species = speciesName;
        this.index = getSpeciesIndex(speciesName);
        this.warning = getSpeciesWarning(this.index);
        this.classification = getSpeciesClassification(this.index);
    }

    //REQUIRES: species must be one of the speciesList
    //EFFECTS: return the index of the species in the species list
    private int getSpeciesIndex(String species) {
        ArrayList<String> speciesList = new ArrayList<>(Arrays.asList(
                "Flobberworm",
                "Ghoul",
                "Kneazle",
                "Griffin",
                "Quintaped",
                "Niffler")
        );

        int index = 0;

        for (int i = 0; i < speciesList.size(); i++) {
            if (species.equals(speciesList.get(i))) {
                index = i;
            }
        }
        return index;
    }

    //EFFECTS: return the warning at the index position in the warning list
    private String getSpeciesWarning(int index) {
        ArrayList<String> warningList = new ArrayList<>(Arrays.asList(
                "Produces mucus",
                null,
                "Highly intelligent",
                "Capable of Flight",
                "Carnivorous, taste for human flesh[2]" + "\n" + "Extremely dangerous and hostile towards humans",
                "Attracted to shiny objects and metals")
        );

        return warningList.get(index);
    }

    //EFFECTS: return the classification at the index position in the classification list
    private int getSpeciesClassification(int index) {
        ArrayList<Integer> classificationList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 3));

        return classificationList.get(index);
    }

    //EFFECTS: return species' name
    public String getSpeciesName() {
        return this.species;
    }

    //EFFECTS: return species' warning
    public String getWarning() {
        return this.warning;
    }

    //EFFECTS: return species' classification
    public int getClassification() {
        return this.classification;
    }
}
