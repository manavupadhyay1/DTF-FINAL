package src;

/**
 * Stores player state: name, score, level, coins.
 */
public class PlayerProfile {
    private String name;
    private int totalScore;
    private int coins;
    private int level; // 1=Beginner, 2=Amateur, 3=Professional
    private int dishesCompleted;

    public PlayerProfile(String name) {
        this.name = name;
        this.totalScore = 0;
        this.coins = 100;
        this.level = 1;
        this.dishesCompleted = 0;
    }

    public void addScore(int s) { this.totalScore += s; }
    public void addCoins(int c) { this.coins += c; }
    public void completeDish() {
        this.dishesCompleted++;
        if (dishesCompleted >= 3 && level < 2) level = 2;
        if (dishesCompleted >= 7 && level < 3) level = 3;
    }

    public String getName() { return name; }
    public int getTotalScore() { return totalScore; }
    public int getCoins() { return coins; }
    public int getLevel() { return level; }
    public int getDishesCompleted() { return dishesCompleted; }
    public String getLevelName() {
        switch(level) {
            case 1: return "Beginner";
            case 2: return "Amateur";
            case 3: return "Professional";
            default: return "Beginner";
        }
    }
}
