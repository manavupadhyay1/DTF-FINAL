package src;

import javax.swing.*;

/**
 * Entry point. Run this to start the Din Tai Fung Cooking Game.
 *
 * INSTRUCTIONS:
 * 1. Place your ingredient/dish images in the assets/ folders
 * 2. Update paths in GameData.java if needed
 * 3. Compile: javac src/*.java
 * 4. Run: java src.Main
 */
public class Main {
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new GameEngine());
    }
}
