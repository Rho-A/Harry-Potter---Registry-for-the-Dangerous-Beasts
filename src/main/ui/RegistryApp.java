package ui;

import model.MagicalBeast;
import model.MagicalBeastList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Citation: https://github.students.cs.ubc.ca/CPSC210/TellerApp
//           from CPSC 210 - TellerApp

// Magical Beast Registry application
public class RegistryApp {
    private static final String JSON_STORE = "./data/registry.json";
    private MagicalBeastList fullRegistry = new MagicalBeastList();
    private Scanner userInput = new Scanner(System.in);
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    ArrayList<String> speciesList = new ArrayList<>(Arrays.asList(
            "Flobberworm",
            "Ghoul",
            "Kneazle",
            "Griffin",
            "Quintaped")
    );

    public RegistryApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        runRegistry();
    }

    //EFFECTS: create a running registry app, the app terminates when 'q' is entered
    private void runRegistry() {
        boolean keepGoing = true;
        String userCommand = null;

        while (keepGoing) {
            displayMenu();
            userCommand = userInput.next();
            userCommand = userCommand.toLowerCase();

            if (userCommand.equals("q")) {
                keepGoing = false;
            } else {
                processUserCommand(userCommand);
            }
        }
        System.out.println("Have a wonderful day! Goodbye.");
    }

    //EFFECTS: display the main menu of the app
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add Beast");
        System.out.println("\tr -> remove Beast");
        System.out.println("\tm -> modify Beast details");
        System.out.println("\td -> display");
        System.out.println("\ts -> save work room to file");
        System.out.println("\tl -> load work room from file");
        System.out.println("\tq -> quit");
    }

    //EFFECTS: direct user to specific function/method based on user command
    private void processUserCommand(String userCommand) {
        if (userCommand.equals("a")) {
            doAddBeast();
        } else if (userCommand.equals("r")) {
            doRemoveBeast();
        } else if (userCommand.equals("m")) {
            doModifyBeastDetails();
        } else if (userCommand.equals("d")) {
            doDisplay();
        } else if (userCommand.equals("s")) {
            saveRegistry();
        } else if (userCommand.equals("l")) {
            loadRegistry();
        } else {
            System.out.println("Selection not valid. Please retry.");
        }
    }

    //MODIFIES: fullRegistry
    //EFFECTS: add new magical beast to the current registry
    private void doAddBeast() {
        System.out.println("What is the Beast's name?");
        String beastName = userInput.next();
        String beastGender = askBeastGender();
        String beastSpecies = askBeastSpecies();

        System.out.println("What is the owner's name?");
        String beastOwner = userInput.next();

        addToRegistry(createBeast(beastName, beastGender, beastSpecies, beastOwner));

        System.out.println(beastName + " has been successfully added.");
    }

    //EFFECTS: ask for beast's gender
    private String askBeastGender() {
        boolean keepGoingGender = true;
        String beastGender = "";
        while (keepGoingGender) {
            System.out.println("What is the Beast's gender? Choose one of the following: Male, Female, Unknown.");
            beastGender = userInput.next();

            if (beastGender.equals("Male") || beastGender.equals("Female") || beastGender.equals("Unknown")) {
                keepGoingGender = false;
            }
        }
        return beastGender;
    }

    //EFFECTS: ask for beast's species
    private String askBeastSpecies() {
        boolean keepGoing = true;
        String beastSpecies = "";

        while (keepGoing) {
            System.out.println("What is the Beast's species? Choose one of the following:"
                    + speciesList);
            beastSpecies = userInput.next();

            if (checkCorrectSpecies(beastSpecies)) {
                keepGoing = false;
            } else {
                System.out.println("Species entered does not exist, please enter again.");
            }
        }
        return beastSpecies;
    }

    //EFFECTS: return true when the input species is in the species list
    private boolean checkCorrectSpecies(String species) {
        for (String name : speciesList) {
            if (species.equals(name)) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: beastGender must be one of: Male, Female or Unknown
    //          beastSpecies must be one of the species in speciesList
    //EFFECTS: create a new magical beast with defined name, gender, species, owner
    private MagicalBeast createBeast(String beastName, String beastGender, String beastSpecies, String beastOwner) {
        return new MagicalBeast(beastName, beastGender, beastSpecies, beastOwner);
    }

    //MODIFIES: fullRegistry
    //EFFECTS: add a magical beast into the registry
    private void addToRegistry(MagicalBeast beast) {
        fullRegistry.addMagicalBeast(beast);
    }

    //MODIFIES: fullRegistry
    //EFFECTS: remove a beast from the registry
    private void doRemoveBeast() {
        String beastName = chooseBeast("remove");

        List<String> listWithSameNameInString = fullRegistry.getFilteredMagicalBeastsByName(beastName);
        List<MagicalBeast> listWithSameNameInBeast = fullRegistry.getFilteredMagicalBeastsByBeast(beastName);

        int position = positionBeast(listWithSameNameInString, "modify");
        MagicalBeast beastToRemove = listWithSameNameInBeast.get(position);

        String input = confirmBeast("remove", beastToRemove);

        if (input.equals("y")) {

            fullRegistry.removeMagicalBeast(beastToRemove);
            System.out.println(beastToRemove.getBeastName() + " has been removed successfully.");
        } else {
            runRegistry();
        }
    }

    //EFFECTS: return the name of the beast that user wishes to act upon
    private String chooseBeast(String action) {
        System.out.println("What is the name of the beast you wish to " + action + "?");
        return userInput.next();
    }

    //EFFECTS: return the index of the targeted beast in the filtered list
    private int positionBeast(List<String> filteredList, String action) {
        System.out.println("Which of the following would you like to " + action + "?"
                + " Select by entering the position (starting from 0).");
        System.out.println(filteredList);
        return userInput.nextInt();
    }

    //EFFECTS: return y if it is the correct beast to perform action on; otherwise any other keys
    private String confirmBeast(String action, MagicalBeast beast) {
        displayBeastFullInfo(beast);
        System.out.println("Is this the beast you wish to " + action + "?"
                + " Press y to confirm, press any other key to quit");

        return userInput.next();
    }

    //EFFECTS: display functions to modify beast details
    private void doModifyBeastDetails() {
        String beastName = chooseBeast("modify");

        List<String> listWithSameNameInString = getFilteredListStringByName(beastName);
        List<MagicalBeast> listWithSameNameInBeast = getFilteredListMagicalBeastByName(beastName);

        int position = positionBeast(listWithSameNameInString, "modify");
        MagicalBeast beastToModify = listWithSameNameInBeast.get(position);

        String input = confirmBeast("modify", beastToModify);

        if (input.equals("y")) {
            System.out.println("What would you like to modify?");
            System.out.println("\tb -> change beast name");
            System.out.println("\to -> change owner name");
            System.out.println("\tp -> add parents");
            System.out.println("\ts -> add siblings");
            System.out.println("\tg -> add offsprings");
            System.out.println("\te -> add extra notes");
            String selectOption = userInput.next();
            modifySelection(selectOption, beastToModify);
        } else {
            runRegistry();
        }
    }

    //MODIFIES: beast
    //EFFECTS: add parent to a beast
    private void addParents(MagicalBeast beast) {
        MagicalBeast beastOffspring = beast;
        MagicalBeast beastParent = askBeastToActOn("parent");

        if (beastOffspring == null || beastParent == null) {
            runRegistry();
            return;
        }

        beastOffspring.addParents(beastParent);
        beastParent.addOffsprings(beastOffspring);

        System.out.println(beastOffspring.getBeastName() + "'s parent "
                + beastParent.getBeastName() + " has been successfully added.");
        System.out.println(beastParent.getBeastName() + "'s offspring "
                + beastOffspring.getBeastName() + " has been successfully added.");
    }

    //MODIFIES: beast
    //EFFECTS: add sibling to a beast
    private void addSiblings(MagicalBeast beast) {
        MagicalBeast beastSiblingOne = beast;
        MagicalBeast beastSiblingTwo = askBeastToActOn("sibling");

        if (beastSiblingOne == null || beastSiblingTwo == null) {
            runRegistry();
            return;
        }

        beastSiblingOne.addSiblings(beastSiblingTwo);
        beastSiblingTwo.addSiblings(beastSiblingOne);

        System.out.println(beastSiblingOne.getBeastName() + "'s sibling "
                + beastSiblingTwo.getBeastName() + " has been successfully added.");
        System.out.println(beastSiblingTwo.getBeastName() + "'s sibling "
                + beastSiblingOne.getBeastName() + " has been successfully added.");
    }

    //MODIFIES: beast
    //EFFECTS: add offspring to a beast
    private void addOffsprings(MagicalBeast beast) {
        MagicalBeast beastParent = beast;
        MagicalBeast beastOffspring = askBeastToActOn("offspring");

        if (beastParent == null || beastOffspring == null) {
            runRegistry();
            return;
        }

        beastParent.addOffsprings(beastOffspring);
        beastOffspring.addParents(beastParent);

        System.out.println(beastParent.getBeastName() + "'s offspring "
                + beastOffspring.getBeastName() + " has been successfully added.");
        System.out.println(beastOffspring.getBeastName() + "'s parent "
                + beastParent.getBeastName() + " has been successfully added.");
    }

    //MODIFIES: beast
    //EFFECTS: add extra notes to a beast
    private void addExtraNotes(MagicalBeast beast) {
        userInput.nextLine();
        System.out.println("Please enter the notes you would like to add to " + beast.getBeastName() + ".");
        String notes = userInput.nextLine();
        beast.addExtraNotes(notes);
        System.out.println("[" + notes + "]" + " has been successfully added to " + beast.getBeastName());
    }

    //EFFECTS: return a list of name that are the same as beastName
    private List<String> getFilteredListStringByName(String beastName) {
        return fullRegistry.getFilteredMagicalBeastsByName(beastName);
    }

    //EFFECTS: return a list of MagicalBeast that have the same beastName
    private List<MagicalBeast> getFilteredListMagicalBeastByName(String beastName) {
        return fullRegistry.getFilteredMagicalBeastsByBeast(beastName);
    }

    //EFFECTS: return the magical beast that the user is looking to perform action on
    private MagicalBeast askBeastToActOn(String action) {
        System.out.println("What is the name of the " + action + " ?");
        String beastName = userInput.next();

        List<String> sameNameInString = getFilteredListStringByName(beastName);
        List<MagicalBeast> sameNameInBeast = getFilteredListMagicalBeastByName(beastName);

        int positionBeast = positionBeast(sameNameInString, "set as the " + action);
        MagicalBeast beast = sameNameInBeast.get(positionBeast);

        String confirm = confirmBeast("set as a " + action, beast);

        if (confirm.equals("y")) {
            return beast;
        } else {
            return null;
        }
    }

    //EFFECTS: display two functions: select or filter, to the user to select; otherwise go back to the main menu
    private void doDisplay() {
        System.out.println(fullRegistry.getAllMagicalBeastNames());
        System.out.println("\nSelect from:");
        System.out.println("\ts -> select Beast");
        System.out.println("\tf -> filter List");
        System.out.println("\tany other keys -> back to main menu");
        String input = userInput.next();
        if (input.equals("s")) {
            selectDisplayBeast();
        } else if (input.equals("f")) {
            filterBeast();
        } else {
            runRegistry();
        }
    }

    //EFFECTS: produce a filtered registry list by species or owner
    private void filterBeast() {
        System.out.println("Filter by:");
        System.out.println("\ts -> species");
        System.out.println("\to -> owner");
        System.out.println("\tany other keys -> main menu");
        String input = userInput.next();
        if (input.equals("s")) {
            List<String> filterBySpeciesName = getConvertFromMagicalBeastToString(filterBySpecies());
            System.out.println(filterBySpeciesName);

            System.out.println("Press any key to go back to the main menu.");
            String key = userInput.next();
            runRegistry();

        } else if (input.equals("o")) {
            List<String> filterByOwnerName = getConvertFromMagicalBeastToString(filterByOwner());
            System.out.println(filterByOwnerName);

            System.out.println("Press any key to go back to the main menu.");
            String key = userInput.next();
            runRegistry();

        } else {
            runRegistry();
        }
    }

    //EFFECTS: convert a list of Magical Beast to a list of Magical Beast's name
    public List<String> getConvertFromMagicalBeastToString(List<MagicalBeast> b) {
        List<String> listString = new ArrayList<>();

        for (MagicalBeast beast : b) {
            listString.add(beast.getBeastName());
        }
        return listString;
    }

    //EFFECTS: return a list of magical beast filtered by owner's name
    private List<MagicalBeast> filterByOwner() {
        System.out.println("What is the name of the owner you wish to filter by?");
        String input = userInput.next();
        return fullRegistry.getFilteredMagicalBeastsByOwner(input);
    }

    //EFFECTS: return a list of magical beast filtered by species' name
    private List<MagicalBeast> filterBySpecies() {
        List<MagicalBeast> filteredList = new ArrayList<>();
        String beastSpecies = "";
        boolean keepGoing = true;

        while (keepGoing) {
            System.out.println("What is the species you wish to filter by? Choose one of the following:"
                    + speciesList);
            beastSpecies = userInput.next();

            if (checkCorrectSpecies(beastSpecies)) {
                filteredList = fullRegistry.getFilteredMagicalBeastsBySpecies(beastSpecies);
                keepGoing = false;
            } else {
                System.out.println("Species entered does not exist, please enter again.");
            }
        }
        return filteredList;
    }

    //EFFECTS: select and display the full information of the selected beast
    private void selectDisplayBeast() {
        System.out.println("Which of the beasts do you wish to select?"
                + " Select by entering the position (starting from 0).");
        System.out.println(fullRegistry.getAllMagicalBeastNames());
        int input = userInput.nextInt();
        MagicalBeast selectedBeast = fullRegistry.getMagicalBeast(input);
        displayBeastFullInfo(selectedBeast);
        System.out.println("Press any key to go back to the main menu.");
        String key = userInput.next();
        runRegistry();
    }

    //EFFECTS: display the full information of the magical beast
    private void displayBeastFullInfo(MagicalBeast beast) {
        System.out.println("Unique ID: " + beast.getUniqueId());
        System.out.println("Name: " + beast.getBeastName());
        System.out.println("Gender: " + beast.getGender());
        System.out.println("Species: " + beast.getSpeciesName());
        System.out.println("Species warning: " + beast.getSpeciesSpecificWarning());
        System.out.println("Classification: " + beast.getClassificationInX());
        System.out.println("Owner's name:" + beast.getOwnerName());
        System.out.println("Parents: " + beast.getParents());
        System.out.println("Siblings:" + beast.getSiblings());
        System.out.println("Offsprings: " + beast.getOffsprings());
        System.out.println("Extra notes: " + beast.getExtraNotes());
    }

    //MODIFIES: beast
    //EFFECTS: based on user's input, change the beast name, owner,
    //         add parents, siblings, offsprings, extra notes of the magical beast
    private void modifySelection(String selectOption, MagicalBeast beast) {
        if (selectOption.equals("b")) {
            System.out.println("What is the name you wish to set?");
            String name = userInput.next();
            beast.setBeastName(name);

            System.out.println(name + " has been successfully changed.");
        } else if (selectOption.equals("o")) {
            System.out.println("What is the name you wish to set?");
            String name = userInput.next();
            beast.setOwnerName(name);

            System.out.println(name + " has been successfully changed.");
        } else if (selectOption.equals("p")) {
            addParents(beast);
        } else if (selectOption.equals("s")) {
            addSiblings(beast);
        } else if (selectOption.equals("g")) {
            addOffsprings(beast);
        } else if (selectOption.equals("e")) {
            addExtraNotes(beast);
        }
        System.out.println("Press any key to go back to the main menu.");
        String key = userInput.next();
        runRegistry();
    }

    // EFFECTS: saves the registry to file
    private void saveRegistry() {
        try {
            jsonWriter.open();;
            jsonWriter.write(fullRegistry);
            jsonWriter.close();
            System.out.println("Saved registry to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads registry from file
    private void loadRegistry() {
        try {
            fullRegistry = jsonReader.read();
            System.out.println("Loaded registry from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
