package src;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a cooking ingredient with name, image, and drag state.
 */
public class Ingredient {
    private String name;
    private String imagePath; // <-- SET YOUR IMAGE PATH HERE
    private ImageIcon icon;
    private int x, y;
    private int width = 80, height = 80;
    private boolean dragging = false;
    private boolean placed = false;

    public Ingredient(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
        loadImage();
    }

    private void loadImage() {
        try {
            ImageIcon raw = new ImageIcon(imagePath);
            if (raw.getIconWidth() > 0) {
                Image scaled = raw.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                this.icon = new ImageIcon(scaled);
            } else {
                this.icon = null;
            }
        } catch (Exception e) {
            this.icon = null;
        }
    }

    public void draw(Graphics2D g2, int drawX, int drawY) {
        if (placed) return;
        this.x = drawX;
        this.y = drawY;
        if (icon != null) {
            g2.drawImage(icon.getImage(), drawX, drawY, width, height, null);
        } else {
            // Placeholder rectangle
            g2.setColor(new Color(255, 228, 196));
            g2.fillRoundRect(drawX, drawY, width, height, 12, 12);
            g2.setColor(new Color(139, 90, 43));
            g2.drawRoundRect(drawX, drawY, width, height, 12, 12);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            FontMetrics fm = g2.getFontMetrics();
            int tw = fm.stringWidth(name);
            g2.drawString(name, drawX + (width - tw) / 2, drawY + height / 2 + 4);
        }
        // Label below
        g2.setColor(new Color(80, 40, 10));
        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontMetrics fm = g2.getFontMetrics();
        int tw = fm.stringWidth(name);
        g2.drawString(name, drawX + (width - tw) / 2, drawY + height + 14);
    }

    public boolean contains(int mx, int my) {
        return mx >= x && mx <= x + width && my >= y && my <= y + height;
    }

    public String getName() { return name; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String path) { this.imagePath = path; loadImage(); }
    public boolean isDragging() { return dragging; }
    public void setDragging(boolean d) { this.dragging = d; }
    public boolean isPlaced() { return placed; }
    public void setPlaced(boolean p) { this.placed = p; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
