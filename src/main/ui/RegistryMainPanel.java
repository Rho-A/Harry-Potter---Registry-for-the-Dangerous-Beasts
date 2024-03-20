package ui;

import model.MagicalBeast;
import model.MagicalBeastList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Citation: https://github.students.cs.ubc.ca/CPSC210/TellerApp
//           from CPSC 210 - TellerApp

// Magical Beast Registry application
public class RegistryMainPanel extends JPanel implements ActionListener {
    private static final String JSON_STORE = "./data/registry.json";
    private MagicalBeastList fullRegistry = new MagicalBeastList();
    private Scanner userInput = new Scanner(System.in);
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Color darkMode = new Color(0x292929);

    private String beastName;
    private String beastGender;
    private String beastSpecies;
    private String beastOwner;
    private String beastDetailText;

    private JPanel mainPanel;
    private JLabel selectLabel;

    private JPanel northPanel = new JPanel();
    private JLabel northImage = new JLabel();
    private JLabel northTitle = new JLabel();

    private JPanel addPanel;
    private JPanel displayPanel;

    private JScrollPane detailsPanel;

    private JPanel filterSidePanel;

    private JPanel beastDetailsPanel;
    private JPanel detailsButtonPanel;
    private JPanel backToMainPanel;
    private JPanel leftSidePanel;
    private JPanel rightSidePanel;

    private JButton addBeast;
    private JButton removeBeast;
    private JButton modifyBeast;
    private JButton display;
    private JButton save;
    private JButton load;
    private JButton quit;
    private JButton details;
    private JButton backToMain;

    private JTable table;

    private JTextField beastText;
    private JTextField ownerText;
    private JTextField text;
    private JRadioButton female;
    private JRadioButton male;
    private JRadioButton unknown;
    private JComboBox dropDown;

    private String[] header = {"Id", "Name", "Gender", "Owner", "Species", "Classification"};
    private String[][] data;

    private int rowSelected;

    private static final String title
            = "Department for the Regulation and Control of Magical Creatures: Dangerous Beast Registry";

    String[] speciesList = {
            "Flobberworm",
            "Ghoul",
            "Kneazle",
            "Griffin",
            "Quintaped"};

    private String[][] fullRegistryData() {
        List<MagicalBeast> fullList = fullRegistry.getAllMagicalBeasts();
        int registrySize = fullRegistry.getAllMagicalBeasts().size();
        data = new String[registrySize][];
        int i = 0;
        for (MagicalBeast b : fullList) {
            String[] beastDetails = new String[6];
            beastDetails[0] = b.getUniqueId();
            beastDetails[1] = b.getBeastName();
            beastDetails[2] = b.getGender();
            beastDetails[3] = b.getOwnerName();
            beastDetails[4] = b.getSpeciesName();
            beastDetails[5] = b.getClassificationInX();

            data[i] = beastDetails;
            i++;
        }
        return data;
    }

    private String[][] filterData(String option, String name) {
        List<MagicalBeast> filteredList;
        if (option.equals("Species")) {
            filteredList = fullRegistry.getFilteredMagicalBeastsBySpecies(name);
            System.out.println(filteredList);
        } else {
            filteredList = fullRegistry.getFilteredMagicalBeastsByOwner(name);
            System.out.println(filteredList);
        }
        int registrySize = filteredList.size();
        data = new String[registrySize][];
        int i = 0;
        for (MagicalBeast b : filteredList) {
            String[] beastDetails = new String[6];
            beastDetails[0] = b.getUniqueId();
            beastDetails[1] = b.getBeastName();
            beastDetails[2] = b.getGender();
            beastDetails[3] = b.getOwnerName();
            beastDetails[4] = b.getSpeciesName();
            beastDetails[5] = b.getClassificationInX();

            data[i] = beastDetails;
            i++;
        }
        return data;
    }

