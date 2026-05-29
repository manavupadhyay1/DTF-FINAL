package src;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

public class LandingPanel extends JPanel {
    private GameEngine engine;

    public LandingPanel(GameEngine engine) {
        this.engine = engine;
        setPreferredSize(new Dimension(1000, 700));
        setLayout(null);

        JButton startBtn = new JButton("START GAME");
        startBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        startBtn.setForeground(Color.WHITE);
        startBtn.setBackground(new Color(180, 30, 30));
        startBtn.setBounds(400, 580, 200, 55);
        startBtn.setFocusPainted(false);
        startBtn.setBorderPainted(false);
        startBtn.addActionListener(e -> engine.showScreen("name"));
        add(startBtn);
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

        // ===== MAIN CIRCLE =====
        int R = 300;
        int cx = w / 2;
        int cy = h / 2 - 40;

        g2.setColor(new Color(255, 220, 220));
        g2.fillOval(cx - R, cy - R, R * 2, R * 2);

        // ===== DISH POSITIONS =====
        String[] images = {
                GameData.IMG_XLB,
                GameData.IMG_NOODLE_SOUP,
                GameData.IMG_CUCUMBER_SD,
                GameData.IMG_CHOCO_BAO
        };

        int smallR = 65;
        int orbit = 200;

        for (int i = 0; i < 4; i++) {
            double angle = Math.toRadians(i * 90 - 90);

            int x = (int)(cx + orbit * Math.cos(angle)) - smallR;
            int y = (int)(cy + orbit * Math.sin(angle)) - smallR;

            drawDishCircle(g2, images[i], x, y, smallR * 2);
        }

        // ===== CENTER TEXT (FIXED POSITION) =====
        g2.setColor(new Color(190, 30, 45));
        g2.fillRoundRect(cx - 130, cy - 30, 260, 80, 20, 20);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.BOLD, 28));
        drawCentered(g2, "鼎 泰 豐", cx, cy);

        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        drawCentered(g2, "DIN TAI FUNG", cx, cy + 25);

        // ===== MASCOT =====
        tryDrawImage(g2, GameData.IMG_MASCOT, 60, cy - 80, 150, 180);
    }

    // ===== FIXED DISH DRAWING =====
    private void drawDishCircle(Graphics2D g2, String path, int x, int y, int size) {

        // Plate
        g2.setColor(new Color(255, 245, 230));
        g2.fillOval(x, y, size, size);

        g2.setColor(new Color(180, 140, 90));
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x, y, size, size);

        // CLIP IMAGE INTO CIRCLE (🔥 removes white square issue)
        Shape oldClip = g2.getClip();
        Ellipse2D circle = new Ellipse2D.Double(x + 5, y + 5, size - 10, size - 10);
        g2.setClip(circle);

        tryDrawImage(g2, path, x + 5, y + 5, size - 10, size - 10);

        g2.setClip(oldClip); // restore
    }

    private boolean tryDrawImage(Graphics2D g2, String path, int x, int y, int w, int h) {
        try {
            ImageIcon ic = new ImageIcon(path);
            if (ic.getIconWidth() > 0) {
                g2.drawImage(ic.getImage(), x, y, w, h, null);
                return true;
            }
        } catch (Exception ignored) {}
        return false;
    }

    private void drawCentered(Graphics2D g2, String text, int cx, int cy) {
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(text, cx - fm.stringWidth(text) / 2, cy);
    }
}