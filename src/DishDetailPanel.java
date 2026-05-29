package src;

import java.awt.*;
import javax.swing.*;

public class DishDetailPanel extends JPanel {
    private GameEngine engine;
    private Dish dish;

    public DishDetailPanel(GameEngine engine, Dish dish) {
        this.engine = engine;
        this.dish = dish;

        setPreferredSize(new Dimension(1000, 700));
        setLayout(new BorderLayout());

        // ===== MAIN CARD PANEL =====
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(255, 248, 230));
        card.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // ===== DISH NAME =====
        JLabel nameLabel = new JLabel("Din Tai Fung " + dish.getName());
        nameLabel.setFont(new Font("Serif", Font.BOLD, 26));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setForeground(new Color(60, 30, 10));
        card.add(nameLabel);

        card.add(Box.createVerticalStrut(15));

        // ===== IMAGE =====
        ImageIcon icon = new ImageIcon(dish.getImagePath());
        Image img = icon.getImage().getScaledInstance(140, 90, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(imageLabel);

        card.add(Box.createVerticalStrut(15));

        // ===== STARS =====
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < dish.getStarRating(); i++) stars.append("★ ");

        JLabel starLabel = new JLabel(stars.toString());
        starLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        starLabel.setForeground(new Color(220, 180, 50));
        starLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(starLabel);

        card.add(Box.createVerticalStrut(20));

        // ===== DESCRIPTION =====
        JLabel profileTitle = new JLabel("Profile");
        profileTitle.setFont(new Font("Serif", Font.BOLD, 18));
        profileTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(profileTitle);

        card.add(Box.createVerticalStrut(10));

        JLabel desc = new JLabel("<html><div style='text-align:center;'>"
                + dish.getDescription().replace("\n", "<br>")
                + "</div></html>");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 14));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(desc);

        card.add(Box.createVerticalStrut(20));

        // ===== EFFECT =====
        JLabel effectTitle = new JLabel("Effect");
        effectTitle.setFont(new Font("Serif", Font.BOLD, 18));
        effectTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(effectTitle);

        card.add(Box.createVerticalStrut(10));

        JLabel stats = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "profit: " + dish.getProfit() + "<br>"
                        + "cook time: " + (dish.getCookTimeSeconds() / 60) + " minute<br>"
                        + "score: " + dish.getScore()
                        + "</div></html>"
        );
        stats.setFont(new Font("SansSerif", Font.PLAIN, 14));
        stats.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(stats);

        card.add(Box.createVerticalStrut(20));

        // ===== INGREDIENTS =====
        JLabel ingTitle = new JLabel("Ingredients:");
        ingTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        ingTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(ingTitle);

        StringBuilder ingStr = new StringBuilder();
        for (Ingredient ing : dish.getIngredients()) {
            if (ingStr.length() > 0) ingStr.append(", ");
            ingStr.append(ing.getName());
        }

        JLabel ingLabel = new JLabel("<html><div style='text-align:center;'>"
                + ingStr.toString()
                + "</div></html>");
        ingLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        ingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(ingLabel);

        // ===== BUTTON PANEL =====
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(190, 30, 45));

        JButton cookBtn = new JButton("START COOKING →");
        cookBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        cookBtn.setForeground(Color.WHITE);
        cookBtn.setBackground(new Color(180, 30, 30));
        cookBtn.setFocusPainted(false);
        cookBtn.setBorderPainted(false);
        cookBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cookBtn.addActionListener(e -> engine.showCooking(dish));

        bottomPanel.add(cookBtn);

        // ===== BACK BUTTON =====
        JButton backBtn = new JButton("← BACK");
        backBtn.setBounds(20, 20, 100, 35);
        backBtn.addActionListener(e -> engine.showScreen("menu"));

        // ===== WRAPPER PANEL =====
        JPanel wrapper = new JPanel(null);
        wrapper.setBackground(new Color(190, 30, 45));

        card.setBounds(300, 50, 400, 520);
        wrapper.add(card);
        wrapper.add(backBtn);

        add(wrapper, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}