    private void navigateDetailScreen() {
        removeAll();
        setBackground(darkMode);
        setPreferredSize(new Dimension(1000,800));
        setLayout(new BorderLayout());

        createDetailsPanel();
        createFilterSidePanel();
        createDetailsButtonPanel();
        createBackToMainPanel();
        createLeftSidePanel();
        createRightSidePanel();


        JPanel blankN = new JPanel();
        blankN.setBackground(darkMode);
        blankN.setPreferredSize(new Dimension(80,80));
        JPanel blankW = new JPanel();
        blankW.setBackground(darkMode);
        blankW.setPreferredSize(new Dimension(80,40));
        JPanel blankS = new JPanel();
        blankS.setBackground(darkMode);
        blankS.setPreferredSize(new Dimension(80,80));

        add(blankN, BorderLayout.NORTH);
        add(leftSidePanel, BorderLayout.WEST);
        add(detailsButtonPanel, BorderLayout.SOUTH);
        add(detailsPanel, BorderLayout.CENTER);
        add(rightSidePanel, BorderLayout.EAST);


        revalidate();
        repaint();
    }

    private void createRightSidePanel() {
        rightSidePanel = new JPanel();
        rightSidePanel.setBackground(darkMode);
        rightSidePanel.setLayout(new BoxLayout(rightSidePanel, BoxLayout.Y_AXIS));
        JPanel blankE = new JPanel();
        blankE.setBackground(darkMode);
        blankE.setPreferredSize(new Dimension(250,200));
        rightSidePanel.add(blankE);

/*        if (rowSelected < 0) {
            rightSidePanel.remove(beastDetailsPanel);
            rightSidePanel.add(blankE);
        } else {
            createBeastDetailsPanel();
            rightSidePanel.remove(blankE);
            rightSidePanel.add(beastDetailsPanel);
        }*/
        revalidate();
        repaint();
    }

    private void navigateFilteredDetailScreen() {
        removeAll();
        setBackground(darkMode);
        setPreferredSize(new Dimension(1000,800));
        setLayout(new BorderLayout());

        createFilteredDetailsPanel();
        createFilterSidePanel();
        createBackToMainPanel();
        createLeftSidePanel();

        JPanel blankN = new JPanel();
        blankN.setBackground(darkMode);
        blankN.setPreferredSize(new Dimension(80,80));
        JPanel blankW = new JPanel();
        blankW.setBackground(darkMode);
        blankW.setPreferredSize(new Dimension(80,40));
        JPanel blankS = new JPanel();
        blankS.setBackground(darkMode);
        blankS.setPreferredSize(new Dimension(80,80));
        JPanel blankE = new JPanel();
        blankE.setBackground(darkMode);
        blankE.setPreferredSize(new Dimension(80,40));

        add(blankN, BorderLayout.NORTH);
        add(leftSidePanel, BorderLayout.WEST);
        add(blankS, BorderLayout.SOUTH);
        add(detailsPanel, BorderLayout.CENTER);
        add(blankE, BorderLayout.EAST);
        revalidate();
        repaint();
    }

