package ui;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Registry extends JFrame {
    private RegistryMainPanel registryMainPanel;

    // construct registry window
    // Effects: construct the window for registry
    public Registry() {
        super("Dangerous Beast Registry");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon logo =
                new ImageIcon("./data/logo.png");
        try {
            registryMainPanel = new RegistryMainPanel();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
        setIconImage(logo.getImage());

        add(registryMainPanel);

        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        new Registry();
    }
}
