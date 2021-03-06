package me.evilterabite.bitscraftingapi.gui.crafting;

import me.evilterabite.bitscraftingapi.BitsCraftingAPI;
import me.evilterabite.bitscraftingapi.api.ItemHandler;
import me.evilterabite.bitscraftingapi.files.recipes.RecipeConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class RecipeHandler {
    private static Plugin plugin = BitsCraftingAPI.getPlugin(BitsCraftingAPI.class);
    private static FileConfiguration recipeConfig = RecipeConfig.get();


    public static Boolean exists(String recipe){
        Set<String> recipes = Objects.requireNonNull(recipeConfig.getConfigurationSection("Recipes")).getKeys(false);
        return recipes.contains(recipe);
    }

    @Deprecated
    public static ShapedRecipe createRecipe(String configRecipe) {
        if(exists(configRecipe)) {
            Boolean shaped = recipeConfig.getBoolean("Recipes.%recipe%.Shaped.Bool".replace("%recipe%", configRecipe));
            List<String> layout = recipeConfig.getStringList("Recipes.%recipe%.Shaped.Layout".replace("%recipe%", configRecipe));
            Set<String> charSet = recipeConfig.getConfigurationSection("Recipes.%recipe%.Shaped.Items".replace("%recipe%", configRecipe)).getKeys(false);
            List<String> charList = new ArrayList<>();
            charList.addAll(charSet);
            ItemStack result = ItemHandler.deserializeItem(configRecipe, recipeConfig);
            NamespacedKey namespacedKey = new NamespacedKey(plugin, configRecipe);
            ShapedRecipe recipe = new ShapedRecipe(namespacedKey, result);
            int varInt = charList.size();
            recipe.shape(layout.get(0),layout.get(1),layout.get(2));
            for (String s : charList) {
                System.out.println(s);
                String name = recipeConfig.getString("Recipes.%recipe%.Shaped.Items.%item%".replace("%recipe%", configRecipe).replace("%item%", s));
                assert name != null;
                Material material = Material.getMaterial(name);
                char character = s.charAt(0);
                System.out.println(character);
                assert material != null;
                recipe.setIngredient(character, material);
            }



            return recipe;
        }

        return null;
    }

    public static List<ShapedRecipe> getRecipeList(){
        List<ShapedRecipe> recipeList = new ArrayList<>();
        Set<String> recipes = recipeConfig.getConfigurationSection("Recipes").getKeys(false);
        for(String section:recipes) {
            System.out.println(section);
            ShapedRecipe recipe = createRecipe(section);
            recipeList.add(recipe);
        }


        return recipeList;
    }
}