    public RegistryMainPanel() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        navigateMainScreen();
    }

    public void createLeftSidePanel() {
        leftSidePanel = new JPanel();
        leftSidePanel.setBackground(darkMode);
        leftSidePanel.setLayout(new BoxLayout(leftSidePanel, BoxLayout.Y_AXIS));
        backToMainPanel.setPreferredSize(new Dimension(250, 50));
        filterSidePanel.setPreferredSize(new Dimension(250, 500));
        leftSidePanel.add(backToMainPanel);
        leftSidePanel.add(filterSidePanel);
    }

    public void createFilterSidePanel() {
        filterSidePanel = new JPanel();
        filterSidePanel.setBackground(darkMode);
        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setForeground(Color.white);
        dropDown = new JComboBox(new String[] {"Species", "Owner"});
        text = new JTextField();
        text.setPreferredSize(new Dimension(100,24));
        text.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        JButton submit = new JButton("Submit");
        filterSidePanel.add(filterLabel);
        filterSidePanel.add(dropDown);
        filterSidePanel.add(text);
        filterSidePanel.add(submit);
        submit.addActionListener(this::submitFilter);
    }

    public void submitFilter(ActionEvent e) {
        String option = dropDown.getSelectedItem().toString();
        String filterName = text.getText();

        filterData(option, filterName);
        navigateFilteredDetailScreen();
    }

    private void createDetailsPanel() {
        fullRegistryData();
        table = new JTable(data, header);
        table.setMaximumSize(new Dimension(50, 50));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        detailsPanel = new JScrollPane(table);
        detailsPanel.setBackground(darkMode);
        detailsPanel.getVerticalScrollBar();
        detailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Registry Details",
                TitledBorder.CENTER, TitledBorder.TOP, new Font(Font.SERIF, Font.BOLD, 24), Color.white));
    }

    private void createBackToMainPanel() {
        backToMainPanel = new JPanel();
        backToMainPanel.setBackground(darkMode);
        backToMain = new JButton("Go back to main menu");
        backToMain.addActionListener(e -> navigateMainScreen());
        backToMainPanel.add(backToMain);
    }

    private void createDetailsButtonPanel() {
        detailsButtonPanel = new JPanel();
        detailsButtonPanel.setBackground(darkMode);
        removeBeast = new JButton("Remove Beast");
        modifyBeast = new JButton("Modify Beast Details");
        details = new JButton("See details");

        removeBeast.addActionListener(e -> removeBeastAction());
        modifyBeast.addActionListener(e -> doModifyBeastDetails());
        details.addActionListener(e -> addBeastDetailPanelToRHS());
        detailsButtonPanel.add(removeBeast);
        detailsButtonPanel.add(modifyBeast);
        detailsButtonPanel.add(details);
    }

    private void addBeastDetailPanelToRHS() {
        rightSidePanel.removeAll();
        rightSidePanel.setLayout(new BoxLayout(rightSidePanel, BoxLayout.Y_AXIS));
        rightSidePanel.setBackground(darkMode);
        createBeastDetailsPanel();
        beastDetailsPanel.setPreferredSize(new Dimension(250,300));
        rightSidePanel.add(beastDetailsPanel);

        revalidate();
        repaint();
    }

    private void removeBeastAction() {
        rowSelected = table.getSelectedRow();
        MagicalBeast b = fullRegistry.getMagicalBeast(rowSelected);
        fullRegistry.removeMagicalBeast(b);
        navigateDetailScreen();
    }

    private void createBeastDetailsPanel() {
        beastDetailsPanel = new JPanel();
        beastDetailsPanel.setLayout(new BoxLayout(beastDetailsPanel, BoxLayout.PAGE_AXIS));
        String[] labels = {"Unique ID: ", "Name: ", "Gender: ", "Species: ", "Species warning: ", "Classification: ",
                "Owner: ", "Parents: ", "Siblings: ", "Offsprings: ", "Extra notes: "};
        rowSelected = table.getSelectedRow();
        System.out.println(rowSelected);

        MagicalBeast b = fullRegistry.getMagicalBeast(rowSelected);
        JLabel detailsText;
        JLabel arrayDetailText;
        for (String i : labels) {
            if (i.equals("Parents: ") | i.equals("Siblings: ") | i.equals("Offsprings: ") | i.equals("Extra notes: ")) {
                List<String> arrayText =  beastArrayDetail(i, b);
                for (String s : arrayText) {
                    arrayDetailText = new JLabel(i + s);
                    beastDetailsPanel.add(arrayDetailText);
                }
            } else {
                beastDetailText = beastDetail(i, b);
                detailsText = new JLabel(i + beastDetailText);
                beastDetailsPanel.add(detailsText);
            }
        }
    }

    private List<String> beastArrayDetail(String option, MagicalBeast beast) {
        if (option.equals("Parents: ")) {
            return beast.getParents();
        } else if (option.equals("Siblings: ")) {
            return beast.getSiblings();
        } else if (option.equals("Offsprings: ")) {
            return beast.getOffsprings();
        } else {
            return beast.getExtraNotes();
        }
    }


    private String beastDetail(String option, MagicalBeast beast) {
        if (option.equals("Unique ID: ")) {
            return beast.getUniqueId();
        } else if (option.equals("Name: ")) {
            return beast.getBeastName();
        } else if (option.equals("Species: ")) {
            return beast.getSpeciesName();
        } else if (option.equals("Species warning: ")) {
            return beast.getSpeciesSpecificWarning();
        } else if (option.equals("Classification: ")) {
            return beast.getClassificationInX();
        } else {
            return beast.getOwnerName();
        }
    }

    private void createFilteredDetailsPanel() {
        JTable table = new JTable(data, header);
        table.setMaximumSize(new Dimension(50, 50));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        detailsPanel = new JScrollPane(table);
        detailsPanel.setBackground(darkMode);
        detailsPanel.getVerticalScrollBar();
        detailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Registry Details",
                TitledBorder.CENTER, TitledBorder.TOP, new Font(Font.SERIF, Font.BOLD, 24), Color.white));
    }

    private void navigateMainScreen() {
        removeAll();
        setBackground(darkMode);
        setPreferredSize(new Dimension(1000,800));
        setLayout(new BorderLayout(0,50));
        createNorthPanel();
        createMainPanel();
        add(northPanel, BorderLayout.PAGE_START);
        add(mainPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void createNorthPanel() {
        northTitle.setText(title);
        northTitle.setBackground(darkMode);
        northTitle.setForeground(Color.white);
        northTitle.setFont(new Font(Font.SERIF, Font.ITALIC | Font.BOLD, 24));
        northTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        northImage.setIcon(new ImageIcon("./data/white-logo.png"));
        northImage.setBackground(darkMode);
        northImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
        northPanel.setBackground(darkMode);
        northPanel.add(northImage);
        northPanel.add(northTitle);
    }

    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(darkMode);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        selectLabel = new JLabel("Select one of the following options:");
        selectLabel.setForeground(Color.white);
        selectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        mainPanel.add(selectLabel);
        createMainButtons();
        mainPanel.add(addBeast);
        mainPanel.add(Box.createRigidArea(new Dimension(5,5)));
        mainPanel.add(removeBeast);
        mainPanel.add(Box.createRigidArea(new Dimension(5,5)));
        mainPanel.add(modifyBeast);
        mainPanel.add(Box.createRigidArea(new Dimension(5,5)));
        mainPanel.add(display);
        mainPanel.add(Box.createRigidArea(new Dimension(5,5)));
        mainPanel.add(save);
        mainPanel.add(Box.createRigidArea(new Dimension(5,5)));
        mainPanel.add(load);
        mainPanel.add(Box.createRigidArea(new Dimension(5,5)));
        mainPanel.add(quit);
    }

    private void createMainButtons() {
        addBeast = new JButton("Add Beast");
        removeBeast = new JButton("Remove Beast");
        modifyBeast = new JButton("Modify Beast Details");
        display = new JButton("Display All Beasts");
        save = new JButton("Save");
        load = new JButton("Load");
        quit = new JButton("Quit");
        //buttonCustomization();
        addBeast.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeBeast.setAlignmentX(Component.CENTER_ALIGNMENT);
        modifyBeast.setAlignmentX(Component.CENTER_ALIGNMENT);
        display.setAlignmentX(Component.CENTER_ALIGNMENT);
        save.setAlignmentX(Component.CENTER_ALIGNMENT);
        load.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBeast.addActionListener(e -> createAddPanel());
        removeBeast.addActionListener(e -> doRemoveBeast());
        modifyBeast.addActionListener(e -> doModifyBeastDetails());
        display.addActionListener(e -> navigateDetailScreen());
        save.addActionListener(e -> saveRegistry());
        load.addActionListener(e -> loadRegistry());
        quit.addActionListener(e -> System.exit(0));
    }


    //MODIFIES: fullRegistry
    //EFFECTS: add new magical beast to the current registry
    private void createAddPanel() {
        addPanel = new JPanel();
        addPanel.setBackground(darkMode);
        add(addPanel, BorderLayout.CENTER);

        JLabel beastN = new JLabel("Beast Name:");
        beastN.setForeground(Color.white);
        beastText = new JTextField();
        beastText.setPreferredSize(new Dimension(100,24));
        beastText.setFont(new Font(Font.SERIF, Font.PLAIN, 16));

        JLabel ownerN = new JLabel("Owner Name:");
        ownerN.setForeground(Color.white);
        ownerText = new JTextField();
        ownerText.setPreferredSize(new Dimension(100,24));
        ownerText.setFont(new Font(Font.SERIF, Font.PLAIN, 16));

        JLabel gender = new JLabel("Gender:");
        gender.setForeground(Color.white);
        gender.setBackground(darkMode);

        female = new JRadioButton("Female");
        female.setForeground(Color.white);
        female.setBackground(darkMode);
        female.addActionListener(this);

        male = new JRadioButton("Male");
        male.setForeground(Color.white);
        male.setBackground(darkMode);
        male.addActionListener(this);

        unknown = new JRadioButton("Unknown");
        unknown.setForeground(Color.white);
        unknown.setBackground(darkMode);
        unknown.addActionListener(this);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(female);
        genderGroup.add(male);
        genderGroup.add(unknown);

        JLabel species = new JLabel("Species:");
        species.setForeground(Color.white);
        dropDown = new JComboBox(speciesList);

        JButton submit = new JButton("Submit");

        addPanel.add(beastN);
        addPanel.add(beastText);
        addPanel.add(ownerN);
        addPanel.add(ownerText);
        addPanel.add(gender);
        addPanel.add(female);
        addPanel.add(male);
        addPanel.add(unknown);
        addPanel.add(species);
        addPanel.add(dropDown);
        addPanel.add(submit);

        remove(mainPanel);
        add(addPanel, BorderLayout.CENTER);
        repaint();
        revalidate();

        submit.addActionListener(this::submitAddBeast);
    }

    //MODIFIES: fullRegistry
    //EFFECTS: add new magical beast to the current registry and return to mainPanel
    private void submitAddBeast(ActionEvent e) {
        beastName = beastText.getText();
        beastOwner = ownerText.getText();
        if (female.isSelected()) {
            beastGender = "Female";
        } else if (male.isSelected()) {
            beastGender = "Male";
        } else {
            beastGender = "Unknown";
        }
        beastSpecies = dropDown.getSelectedItem().toString();

        addToRegistry(createBeast(beastName, beastGender, beastSpecies, beastOwner));
        remove(addPanel);
        add(mainPanel, BorderLayout.CENTER);
        repaint();
        revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == removeBeast) {
            doRemoveBeast();
        } else if (e.getSource() == modifyBeast) {
            doModifyBeastDetails();
        } else if (e.getSource() == display) {
            doDisplay();
        } else if (e.getSource() == save) {
            saveRegistry();
        } else if (e.getSource() == load) {
            loadRegistry();
        } else if (e.getSource() == quit) {
            System.exit(0);
        }
    }

    //MODIFIES: fullRegistry
    //EFFECTS: add new magical beast to the current registry
    private void doAddBeast() {
        addToRegistry(createBeast(beastName, beastGender, beastSpecies, beastOwner));
        remove(addPanel);
        add(mainPanel, BorderLayout.CENTER);
        revalidate();
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
