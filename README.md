# Harry Potter Dangerous Beasts Registry

## Department for the Regulation and Control of Magical Creatures - Beast Division

This application is a registry of dangerous beasts under the care of Wizards and Witches. 
The users of this application are employees of the Beast division at the Department for the Regulation and Control of 
Magical Creatures at the Ministry of Magic. I am a huge fan of the Harry Potter series, in particular the beasts
depicted in the story, which inspired me to create a project with a Harry Potter theme.

## User Stories
- As a user, I want to be able to create a new beast and add them to the beast registry with the following specifications.
    - a unique id
    - beast's name
    - species
    - species specific warning
    - classification
    - gender
    - owner's name
    - familial relation
    - extra notes (free text)
- As a user, I want to be able to view the list of beasts in the registry
- As a user, I want to be able to select and view a list of beasts of a specific species
- As a user, I want to be able to select and view a list of beasts under a specific owner
- As a user, I want to be able to select a beast in the list and modify or add new details to that beast
- As a user, I want to be able to save the registry to file (if I so choose)
- As a user, I want to be able to load the registry from file (if I so choose)

# Instructions for Grader
- You can generate the first required action related to the user story "creating a new beast and add them to the beast
registry" by clicking "Add Beast" button in the main menu, then enter the name of the beast, name of the owner, 
select its gender and its species. Once you are finished, click "Submit" button to add this new beast to the registry
- You can generate the second required action related to the user story "select and view a list of beasts of a specific 
species or owner" by clicking "Display & Modify All Beasts" in the main menu. On the left hand side, choose whether to 
filter by "Species" or "Owner" and type in the name of the species or owner, then click "Submit"
- You can generate the third required action related to the user story "view the list of beasts in the registry" by
clicking "Display & Modify All Beasts" in the main menu.
- You can generate the forth required action related to the user story "select a beast in the list and modify or add 
new details to that beast" by clicking "Display & Modify All Beasts" in the main menu. Select the beast that you
wish to modify and click on "Modify Beast Details". On the right-hand side, type in the new beast name, owner name, or 
extra notes if you wish. If you would like to add new parent, sibling, or offspring, select another beast to add to the
current beast and choose one of the three options in the dropdown menu.
- You can locate my visual component in the main menu by opening the application.
- You can save the state of my application by clicking on "Save" in the main menu
- You can reload the state of my application by clicking on "Load" in the main menu

# Phase 4: Task 2
````
- Sat Mar 30 13:55:05 PDT 2024
- Beast first added to the registry.
- Sat Mar 30 13:55:05 PDT 2024
- Beast second added to the registry.
- Sat Mar 30 13:55:05 PDT 2024
- Beast third added to the registry.
- Sat Mar 30 13:55:06 PDT 2024
- Displayed the list of beasts from registry.
- Sat Mar 30 13:55:06 PDT 2024
- Displayed the list of beasts from registry.
- Sat Mar 30 13:55:10 PDT 2024
- first's details has been displayed.
- Sat Mar 30 13:55:12 PDT 2024
- first's details has been displayed.
- Sat Mar 30 13:55:27 PDT 2024
- first's name has been changed to n1.
- Sat Mar 30 13:55:27 PDT 2024
- n1's owner has been changed to no1.
- Sat Mar 30 13:55:27 PDT 2024
- A new extra note has been added to n1.
- Sat Mar 30 13:55:27 PDT 2024
- A new sibling has been assigned to n1.
- Sat Mar 30 13:55:27 PDT 2024
- A new sibling has been assigned to third.
- Sat Mar 30 13:55:27 PDT 2024
- Displayed the list of beasts from registry.
- Sat Mar 30 13:55:27 PDT 2024
- Displayed the list of beasts from registry.
- Sat Mar 30 13:55:35 PDT 2024
- Beast second removed from the registry.
- Sat Mar 30 13:55:35 PDT 2024
- Displayed the list of beasts from registry.
- Sat Mar 30 13:55:35 PDT 2024
- Displayed the list of beasts from registry.
- Sat Mar 30 13:55:44 PDT 2024
- A list filtered by species, Flobberworm, is displayed.
- Sat Mar 30 13:55:53 PDT 2024
- A list filtered by owner's name, no1, is displayed.
````

# Phase 4: Task 3
- If I had more time to work on this project, I would refactor the Species class. Currently, the Species
class is hardcoded with five different species and their associated species warnings and classification in three 
separate arrayLists. For better usability in the future (such as user adding in new species), I think replacing the 
three arrayList with one HashSet and a new class for species warning and classification (key: species name, value: 
new class object that stores species warning and classification).
- Another refactoring would be adding two abstract classes "Animal" and "AnimalList" if we want to make this program 
more generalizable. For example, this application could also be used for pets registration. I am thinking of implementing general methods
that are applicable to any animal (such as name and gender) and have the MagicalBeast extends Animal, and MagicalBeastList
extends AnimalList.

[comment]:<> (An example of text with **bold** and *italic* fonts.)