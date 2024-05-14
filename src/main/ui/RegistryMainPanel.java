package ui;

import model.EventLog;
import model.Event;
import model.MagicalBeast;
import model.MagicalBeastList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

// Citation: Department of Magical Beast Logo from Harry Potter Wiki:
//           https://harrypotter.fandom.com/wiki/Department_for_the_Regulation_and_Control_of_Magical_Creatures?
//           file=Department_for_the_Regulation_and_Control_of_Magical_Creatures_logo.png
//
//           Java GUI: Full Course by Bro Code - https://www.youtube.com/watch?v=Kmgo00avvEw

// Magical Beast Registry application
public class RegistryMainPanel extends JPanel {
    private static final String JSON_STORE = "./data/registry.json";
    private MagicalBeastList fullRegistry = new MagicalBeastList();
    private List<MagicalBeast> filteredList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private final Color darkMode = new Color(0x292929);

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

    private JLabel species;
    private JLabel gender;
    private JLabel ownerN;
    private JLabel beastN;
    private JPanel addPanel;
    private JLabel beastLabel;
    private JLabel ownerLabel;
    private JLabel extraNotes;
    private JLabel addLabel;

    private JScrollPane detailsPanel;

    private JPanel filterSidePanel;

    private JPanel beastDetailsPanel;
    private JPanel detailsButtonPanel;
    private JPanel backToMainPanel;
    private JPanel leftSidePanel;
    private JPanel rightSidePanel;
    private JPanel modifyPanel;

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
    private ButtonGroup genderGroup;
    private JRadioButton female;
    private JRadioButton male;
    private JRadioButton unknown;
    private JComboBox dropDown;

    private final String[] header = {"Id", "Name", "Gender", "Owner", "Species", "Classification"};
    private String[][] data;
    private String[][] filteredData;

    private MagicalBeast firstSelectedBeast;
    private int rowSelected;

    private static final String title
            = "Department for the Regulation and Control of Magical Creatures: Dangerous Beast Registry";

    String[] speciesList = {
            "Flobberworm",
            "Ghoul",
            "Kneazle",
            "Griffin",
            "Quintaped",
            "Niffler"};

