package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Central data store: creates all dishes and ingredients.
 * *** UPDATE IMAGE PATHS HERE to point to your actual asset files ***
 */
public class GameData {

    // =====================================================
    // ASSET PATH CONFIGURATION — Change these to your paths
    // =====================================================
    // Ingredient images
    public static String IMG_FLOUR       = "assets/ingredients/flour.png";
    public static String IMG_SUGAR       = "assets/ingredients/sugar.png";
    public static String IMG_SALT        = "assets/ingredients/salt.png";
    public static String IMG_MILK        = "assets/ingredients/milk.png";
    public static String IMG_EGGS        = "assets/ingredients/eggs.png";
    public static String IMG_BUTTER      = "assets/ingredients/butter.png";
    public static String IMG_CHOCOLATE   = "assets/ingredients/chocolate.png";
    public static String IMG_DOUGH       = "assets/ingredients/dough.png";
    public static String IMG_PORK        = "assets/ingredients/pork.png";
    public static String IMG_GINGER      = "assets/ingredients/ginger.png";
    public static String IMG_SOY_SAUCE   = "assets/ingredients/soy_sauce.png";
    public static String IMG_SESAME_OIL  = "assets/ingredients/sesame_oil.png";
    public static String IMG_SCALLIONS   = "assets/ingredients/scallions.png";
    public static String IMG_WRAPPER     = "assets/ingredients/wrapper.png";
    public static String IMG_BROTH       = "assets/ingredients/broth.png";
    public static String IMG_NOODLES     = "assets/ingredients/noodles.png";
    public static String IMG_CABBAGE     = "assets/ingredients/cabbage.png";
    public static String IMG_CUCUMBER    = "assets/ingredients/cucumber.png";
    public static String IMG_CHILI       = "assets/ingredients/chili.png";
    public static String IMG_VINEGAR     = "assets/ingredients/vinegar.png";
    public static String IMG_SHRIMP      = "assets/ingredients/shrimp.png";
    public static String IMG_RICE        = "assets/ingredients/rice.png";

    // Dish images
    public static String IMG_CHOCO_BAO   = "assets/dishes/chocolate_bao.png";
    public static String IMG_XLB         = "assets/dishes/xlb.png";
    public static String IMG_NOODLE_SOUP = "assets/dishes/noodle_soup.png";
    public static String IMG_FRIED_RICE  = "assets/dishes/fried_rice.png";
    public static String IMG_SHRIMP_DUMP = "assets/dishes/shrimp_dumpling.png";
    public static String IMG_CUCUMBER_SD = "assets/dishes/cucumber_salad.png";

    // Background images
    public static String IMG_BG_LANDING  = "assets/backgrounds/landing_bg.png";
    public static String IMG_BG_KITCHEN  = "assets/backgrounds/kitchen_bg.png";
    public static String IMG_MASCOT      = "assets/ui/mascot.png";
    public static String IMG_NEW_MASCOT = "assets/ui/new_mascot.png";

