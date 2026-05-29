package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Main game controller: manages screens, player, dishes.
 */
public class GameEngine {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private PlayerProfile player;
    private List<Dish> dishes;

    public GameEngine() {
        dishes = GameData.createDishes();

        frame = new JFrame("Din Tai Fung - Cooking Adventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add screens
        mainPanel.add(new LandingPanel(this), "landing");
        mainPanel.add(new NameEntryPanel(this), "name");

        frame.setContentPane(mainPanel);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        showScreen("landing");
    }

    public void showScreen(String name) {
        if (name.equals("menu")) {
            // Rebuild menu each time (to reflect unlocks)
            mainPanel.add(new MenuPanel(this, dishes), "menu");
        }
        cardLayout.show(mainPanel, name);
    }

    public void showDishDetail(Dish dish) {
        String key = "detail_" + dish.getName();
        mainPanel.add(new DishDetailPanel(this, dish), key);
        cardLayout.show(mainPanel, key);
    }

    public void showCooking(Dish dish) {
        String key = "cook_" + dish.getName() + "_" + System.currentTimeMillis();
        mainPanel.add(new CookingPanel(this, dish), key);
        cardLayout.show(mainPanel, key);
    }

    public void setPlayer(PlayerProfile p) { this.player = p; }
    public PlayerProfile getPlayer() { return player; }
    public List<Dish> getDishes() { return dishes; }

    // Unlock next dish after completing one
    public void unlockNext() {
        for (Dish d : dishes) {
            if (!d.isUnlocked()) {
                d.setUnlocked(true);
                return;
            }
        }
    }
}
