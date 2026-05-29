package src;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a dish with its recipe, stats, and cooking steps.
 */
public class Dish {
    private String name;
    private String description;
    private String imagePath; // <-- SET YOUR DISH IMAGE PATH HERE
    private int cookTimeSeconds;
    private int score;
    private int profit;
    private int starRating; // 1-5
    private boolean unlocked;
    private List<Ingredient> ingredients;
    private List<String> cookingSteps;

    public Dish(String name, String description, String imagePath,
                int cookTimeSeconds, int score, int profit, int starRating, boolean unlocked) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.cookTimeSeconds = cookTimeSeconds;
        this.score = score;
        this.profit = profit;
        this.starRating = starRating;
        this.unlocked = unlocked;
        this.ingredients = new ArrayList<>();
        this.cookingSteps = new ArrayList<>();
    }

    public void addIngredient(Ingredient ing) { ingredients.add(ing); }
    public void addStep(String step) { cookingSteps.add(step); }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String p) { this.imagePath = p; }
    public int getCookTimeSeconds() { return cookTimeSeconds; }
    public int getScore() { return score; }
    public int getProfit() { return profit; }
    public int getStarRating() { return starRating; }
    public boolean isUnlocked() { return unlocked; }
    public void setUnlocked(boolean u) { this.unlocked = u; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public List<String> getCookingSteps() { return cookingSteps; }
}