    public static List<Dish> createDishes() {
        List<Dish> dishes = new ArrayList<>();

        // 1. Chocolate Bao
        Dish chocoBao = new Dish("Chocolate Bao",
            "Soft, fluffy steamed bao with rich,\ndecadent molten chocolate.\nA Din Tai Fung classic.",
            IMG_CHOCO_BAO, 60, 20, 35, 5, true);
        chocoBao.addIngredient(new Ingredient("Flour", IMG_FLOUR));
        chocoBao.addIngredient(new Ingredient("Sugar", IMG_SUGAR));
        chocoBao.addIngredient(new Ingredient("Milk", IMG_MILK));
        chocoBao.addIngredient(new Ingredient("Butter", IMG_BUTTER));
        chocoBao.addIngredient(new Ingredient("Chocolate", IMG_CHOCOLATE));
        chocoBao.addIngredient(new Ingredient("Dough", IMG_DOUGH));
        chocoBao.addStep("Mix flour, sugar, and milk into a bowl");
        chocoBao.addStep("Knead the dough until smooth");
        chocoBao.addStep("Add chocolate filling to center");
        chocoBao.addStep("Fold and seal the bao");
        chocoBao.addStep("Steam for 8 minutes");
        dishes.add(chocoBao);

        // 2. Xiao Long Bao
        Dish xlb = new Dish("Xiao Long Bao",
            "Delicate soup dumplings filled with\nsavory pork and rich broth.\nThe signature dish.",
            IMG_XLB, 90, 30, 50, 5, true);
        xlb.addIngredient(new Ingredient("Wrapper", IMG_WRAPPER));
        xlb.addIngredient(new Ingredient("Pork", IMG_PORK));
        xlb.addIngredient(new Ingredient("Ginger", IMG_GINGER));
        xlb.addIngredient(new Ingredient("Soy Sauce", IMG_SOY_SAUCE));
        xlb.addIngredient(new Ingredient("Sesame Oil", IMG_SESAME_OIL));
        xlb.addIngredient(new Ingredient("Broth", IMG_BROTH));
        xlb.addStep("Prepare the pork filling with ginger");
        xlb.addStep("Add soy sauce and sesame oil");
        xlb.addStep("Mix in the gelatinized broth");
        xlb.addStep("Wrap filling in thin wrappers");
        xlb.addStep("Pleat each dumpling with 18 folds");
        xlb.addStep("Steam on bamboo steamer for 6 min");
        dishes.add(xlb);

        // 3. Noodle Soup
        Dish noodleSoup = new Dish("Noodle Soup",
            "Hand-pulled noodles in a fragrant\npork bone broth with soft-boiled egg\nand fresh scallions.",
            IMG_NOODLE_SOUP, 120, 25, 40, 4, true);
        noodleSoup.addIngredient(new Ingredient("Noodles", IMG_NOODLES));
        noodleSoup.addIngredient(new Ingredient("Broth", IMG_BROTH));
        noodleSoup.addIngredient(new Ingredient("Eggs", IMG_EGGS));
        noodleSoup.addIngredient(new Ingredient("Scallions", IMG_SCALLIONS));
        noodleSoup.addIngredient(new Ingredient("Pork", IMG_PORK));
        noodleSoup.addStep("Boil the noodles until al dente");
        noodleSoup.addStep("Prepare the pork bone broth");
        noodleSoup.addStep("Soft-boil the eggs (6.5 minutes)");
        noodleSoup.addStep("Assemble noodles in bowl with broth");
        noodleSoup.addStep("Top with egg, pork, and scallions");
        dishes.add(noodleSoup);

        // 4. Fried Rice
        Dish friedRice = new Dish("Fried Rice",
            "Wok-tossed rice with eggs, scallions,\nand savory soy sauce.\nSimple but perfected.",
            IMG_FRIED_RICE, 60, 15, 25, 3, false);
        friedRice.addIngredient(new Ingredient("Rice", IMG_RICE));
        friedRice.addIngredient(new Ingredient("Eggs", IMG_EGGS));
        friedRice.addIngredient(new Ingredient("Scallions", IMG_SCALLIONS));
        friedRice.addIngredient(new Ingredient("Soy Sauce", IMG_SOY_SAUCE));
        friedRice.addIngredient(new Ingredient("Salt", IMG_SALT));
        friedRice.addStep("Heat wok until smoking");
        friedRice.addStep("Scramble eggs in wok");
        friedRice.addStep("Add rice and toss on high heat");
        friedRice.addStep("Season with soy sauce and salt");
        friedRice.addStep("Garnish with scallions");
        dishes.add(friedRice);

        // 5. Shrimp Dumpling
        Dish shrimpDump = new Dish("Shrimp Dumpling",
            "Crystal-skinned dumplings with\njuicy whole shrimp filling.\nA dim sum favorite.",
            IMG_SHRIMP_DUMP, 75, 25, 45, 4, false);
        shrimpDump.addIngredient(new Ingredient("Wrapper", IMG_WRAPPER));
        shrimpDump.addIngredient(new Ingredient("Shrimp", IMG_SHRIMP));
        shrimpDump.addIngredient(new Ingredient("Ginger", IMG_GINGER));
        shrimpDump.addIngredient(new Ingredient("Sesame Oil", IMG_SESAME_OIL));
        shrimpDump.addIngredient(new Ingredient("Salt", IMG_SALT));
        shrimpDump.addStep("Devein and chop the shrimp");
        shrimpDump.addStep("Season with ginger and sesame oil");
        shrimpDump.addStep("Wrap in crystal wrappers");
        shrimpDump.addStep("Steam for 5 minutes");
        dishes.add(shrimpDump);

        // 6. Cucumber Salad
        Dish cucumberSalad = new Dish("Cucumber Salad",
            "Smashed cucumbers in a tangy,\nspicy chili-vinegar dressing.\nRefreshing and bold.",
            IMG_CUCUMBER_SD, 30, 10, 15, 3, false);
        cucumberSalad.addIngredient(new Ingredient("Cucumber", IMG_CUCUMBER));
        cucumberSalad.addIngredient(new Ingredient("Chili", IMG_CHILI));
        cucumberSalad.addIngredient(new Ingredient("Vinegar", IMG_VINEGAR));
        cucumberSalad.addIngredient(new Ingredient("Soy Sauce", IMG_SOY_SAUCE));
        cucumberSalad.addIngredient(new Ingredient("Sesame Oil", IMG_SESAME_OIL));
        cucumberSalad.addStep("Smash cucumbers with knife");
        cucumberSalad.addStep("Cut into bite-sized pieces");
        cucumberSalad.addStep("Mix chili, vinegar, soy sauce");
        cucumberSalad.addStep("Toss cucumbers in dressing");
        dishes.add(cucumberSalad);

        return dishes;
    }
}
