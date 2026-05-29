package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NameEntryPanel extends JPanel {
    private GameEngine engine;
    private JTextField nameField;

    public NameEntryPanel(GameEngine engine) {
        this.engine = engine;
        setPreferredSize(new Dimension(1000, 700));
        setLayout(null);

        // ===== NAME FIELD (SHIFTED RIGHT) =====
        nameField = new JTextField();
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 26));
        nameField.setForeground(new Color(150, 150, 150));
        nameField.setText("ENTER YOUR NAME");
        nameField.setHorizontalAlignment(JTextField.CENTER);

        // moved right so it never overlaps mascot
        nameField.setBounds(420, 300, 450, 65);

        nameField.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        nameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameField.getText().equals("ENTER YOUR NAME")) {
                    nameField.setText("");
                    nameField.setForeground(new Color(60, 30, 10));
                }
            }
        });

        add(nameField);

        // ===== BUTTON =====
        JButton continueBtn = new JButton("LET'S COOK! →");
        continueBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        continueBtn.setForeground(Color.WHITE);
        continueBtn.setBackground(new Color(180, 30, 30));
        continueBtn.setBounds(540, 400, 200, 50);
        continueBtn.setFocusPainted(false);
        continueBtn.setBorderPainted(false);

        continueBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty() || name.equals("ENTER YOUR NAME")) {
                nameField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                return;
            }
            engine.setPlayer(new PlayerProfile(name));
            engine.showScreen("menu");
        });

        add(continueBtn);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int w = getWidth();
        int h = getHeight();

        // ===== BACKGROUND =====
        g2.setColor(new Color(190, 30, 45));
        g2.fillRect(0, 0, w, h);

        // ❌ REMOVED: "R U DTF?" TEXT COMPLETELY

        // ===== MASCOT (SHIFTED LEFT & FULLY VISIBLE) =====
        drawNewMascot(g2, 80, 120);
    }

    // ===== DRAW MASCOT =====
    private void drawNewMascot(Graphics2D g2, int x, int y) {
        try {
            ImageIcon icon = new ImageIcon(GameData.IMG_NEW_MASCOT);
            if (icon.getIconWidth() > 0) {

                // Slightly smaller so it fits cleanly
                g2.drawImage(icon.getImage(), x, y, 260, 320, null);
            }
        } catch (Exception e) {
            g2.setColor(Color.WHITE);
            g2.drawString("Image not found", x, y);
        }
    }
}