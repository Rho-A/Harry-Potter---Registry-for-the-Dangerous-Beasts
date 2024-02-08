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
    public void addMagicalBeast(MagicalBeast id) {
        this.beastList.add(id);
    }

    //MODIFIES: this
    //EFFECTS: remove a magical beast in the beast list
    public void removeMagicalBeast(MagicalBeast id) {
        this.beastList.remove(id);
    }

    public List<MagicalBeast> getAllMagicalBeasts() {
        return this.beastList;
    }

    //EFFECTS: return a list of magical beasts of the specific species
    public List<MagicalBeast> getMagicalBeastsOfSpecies(Species species) {
        List<MagicalBeast> specificSpecies = new ArrayList<>();

        for (MagicalBeast magicalBeast : this.beastList) {
            if (species.equals(magicalBeast.getSpecies())) {
                specificSpecies.add(magicalBeast);
            }
        }
        return specificSpecies;
    }

    //EFFECTS: return a list of magical beasts of the owner's
    public List<MagicalBeast> getMagicalBeastsOfOwner(String ownerName) {
        List<MagicalBeast> specificOwner = new ArrayList<>();

        for (MagicalBeast magicalBeast : this.beastList) {
            if (ownerName.equals(magicalBeast.getOwnerName())) {
                specificOwner.add(magicalBeast);
            }
        }
        return specificOwner;
    }
}
