package model;

import java.util.ArrayList;
import java.util.List;

public class MagicalBeastList {
    List<MagicalBeast> beastList;

    //EFFECTS: create an empty list of magical beast
    public MagicalBeastList() {
        this.beastList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add a magical beast to the beast list
    public void addMagicalBeast(MagicalBeast beast) {
        this.beastList.add(beast);
    }

    //MODIFIES: this
    //EFFECTS: remove a magical beast in the beast list
    public void removeMagicalBeast(MagicalBeast beast) {
        this.beastList.remove(beast);
    }

    //EFFECTS: return true if the beastList is empty, otherwise false
    public boolean isEmpty() {
        return this.beastList.isEmpty();
    }

    //EFFECTS: return the full list of beastList
    public List<MagicalBeast> getAllMagicalBeasts() {
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
        return specificName;
    }
}