    //EFFECTS: create and open a running registry beginning at main menu
    public RegistryMainPanel() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        navigateMainScreen();
    }

    //EFFECTS: navigate to the main screen with 2 panels: northPanel and mainPanel
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

    //EFFECTS: create the main panel which provides navigation to addPanel, displayPanel, and save/load/quit functions
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
        mainPanel.add(display);
        mainPanel.add(Box.createRigidArea(new Dimension(5,5)));
        mainPanel.add(save);
        mainPanel.add(Box.createRigidArea(new Dimension(5,5)));
        mainPanel.add(load);
        mainPanel.add(Box.createRigidArea(new Dimension(5,5)));
        mainPanel.add(quit);
    }

    //EFFECTS: create buttons for main panel
    private void createMainButtons() {
        addBeast = new JButton("Add Beast");
        display = new JButton("Display & Modify All Beasts");
        save = new JButton("Save");
        load = new JButton("Load");
        quit = new JButton("Quit");
        addBeast.setAlignmentX(Component.CENTER_ALIGNMENT);
        display.setAlignmentX(Component.CENTER_ALIGNMENT);
        save.setAlignmentX(Component.CENTER_ALIGNMENT);
        load.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBeast.addActionListener(e -> createAddPanel());
        display.addActionListener(e -> navigateDetailScreen());
        save.addActionListener(e -> saveRegistry());
        load.addActionListener(e -> loadRegistry());
        quit.addActionListener(e -> quitApplication());
    }

    private void quitApplication() {
        for (Iterator<Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }
        System.exit(0);
    }

    //EFFECTS: navigate to detail screen displaying all beasts in the registry
    private void navigateDetailScreen() {
        removeAll();
        setBackground(darkMode);
        setPreferredSize(new Dimension(1000,800));
        setLayout(new BorderLayout());

        createDetailsPanel();
        createFilterSidePanel();
        createDetailsButtonPanel(false);
        createBackToMainPanel();
        createLeftSidePanel();
        createRightSidePanel();

        JPanel blankN = new JPanel();
        blankN.setBackground(darkMode);
        blankN.setPreferredSize(new Dimension(80,80));

        add(blankN, BorderLayout.NORTH);
        add(leftSidePanel, BorderLayout.WEST);
        add(detailsButtonPanel, BorderLayout.SOUTH);
        add(detailsPanel, BorderLayout.CENTER);
        add(rightSidePanel, BorderLayout.EAST);

        revalidate();
        repaint();
    }

    //EFFECTS: navigate to detail screen displaying select beasts in the filtered registry
    private void navigateFilteredDetailScreen() {
        removeAll();
        setBackground(darkMode);
        setPreferredSize(new Dimension(1000,800));
        setLayout(new BorderLayout());

        createFilteredDetailsPanel();
        createFilterSidePanel();
        createBackToMainPanel();
        createLeftSidePanel();

        createDetailsButtonPanel(true);
        createRightSidePanel();

        JPanel blankN = new JPanel();
        blankN.setBackground(darkMode);
        blankN.setPreferredSize(new Dimension(80,80));

        add(blankN, BorderLayout.NORTH);
        add(leftSidePanel, BorderLayout.WEST);
        add(detailsButtonPanel, BorderLayout.SOUTH);
        add(detailsPanel, BorderLayout.CENTER);
        add(rightSidePanel, BorderLayout.EAST);

        revalidate();
        repaint();
    }


    //EFFECTS: create a default(blank) rightSidePanel of the detail screen
    private void createRightSidePanel() {
        rightSidePanel = new JPanel();
        rightSidePanel.setBackground(darkMode);
        rightSidePanel.setLayout(new BoxLayout(rightSidePanel, BoxLayout.Y_AXIS));
        JPanel blankE = new JPanel();
        blankE.setBackground(darkMode);
        blankE.setPreferredSize(new Dimension(250,200));
        rightSidePanel.add(blankE);

        revalidate();
        repaint();
    }

    //EFFECTS: create a leftSidePanel consists of backToMainPanel and filterSidePanel
    public void createLeftSidePanel() {
        leftSidePanel = new JPanel();
        leftSidePanel.setBackground(darkMode);
        leftSidePanel.setLayout(new BoxLayout(leftSidePanel, BoxLayout.Y_AXIS));
        backToMainPanel.setPreferredSize(new Dimension(250, 50));
        filterSidePanel.setPreferredSize(new Dimension(250, 500));
        leftSidePanel.add(backToMainPanel);
        leftSidePanel.add(filterSidePanel);
    }

    //EFFECTS: create a filterSidePanel that provides user filtering options by species or owner
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

    //EFFECTS: produce a filtered registry list and navigate to the filtered detail screen
    public void submitFilter(ActionEvent e) {
        String option = dropDown.getSelectedItem().toString();
        String filterName = text.getText();

        filteredData = filterData(option, filterName);
        navigateFilteredDetailScreen();
    }

    //EFFECTS: produce an array of magical beasts, each element in the array is an array of beast's details
    private String[][] fullRegistryData() {
        List<MagicalBeast> fullList = fullRegistry.getAllMagicalBeasts();
        int registrySize = fullList.size();
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

    //EFFECTS: produce an array of filtered magical beasts based on option and name
    private String[][] filterData(String option, String name) {
        if (option.equals("Species")) {
            filteredList = fullRegistry.getFilteredMagicalBeastsBySpecies(name);
        } else {
            filteredList = fullRegistry.getFilteredMagicalBeastsByOwner(name);
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

    //EFFECTS: create a details panel containing a table of all the beasts in the registry
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

    //EFFECTS: create a backToMainPanel with a function to go back to the main menu
    private void createBackToMainPanel() {
        backToMainPanel = new JPanel();
        backToMainPanel.setBackground(darkMode);
        backToMain = new JButton("Go back to main menu");
        backToMain.addActionListener(e -> navigateMainScreen());
        backToMainPanel.add(backToMain);
    }

    //EFFECTS: create a panel with 3 buttons in the details panel: remove, modify, details
    private void createDetailsButtonPanel(Boolean filter) {
        detailsButtonPanel = new JPanel();
        detailsButtonPanel.setBackground(darkMode);
        removeBeast = new JButton("Remove Beast");
        modifyBeast = new JButton("Modify Beast Details");
        details = new JButton("See details");

        removeBeast.addActionListener(e -> removeBeastAction(filter));
        modifyBeast.addActionListener(e -> addModifyPanelToRHS(filter));
        details.addActionListener(e -> addBeastDetailPanelToRHS(filter));
        detailsButtonPanel.add(removeBeast);
        detailsButtonPanel.add(modifyBeast);
        detailsButtonPanel.add(details);
    }

    //MODIFIES: rightSidePanel
    //EFFECTS: add modifyPanel to rightSidePanel
    private void addModifyPanelToRHS(Boolean filter) {
        addBeastDetailPanelToRHS(filter);
        if (filter) {
            createModifyPanel(true);
        } else {
            createModifyPanel(false);
        }
        modifyPanel.setPreferredSize(new Dimension(300,300));
        rightSidePanel.add(modifyPanel);

        revalidate();
        repaint();
    }

    //EFFECTS: create a modifyPanel for modifying beast details
    //              true if this is acting on a filtered list of beasts, else false
    private void createModifyPanel(Boolean filter) {
        modifyPanel = new JPanel();
        modifyPanel.setBackground(darkMode);

        createModifyPanelSetUp();

        addLabel = new JLabel("Add: ");
        addLabel.setForeground(Color.WHITE);
        String[] selectList = {"", "parents", "siblings", "offsprings"};
        dropDown = new JComboBox(selectList);
        JButton submitModifyButton = new JButton("Submit");

        modifyPanel.add(beastLabel);
        modifyPanel.add(beastText);
        modifyPanel.add(ownerLabel);
        modifyPanel.add(ownerText);
        modifyPanel.add(extraNotes);
        modifyPanel.add(text);
        modifyPanel.add(addLabel);
        modifyPanel.add(dropDown);
        modifyPanel.add(submitModifyButton);

        if (filter) {
            submitModifyButton.addActionListener(e -> submitModifyOnFilteredData());
        } else {
            submitModifyButton.addActionListener(e -> submitModify());
        }
    }

    //EFFECTS: setup for creating modify panel
    private void createModifyPanelSetUp() {
        beastLabel = new JLabel("Change Beast's Name: ");
        beastLabel.setForeground(Color.white);
        beastText = new JTextField();
        beastText.setPreferredSize(new Dimension(100,24));
        beastText.setFont(new Font(Font.SERIF, Font.PLAIN, 16));

        ownerLabel = new JLabel("Change Owner: ");
        ownerLabel.setForeground(Color.white);
        ownerText = new JTextField();
        ownerText.setPreferredSize(new Dimension(100,24));
        ownerText.setFont(new Font(Font.SERIF, Font.PLAIN, 16));

        extraNotes = new JLabel("Add extra notes: ");
        extraNotes.setForeground(Color.white);
        text = new JTextField();
        text.setPreferredSize(new Dimension(150,24));
        ownerText.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
    }

    //MODIFIES: MagicalBeast
    //EFFECTS: modify the details of a selected MagicalBeast, then navigate back to detail screen
    private void submitModify() {
        MagicalBeast b = fullRegistry.getMagicalBeast(table.getSelectedRow());
        String option = dropDown.getSelectedItem().toString();
        if (!beastText.getText().equals("")) {
            firstSelectedBeast.setBeastName(beastText.getText());
        }
        if (!ownerText.getText().equals("")) {
            firstSelectedBeast.setOwnerName(ownerText.getText());
        }
        if (!text.getText().equals("")) {
            firstSelectedBeast.addExtraNotes(text.getText());
        }
        if (option.equals("parents")) {
            firstSelectedBeast.addParents(b);
            b.addOffsprings(firstSelectedBeast);
        } else if (option.equals("siblings")) {
            firstSelectedBeast.addSiblings(b);
            b.addSiblings(firstSelectedBeast);
        } else if (option.equals("offsprings")) {
            firstSelectedBeast.addOffsprings(b);
            b.addParents(firstSelectedBeast);
        }
        navigateDetailScreen();
    }

    //MODIFIES: MagicalBeast
    //EFFECTS: modify the details of a selected MagicalBeast with filtered list, then navigate back to detail screen
    private void submitModifyOnFilteredData() {
        MagicalBeast b = filteredList.get(table.getSelectedRow());
        String option = dropDown.getSelectedItem().toString();
        if (!beastText.getText().equals("")) {
            firstSelectedBeast.setBeastName(beastText.getText());
        }
        if (!ownerText.getText().equals("")) {
            firstSelectedBeast.setOwnerName(ownerText.getText());
        }
        if (!text.getText().equals("")) {
            firstSelectedBeast.addExtraNotes(text.getText());
        }
        if (option.equals("parents")) {
            firstSelectedBeast.addParents(b);
            b.addOffsprings(firstSelectedBeast);
        } else if (option.equals("siblings")) {
            firstSelectedBeast.addSiblings(b);
            b.addSiblings(firstSelectedBeast);
        } else if (option.equals("offsprings")) {
            firstSelectedBeast.addOffsprings(b);
            b.addParents(firstSelectedBeast);
        }
        navigateDetailScreen();
    }

    //EFFECTS: create a filtered detailsPanel
    private void createFilteredDetailsPanel() {
        table = new JTable(data, header);
        table.setMaximumSize(new Dimension(50, 50));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        detailsPanel = new JScrollPane(table);
        detailsPanel.setBackground(darkMode);
        detailsPanel.getVerticalScrollBar();
        detailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Registry Details",
                TitledBorder.CENTER, TitledBorder.TOP, new Font(Font.SERIF, Font.BOLD, 24), Color.white));
    }

    //MODIFIES: rightSidePanel
    //EFFECTS: add beastDetailsPanel to rightSidePanel
    private void addBeastDetailPanelToRHS(Boolean filter) {
        rightSidePanel.removeAll();
        rightSidePanel.setLayout(new BoxLayout(rightSidePanel, BoxLayout.Y_AXIS));
        rightSidePanel.setPreferredSize(new Dimension(300, 300));
        rightSidePanel.setBackground(darkMode);
        createBeastDetailsPanel(filter);
        beastDetailsPanel.setPreferredSize(new Dimension(300,300));
        rightSidePanel.add(beastDetailsPanel);

        revalidate();
        repaint();
    }

    //MODIFIES: fullRegistry
    //EFFECTS: remove beast from registry
    private void removeBeastAction(Boolean filter) {
        rowSelected = table.getSelectedRow();
        MagicalBeast b;
        if (filter) {
            b = filteredList.get(rowSelected);
        } else {
            b = fullRegistry.getMagicalBeast(rowSelected);
        }
        fullRegistry.removeMagicalBeast(b);
        navigateDetailScreen();
    }

    //EFFECTS: create beast details panel
    private void createBeastDetailsPanel(Boolean filter) {
        beastDetailsPanel = new JPanel();
        beastDetailsPanel.setLayout(new BoxLayout(beastDetailsPanel, BoxLayout.PAGE_AXIS));
        String[] labels = {"Unique ID: ", "Name: ", "Gender: ", "Species: ", "Species warning: ", "Classification: ",
                "Owner: ", "Parents: ", "Siblings: ", "Offsprings: ", "Extra notes: "};
        rowSelected = table.getSelectedRow();
        if (filter) {
            firstSelectedBeast = filteredList.get(rowSelected);
        } else {
            firstSelectedBeast = fullRegistry.getMagicalBeast(rowSelected);
        }

        for (String i : labels) {
            if (i.equals("Parents: ") | i.equals("Siblings: ") | i.equals("Offsprings: ") | i.equals("Extra notes: ")) {
                List<String> arrayText =  beastArrayDetail(i, firstSelectedBeast);
                for (String s : arrayText) {
                    JLabel arrayDetailText = new JLabel(i + s);
                    beastDetailsPanel.add(arrayDetailText);
                }
            } else {
                beastDetailText = beastDetail(i, firstSelectedBeast);
                JLabel detailsText = new JLabel(i + beastDetailText);
                beastDetailsPanel.add(detailsText);
            }
        }
    }

    //EFFECTS: return a list of parents, siblings, or offsprings of beast
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

    //EFFECTS: return beast's id, name, species, warning, classification, or owner's name
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

    //EFFECTS: create northPanel for mainScreen and addScreen
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

    //MODIFIES: fullRegistry
    //EFFECTS: add new magical beast to the current registry
    private void createAddPanel() {
        addPanel = new JPanel();
        addPanel.setBackground(darkMode);
        add(addPanel, BorderLayout.CENTER);

        createAddPanelSetUp();

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
        createBackToMainPanel();

        remove(mainPanel);
        add(addPanel, BorderLayout.CENTER);
        add(backToMainPanel, BorderLayout.SOUTH);
        repaint();
        revalidate();

        submit.addActionListener(this::submitAddBeast);
    }

    //EFFECTS: setup for addPanel, including labels for beast name, owner, gender, species
    private void createAddPanelSetUp() {
        beastN = new JLabel("Beast Name:");
        beastN.setForeground(Color.white);
        beastText = new JTextField();
        beastText.setPreferredSize(new Dimension(100,24));
        beastText.setFont(new Font(Font.SERIF, Font.PLAIN, 16));

        ownerN = new JLabel("Owner Name:");
        ownerN.setForeground(Color.white);
        ownerText = new JTextField();
        ownerText.setPreferredSize(new Dimension(100,24));
        ownerText.setFont(new Font(Font.SERIF, Font.PLAIN, 16));

        gender = new JLabel("Gender:");
        gender.setForeground(Color.white);
        gender.setBackground(darkMode);
        createGenderRadioButton();
        genderGroup = new ButtonGroup();
        genderGroup.add(female);
        genderGroup.add(male);
        genderGroup.add(unknown);

        species = new JLabel("Species:");
        species.setForeground(Color.white);
        dropDown = new JComboBox(speciesList);
    }

    //EFFECTS: create radio buttons for female, male, and unknown
    private void createGenderRadioButton() {
        female = new JRadioButton("Female");
        female.setForeground(Color.white);
        female.setBackground(darkMode);

        male = new JRadioButton("Male");
        male.setForeground(Color.white);
        male.setBackground(darkMode);

        unknown = new JRadioButton("Unknown");
        unknown.setForeground(Color.white);
        unknown.setBackground(darkMode);
    }

    //MODIFIES: fullRegistry
    //EFFECTS: add new magical beast to the current registry and return to mainScreen
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

        doAddBeast();
        navigateMainScreen();
    }

    //MODIFIES: fullRegistry
    //EFFECTS: add new magical beast to the current registry
    private void doAddBeast() {
        addToRegistry(createBeast(beastName, beastGender, beastSpecies, beastOwner));
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


    // EFFECTS: saves the registry to file
    private void saveRegistry() {
        try {
            jsonWriter.open();
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
