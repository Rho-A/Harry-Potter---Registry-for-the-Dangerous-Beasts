package ui;

import model.MagicalBeast;
import model.MagicalBeastList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Magical Beast Registry application
public class RegistryApp {
    private MagicalBeastList fullRegistry = new MagicalBeastList();
    private Scanner userInput = new Scanner(System.in);

    ArrayList<String> speciesList = new ArrayList<>(Arrays.asList(
            "Flobberworm",
            "Ghoul",
            "Kneazle",
            "Griffin",
            "Quintaped")
    );

    public RegistryApp() {
        runRegistry();
    }

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

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add Beast");
        System.out.println("\tr -> remove Beast");
        System.out.println("\tm -> modify Beast details");
        System.out.println("\td -> display");
        System.out.println("\tq -> quit");
    }

    private void processUserCommand(String userCommand) {
        if (userCommand.equals("a")) {
            doAddBeast();
        } else if (userCommand.equals("r")) {
            doRemoveBeast();
        } else if (userCommand.equals("m")) {
            doModifyBeastDetails();
        } else if (userCommand.equals("d")) {
            doDisplay();
        } else {
            System.out.println("Selection not valid. Please retry.");
        }
    }

    private void doAddBeast() {
        System.out.println("What is the Beast's name?");
        String beastName = userInput.next();
        System.out.println("What is the Beast's gender?");
        String beastGender = userInput.next();

        boolean keepGoing = true;
        String beastSpecies = "";

        while (keepGoing) {
            System.out.println("What is the Beast's species? Choose one of the following:"
                    + speciesList);
            beastSpecies = userInput.next();

            if (correctSpecies(beastSpecies)) {
                keepGoing = false;
            } else {
                System.out.println("Species entered does not exist, please enter again.");
            }

        }

        System.out.println("What is the owner's name?");
        String beastOwner = userInput.next();

        addToRegistry(createBeast(beastName, beastGender, beastSpecies, beastOwner));

        System.out.println(beastName + " has been successfully added.");
    }

    private boolean correctSpecies(String species) {
        for (String name : speciesList) {
            if (species.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private MagicalBeast createBeast(String beastName, String beastGender, String beastSpecies, String beastOwner) {
        return new MagicalBeast(beastName, beastGender, beastSpecies, beastOwner);
    }

    private void addToRegistry(MagicalBeast beast) {
        fullRegistry.addMagicalBeast(beast);
    }

    private void doRemoveBeast() {
        String beastName = chooseBeast("remove");

        ArrayList<String> listWithSameNameInString = fullRegistry.getFilteredMagicalBeastsByName(beastName);
        ArrayList<MagicalBeast> listWithSameNameInBeast = fullRegistry.getFilteredMagicalBeastsByBeast(beastName);

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

    private String chooseBeast(String action) {
        System.out.println("What is the name of the beast you wish to " + action + "?");
        return userInput.next();
    }

    private int positionBeast(ArrayList<String> filteredList, String action) {
        System.out.println("Which of the following would you like to " + action + "?"
                + " Select by entering the position (starting from 0).");
        System.out.println(filteredList);
        return userInput.nextInt();
    }

    private String confirmBeast(String action, MagicalBeast beast) {
        displayBeastFullInfo(beast);
        System.out.println("Is this the beast you wish to " + action + "?"
                + "press y to confirm, press any other key to quit");

        return userInput.next();
    }

    private void doModifyBeastDetails() {
        String beastName = chooseBeast("modify");

        ArrayList<String> listWithSameNameInString = fullRegistry.getFilteredMagicalBeastsByName(beastName);
        ArrayList<MagicalBeast> listWithSameNameInBeast = fullRegistry.getFilteredMagicalBeastsByBeast(beastName);

        int position = positionBeast(listWithSameNameInString, "modify");
        MagicalBeast beastToModify = listWithSameNameInBeast.get(position);

        String input = confirmBeast("modify", beastToModify);

        if (input.equals("y")) {
            System.out.println("What would you like to modify?");
            System.out.println("\tb -> change beast name");
            System.out.println("\to -> change owner name");
            //System.out.println("\tp -> add parents");
            //System.out.println("\ts -> add siblings");
            //System.out.println("\tg -> add offsprings");
            //System.out.println("\te -> add extra notes");
            String selectOption = userInput.next();
            modifySelection(selectOption, beastToModify);
        } else {
            runRegistry();
        }
    }

    private void doDisplay() {
        System.out.println(fullRegistry.getAllMagicalBeastNames());
        System.out.println("\nSelect from:");
        System.out.println("\ts -> select Beast");
        System.out.println("\tf -> filter List");
        String input = userInput.next();
        if (input.equals("s")) {
            selectBeast();
        } else if (input.equals("f")) {
            filterBeast();
        } else {
            runRegistry();
        }
    }

    private void filterBeast() {
        System.out.println("Filter by:");
        System.out.println("\ts -> species");
        System.out.println("\to -> owner");
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


    private List<MagicalBeast> filterByOwner() {
        System.out.println("What is the name of the owner you wish to filter by?");
        String input = userInput.next();
        return fullRegistry.getFilteredMagicalBeastsByOwner(input);
    }


    private List<MagicalBeast> filterBySpecies() {
        String beastSpecies = "";
        boolean keepGoing = true;

        while (keepGoing) {
            System.out.println("What is the species you wish to filter by? Choose one of the following:"
                    + speciesList);
            beastSpecies = userInput.next();

            if (correctSpecies(beastSpecies)) {
                keepGoing = false;
                return fullRegistry.getFilteredMagicalBeastsBySpecies(beastSpecies);
            } else {
                System.out.println("Species entered does not exist, please enter again.");
            }
        }

        return null;

    }

    private void selectBeast() {
        System.out.println("Which of the beasts do you wish to select?"
                + " Select by entering the position (starting from 0).");
        int input = userInput.nextInt();
        MagicalBeast selectedBeast = fullRegistry.getMagicalBeast(input);
        displayBeastFullInfo(selectedBeast);
        System.out.println("Press any key to go back to the main menu.");
        String key = userInput.next();
        runRegistry();
    }

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
        }

        System.out.println("Press any key to go back to the main menu.");
        String key = userInput.next();
        runRegistry();
    }



}
