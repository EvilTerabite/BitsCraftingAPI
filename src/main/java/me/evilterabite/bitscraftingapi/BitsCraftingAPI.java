package me.evilterabite.bitscraftingapi;

import me.evilterabite.bitscraftingapi.files.recipes.RecipeConfig;
import me.evilterabite.bitscraftingapi.gui.crafting.RecipeHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class BitsCraftingAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "BitsCrafting Started.");
        setupConfigs();
        setupRecipes();
    }

    @Override
    public void onDisable() {

    }


    public void setupConfigs(){
        saveResource("recipes.yml", false);
        saveDefaultConfig();
        RecipeConfig.setup();
    }

    public void setupRecipes(){
        List<ShapedRecipe> recipeList = RecipeHandler.getRecipeList();
        for(ShapedRecipe recipe:recipeList) {
            Bukkit.addRecipe(recipe);
        }
    }
}
