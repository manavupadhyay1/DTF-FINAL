package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Screen 5: Cooking station with drag-and-drop ingredients, step-by-step cooking.
 * Features: timer, ingredient shelf with images + labels, mixing bowl drop zone,
 * step progression, steam animation, scoring.
 */
public class CookingPanel extends JPanel implements MouseListener, MouseMotionListener {
    private GameEngine engine;
    private Dish dish;
    private List<Ingredient> ingredients;
    private int currentStep = 0;
    private int timeRemaining;
    private Timer countdownTimer;
    private Timer steamTimer;

    // Drag state
    private Ingredient draggedIngredient = null;
    private int dragOffX, dragOffY;
    private int dragX, dragY;

    // Drop zone (mixing bowl area)
    private Rectangle dropZone = new Rectangle(380, 340, 240, 180);
    private List<String> placedIngredients = new ArrayList<>();

    // Steam animation
    private int steamFrame = 0;
    private boolean cooking = false;
    private boolean completed = false;
    private int earnedScore = 0;

    // Difficulty
    private boolean showHints;
    private float timerMultiplier;

    public CookingPanel(GameEngine engine, Dish dish) {
        this.engine = engine;
        this.dish = dish;
        this.ingredients = new ArrayList<>();
        for (Ingredient orig : dish.getIngredients()) {
            ingredients.add(new Ingredient(orig.getName(), orig.getImagePath()));
        }

        // Difficulty settings
        int level = engine.getPlayer() != null ? engine.getPlayer().getLevel() : 1;
        showHints = (level == 1);
        timerMultiplier = level == 1 ? 2.0f : level == 2 ? 1.5f : 1.0f;
        timeRemaining = (int)(dish.getCookTimeSeconds() * timerMultiplier);

        setPreferredSize(new Dimension(1000, 700));
        setLayout(null);
        addMouseListener(this);
        addMouseMotionListener(this);

        // Back button
        JButton backBtn = new JButton("← BACK");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        backBtn.setForeground(new Color(190, 30, 45));
        backBtn.setBackground(new Color(255, 240, 240));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setOpaque(true);
        backBtn.setBounds(20, 20, 90, 35);
        backBtn.addActionListener(e -> {
            if (countdownTimer != null) countdownTimer.stop();
            if (steamTimer != null) steamTimer.stop();
            engine.showScreen("menu");
        });
        add(backBtn);

        // Start timer
        countdownTimer = new Timer(1000, e -> {
            if (!completed) {
                timeRemaining--;
                if (timeRemaining <= 0) {
                    timeRemaining = 0;
                    completed = true;
                    earnedScore = Math.max(0, dish.getScore() / 2);
                    countdownTimer.stop();
                }
                repaint();
            }
        });
        countdownTimer.start();

        // Steam animation timer
        steamTimer = new Timer(150, e -> {
            steamFrame = (steamFrame + 1) % 20;
            if (cooking || completed) repaint();
        });
        steamTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        // ===== BACKGROUND: Kitchen scene =====
        // Din Tai Fung header bar
        g2.setColor(new Color(190, 30, 45));
        g2.fillRect(0, 0, w, 60);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.BOLD, 20));
        drawCentered(g2, "鼎 泰 豐", w / 2, 28);
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        drawCentered(g2, "DIN TAI FUNG", w / 2, 48);

        // Kitchen wall
        g2.setColor(new Color(230, 210, 180));
        g2.fillRect(0, 60, w, 250);
        // Brick pattern
        g2.setColor(new Color(215, 195, 165));
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 20; col++) {
                int bx = col * 55 + (row % 2 == 0 ? 0 : 27);
                g2.drawRect(bx, 60 + row * 30, 55, 30);
            }
        }

        // Window (showing garden/trees)
        g2.setColor(new Color(180, 210, 160));
        g2.fillRect(300, 80, 400, 200);
        g2.setColor(new Color(120, 85, 55));
        g2.setStroke(new BasicStroke(6));
        g2.drawRect(300, 80, 400, 200);
        g2.drawLine(500, 80, 500, 280);
        g2.drawLine(300, 180, 700, 180);
        // Trees through window
        g2.setColor(new Color(100, 170, 80));
        g2.fillOval(350, 100, 80, 60);
        g2.fillOval(520, 90, 100, 70);
        g2.fillOval(620, 110, 60, 50);

        // Lanterns
        drawLantern(g2, 180, 90);
        drawLantern(g2, 780, 90);

        // Wooden counter / table
        g2.setColor(new Color(140, 95, 55));
        g2.fillRect(0, 310, w, 390);
        g2.setColor(new Color(120, 80, 45));
        for (int i = 0; i < 10; i++) {
            g2.drawLine(0, 320 + i * 40, w, 325 + i * 40);
        }

        // Bamboo mat on table
        g2.setColor(new Color(190, 180, 130));
        g2.fillRect(370, 330, 260, 200);
        g2.setColor(new Color(170, 160, 110));
        for (int i = 0; i < 10; i++) {
            g2.drawLine(370, 330 + i * 20, 630, 330 + i * 20);
        }

        // ===== DROP ZONE (mixing bowl) =====
        g2.setColor(new Color(200, 180, 150, 100));
        g2.fillOval(dropZone.x, dropZone.y, dropZone.width, dropZone.height);
        g2.setColor(new Color(160, 130, 90));
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(dropZone.x, dropZone.y, dropZone.width, dropZone.height);

        if (placedIngredients.isEmpty() && !cooking && !completed) {
            g2.setColor(new Color(120, 90, 50, 150));
            g2.setFont(new Font("SansSerif", Font.ITALIC, 13));
            drawCentered(g2, "Drop ingredients here", dropZone.x + dropZone.width / 2, dropZone.y + dropZone.height / 2);
        } else {
            // Show placed ingredients
            g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            g2.setColor(new Color(80, 50, 20));
            for (int i = 0; i < placedIngredients.size(); i++) {
                drawCentered(g2, "✓ " + placedIngredients.get(i), dropZone.x + dropZone.width / 2, dropZone.y + 30 + i * 16);
            }
        }

        // ===== INGREDIENT SHELF =====
        // Shelf background
        g2.setColor(new Color(100, 70, 40));
        g2.fillRect(0, 555, w, 145);
        g2.setColor(new Color(80, 55, 30));
        g2.fillRect(0, 555, w, 6);

        // Draw each ingredient with image + label
        int shelfX = 30;
        int shelfY = 575;
        int spacing = (w - 60) / Math.max(ingredients.size(), 1);
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ing = ingredients.get(i);
            if (!ing.isPlaced() && ing != draggedIngredient) {
                ing.draw(g2, shelfX + i * spacing, shelfY);
            }
        }

        // ===== DRAGGED INGREDIENT =====
        if (draggedIngredient != null) {
            draggedIngredient.draw(g2, dragX - 40, dragY - 40);
        }

        // ===== COOKING STEPS =====
        g2.setColor(new Color(255, 248, 230, 230));
        g2.fillRoundRect(15, 320, 200, 220, 12, 12);
        g2.setColor(new Color(140, 100, 60));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(15, 320, 200, 220, 12, 12);

        g2.setColor(new Color(100, 60, 20));
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        g2.drawString("Steps:", 30, 345);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        List<String> steps = dish.getCookingSteps();
        for (int i = 0; i < steps.size(); i++) {
            boolean done = i < currentStep;
            boolean current = i == currentStep;
            g2.setColor(done ? new Color(80, 160, 80) : current ? new Color(190, 30, 45) : new Color(150, 130, 100));
            g2.setFont(new Font("SansSerif", current ? Font.BOLD : Font.PLAIN, 11));
            String prefix = done ? "✓ " : current ? "→ " : "  ";
            g2.drawString(prefix + (i + 1) + ". " + truncate(steps.get(i), 22), 28, 365 + i * 18);
        }

        // Hints (Beginner mode)
        if (showHints && currentStep < steps.size() && !completed) {
            g2.setColor(new Color(255, 255, 200, 220));
            g2.fillRoundRect(15, 545, 200, 25, 6, 6);
            g2.setColor(new Color(180, 130, 30));
            g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
            g2.drawString("💡 Hint: " + truncate(steps.get(currentStep), 25), 25, 562);
        }

        // ===== TIMER =====
        g2.setColor(new Color(255, 248, 230, 230));
        g2.fillRoundRect(770, 320, 210, 70, 12, 12);
        g2.setColor(new Color(140, 100, 60));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(770, 320, 210, 70, 12, 12);
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        g2.setColor(new Color(100, 60, 20));
        g2.drawString("⏱ Time:", 785, 345);
        g2.setFont(new Font("SansSerif", Font.BOLD, 28));
        g2.setColor(timeRemaining < 15 ? new Color(200, 30, 30) : new Color(60, 120, 60));
        String timeStr = String.format("%d:%02d", timeRemaining / 60, timeRemaining % 60);
        g2.drawString(timeStr, 860, 378);

        // Score display
        g2.setColor(new Color(255, 248, 230, 230));
        g2.fillRoundRect(770, 400, 210, 50, 12, 12);
        g2.setColor(new Color(100, 60, 20));
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        g2.drawString("Dish: " + dish.getName(), 785, 422);
        g2.drawString("Score: " + dish.getScore() + " pts", 785, 440);

        // ===== STEAM ANIMATION =====
        if (cooking || completed) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            g2.setColor(new Color(255, 255, 255));
            for (int i = 0; i < 6; i++) {
                int sx = dropZone.x + 40 + i * 35 + (int)(Math.sin(steamFrame + i) * 8);
                int sy = dropZone.y - 10 - steamFrame % 10 * 3 - i * 5;
                g2.fillOval(sx, sy, 18 + i % 3 * 4, 12);
                g2.fillOval(sx + 5, sy - 15, 14, 10);
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        // ===== COMPLETION OVERLAY =====
        if (completed) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, w, h);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            g2.setColor(new Color(255, 248, 230));
            g2.fillRoundRect(w / 2 - 200, h / 2 - 120, 400, 240, 20, 20);

            g2.setColor(new Color(100, 60, 20));
            g2.setFont(new Font("Serif", Font.BOLD, 28));
            drawCentered(g2, cooking ? "🎉 Dish Complete!" : "⏰ Time's Up!", w / 2, h / 2 - 70);

            g2.setFont(new Font("SansSerif", Font.BOLD, 18));
            drawCentered(g2, dish.getName(), w / 2, h / 2 - 35);

            g2.setColor(new Color(220, 180, 50));
            g2.setFont(new Font("SansSerif", Font.BOLD, 20));
            StringBuilder stars = new StringBuilder();
            int earnedStars = cooking ? dish.getStarRating() : Math.max(1, dish.getStarRating() / 2);
            for (int i = 0; i < earnedStars; i++) stars.append("★ ");
            drawCentered(g2, stars.toString(), w / 2, h / 2);

            g2.setColor(new Color(80, 50, 20));
            g2.setFont(new Font("SansSerif", Font.PLAIN, 16));
            drawCentered(g2, "Score: +" + earnedScore + "  |  Coins: +" + dish.getProfit(), w / 2, h / 2 + 35);

            // Buttons drawn via Swing (added dynamically)
        }

        // Mascot in corner
        drawSmallMascot(g2, 20, 70);

        g2.dispose();
    }

    private void completeStep() {
        currentStep++;
        if (currentStep >= dish.getCookingSteps().size()) {
            cooking = true;
            earnedScore = dish.getScore() + (timeRemaining > 0 ? 5 : 0);

            // Delay to show steam, then complete
            Timer finishTimer = new Timer(2000, e -> {
                completed = true;
                countdownTimer.stop();

                // Add score to player
                if (engine.getPlayer() != null) {
                    engine.getPlayer().addScore(earnedScore);
                    engine.getPlayer().addCoins(dish.getProfit());
                    engine.getPlayer().completeDish();
                }

                // Add "Back to Menu" button
                JButton menuBtn = new JButton("BACK TO MENU");
                menuBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
                menuBtn.setForeground(Color.WHITE);
                menuBtn.setBackground(new Color(180, 30, 30));
                menuBtn.setFocusPainted(false);
                menuBtn.setBorderPainted(false);
                menuBtn.setOpaque(true);
                menuBtn.setBounds(getWidth() / 2 - 80, getHeight() / 2 + 60, 160, 40);
                menuBtn.addActionListener(ev -> {
                    steamTimer.stop();
                    engine.showScreen("menu");
                });
                add(menuBtn);
                revalidate();
                repaint();
            });
            finishTimer.setRepeats(false);
            finishTimer.start();
        }
        repaint();
    }

    // ===== MOUSE EVENTS (drag and drop) =====
    @Override
    public void mousePressed(MouseEvent e) {
        if (completed) return;
        for (Ingredient ing : ingredients) {
            if (!ing.isPlaced() && ing.contains(e.getX(), e.getY())) {
                draggedIngredient = ing;
                dragX = e.getX();
                dragY = e.getY();
                dragOffX = e.getX() - ing.getX();
                dragOffY = e.getY() - ing.getY();
                repaint();
                return;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (draggedIngredient != null) {
            dragX = e.getX();
            dragY = e.getY();
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (draggedIngredient != null) {
            if (dropZone.contains(e.getX(), e.getY())) {
                draggedIngredient.setPlaced(true);
                placedIngredients.add(draggedIngredient.getName());

                // Check if all ingredients are placed
                boolean allPlaced = true;
                for (Ingredient ing : ingredients) {
                    if (!ing.isPlaced()) { allPlaced = false; break; }
                }
                if (allPlaced || placedIngredients.size() >= ingredients.size()) {
                    // Auto-advance through steps
                    Timer stepTimer = new Timer(800, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ev) {
                            if (currentStep < dish.getCookingSteps().size()) {
                                completeStep();
                            } else {
                                ((Timer) ev.getSource()).stop();
                            }
                        }
                    });
                    stepTimer.start();
                } else {
                    completeStep();
                }
            }
            draggedIngredient = null;
            repaint();
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}

    private void drawSmallMascot(Graphics2D g2, int x, int y) {
        if (tryDrawImage(g2, GameData.IMG_MASCOT, x, y, 80, 100)) return;
        g2.setColor(new Color(255, 245, 230));
        g2.fillOval(x + 10, y, 55, 50);
        g2.setColor(new Color(60, 30, 10));
        g2.fillOval(x + 25, y + 18, 5, 5);
        g2.fillOval(x + 40, y + 18, 5, 5);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawArc(x + 28, y + 24, 18, 12, 200, 140);
        g2.setColor(new Color(210, 40, 40));
        g2.fillRoundRect(x + 18, y + 48, 38, 30, 8, 8);
        g2.setColor(new Color(190, 30, 45));
        g2.fillOval(x + 28, y - 10, 18, 14);
    }

    private void drawLantern(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(200, 160, 60));
        g2.fillRect(x + 10, y - 20, 4, 20);
        g2.fillArc(x - 5, y - 25, 35, 15, 0, 180);
        g2.setColor(new Color(130, 60, 150));
        g2.fillOval(x - 5, y, 35, 50);
        g2.setColor(new Color(150, 80, 170));
        g2.fillOval(x, y + 5, 25, 40);
        g2.setColor(new Color(255, 200, 80, 120));
        g2.fillOval(x + 5, y + 15, 15, 20);
    }

    private String truncate(String s, int max) {
        return s.length() > max ? s.substring(0, max - 2) + ".." : s;
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
