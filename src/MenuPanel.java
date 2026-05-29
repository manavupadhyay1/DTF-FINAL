package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Screen 3: Rotating table / horizontal carousel of dishes to select.
 */
public class MenuPanel extends JPanel {
    private GameEngine engine;
    private List<Dish> dishes;
    private int selectedIndex = 0;
    private float rotationAngle = 0;
    private Timer animTimer;

    public MenuPanel(GameEngine engine, List<Dish> dishes) {
        this.engine = engine;
        this.dishes = dishes;
        setPreferredSize(new Dimension(1000, 700));
        setLayout(null);

        // Navigation buttons
        JButton leftBtn = createNavButton("◀", 30, 320);
        leftBtn.addActionListener(e -> {
            selectedIndex = (selectedIndex - 1 + dishes.size()) % dishes.size();
            repaint();
        });
        add(leftBtn);

        JButton rightBtn = createNavButton("▶", 920, 320);
        rightBtn.addActionListener(e -> {
            selectedIndex = (selectedIndex + 1) % dishes.size();
            repaint();
        });
        add(rightBtn);

        // Select button
        JButton selectBtn = new JButton("VIEW DISH →");
        selectBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        selectBtn.setForeground(Color.WHITE);
        selectBtn.setBackground(new Color(180, 30, 30));
        selectBtn.setFocusPainted(false);
        selectBtn.setBorderPainted(false);
        selectBtn.setOpaque(true);
        selectBtn.setBounds(410, 600, 180, 45);
        selectBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        selectBtn.addActionListener(e -> {
            Dish d = dishes.get(selectedIndex);
            if (d.isUnlocked()) {
                engine.showDishDetail(d);
            } else {
                JOptionPane.showMessageDialog(this, "🔒 This dish is locked!\nComplete more dishes to unlock.", "Locked", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        add(selectBtn);

        // Back button
        JButton backBtn = new JButton("← BACK");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        backBtn.setForeground(new Color(190, 30, 45));
        backBtn.setBackground(new Color(255, 240, 240));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setOpaque(true);
        backBtn.setBounds(20, 20, 90, 35);
        backBtn.addActionListener(e -> engine.showScreen("landing"));
        add(backBtn);
    }

    private JButton createNavButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 28));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(190, 30, 45, 180));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBounds(x, y, 50, 50);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        // Background with cherry blossom scene vibe
        g2.setColor(new Color(60, 40, 30));
        g2.fillRect(0, 0, w, h);

        // Warm wooden table band
        g2.setColor(new Color(160, 110, 60));
        g2.fillRect(0, 200, w, 320);
        // Wood grain lines
        g2.setColor(new Color(140, 95, 50));
        for (int i = 0; i < 8; i++) {
            g2.drawLine(0, 210 + i * 40, w, 215 + i * 40);
        }

        // Cherry blossoms top area
        g2.setColor(new Color(255, 200, 200, 100));
        for (int i = 0; i < 20; i++) {
            int bx = (int)(Math.random() * w);
            int by = (int)(Math.random() * 180);
            g2.fillOval(bx, by, 12, 12);
        }

        // Title
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 28));
        drawCentered(g2, "SELECT YOUR DISH", w / 2, 170);

        // Player info
        if (engine.getPlayer() != null) {
            g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
            g2.drawString("Chef: " + engine.getPlayer().getName() + "  |  Level: " +
                engine.getPlayer().getLevelName() + "  |  Coins: " + engine.getPlayer().getCoins(), 130, 40);
        }

        // Draw dish carousel
        int centerX = w / 2;
        int centerY = 360;
        for (int i = 0; i < dishes.size(); i++) {
            int offset = i - selectedIndex;
            if (Math.abs(offset) > 3) continue;

            int dx = centerX + offset * 150;
            float scale = (i == selectedIndex) ? 1.0f : 0.65f;
            float a = (i == selectedIndex) ? 1.0f : 0.5f;
            int size = (int)(110 * scale);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));

            // Plate
            g2.setColor(new Color(255, 250, 240));
            g2.fillOval(dx - size / 2, centerY - size / 2, size, size);
            g2.setColor(new Color(200, 180, 150));
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(dx - size / 2, centerY - size / 2, size, size);

            // Dish image or placeholder
            Dish d = dishes.get(i);
            boolean drawn = tryDrawImage(g2, d.getImagePath(), dx - size / 3, centerY - size / 3, (int)(size * 0.66), (int)(size * 0.66));
            if (!drawn) {
                g2.setColor(new Color(100, 60, 20));
                g2.setFont(new Font("SansSerif", Font.BOLD, (int)(11 * scale)));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(d.getName(), dx - fm.stringWidth(d.getName()) / 2, centerY + 5);
            }

            // Lock icon
            if (!d.isUnlocked()) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2.setColor(new Color(0, 0, 0, 150));
                g2.fillOval(dx - size / 2, centerY - size / 2, size, size);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 24));
                drawCentered(g2, "🔒", dx, centerY + 8);
            }

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        // Selected dish name
        Dish sel = dishes.get(selectedIndex);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 22));
        drawCentered(g2, sel.getName(), centerX, 530);

        // Stars
        g2.setFont(new Font("SansSerif", Font.PLAIN, 20));
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < sel.getStarRating(); i++) stars.append("★ ");
        g2.setColor(new Color(255, 200, 50));
        drawCentered(g2, stars.toString().trim(), centerX, 560);

        // Mascot in corner
        drawSmallMascot(g2, 30, h - 170);

        g2.dispose();
    }

    private void drawSmallMascot(Graphics2D g2, int x, int y) {
        if (tryDrawImage(g2, GameData.IMG_MASCOT, x, y, 100, 130)) return;
        g2.setColor(new Color(255, 245, 230));
        g2.fillOval(x + 15, y, 70, 65);
        g2.setColor(new Color(60, 30, 10));
        g2.fillOval(x + 35, y + 25, 6, 6);
        g2.fillOval(x + 55, y + 25, 6, 6);
        g2.setStroke(new BasicStroke(2));
        g2.drawArc(x + 38, y + 30, 22, 15, 200, 140);
        g2.setColor(new Color(210, 40, 40));
        g2.fillRoundRect(x + 25, y + 60, 50, 40, 10, 10);
        g2.setColor(new Color(190, 30, 45));
        g2.fillOval(x + 40, y - 15, 20, 15);
    }

    private boolean tryDrawImage(Graphics2D g2, String path, int x, int y, int w, int h) {
        try {
            ImageIcon ic = new ImageIcon(path);
            if (ic.getIconWidth() > 0) {
                g2.drawImage(ic.getImage(), x, y, w, h, null);
                return true;
            }
        } catch (Exception e) {}
        return false;
    }

    private void drawCentered(Graphics2D g2, String text, int cx, int cy) {
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(text, cx - fm.stringWidth(text) / 2, cy);
    }
}
