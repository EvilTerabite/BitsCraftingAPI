package me.evilterabite.bitscraftingapi.gui.crafting;

import me.evilterabite.bitscraftingapi.BitsCraftingAPI;
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
            ItemStack result = deserializeItem(configRecipe, recipeConfig);
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

    public static ItemStack deserializeItem(String item, FileConfiguration configuration) {
        String name = recipeConfig.getString("Items.%item%.name".replace("%item%", item));
        String itemName =  recipeConfig.getString("Items.%item%.item".replace("%item%", item));
        List<String> enchantList = recipeConfig.getStringList("Items.%item%.enchants".replace("%item%", item));
        int amount = recipeConfig.getInt("Items.%item%.amount".replace("%item%", item));
        List<String> lore = recipeConfig.getStringList("Items.%item%.lore".replace("%item%", item));
        assert itemName != null;
        ItemStack itemStack = new ItemStack(Objects.requireNonNull(Material.getMaterial(itemName)), amount);
        if(enchantList != null) {
            for (String rawEnchant : enchantList) {
                List<String> enchantSplit = Arrays.asList(rawEnchant.split(":"));
                String level = enchantSplit.get(1);
                String strEnchant = enchantSplit.get(0);
                Enchantment enchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(strEnchant));
                assert enchantment != null;
                itemStack.addUnsafeEnchantment(enchantment, Integer.parseInt(level));
            }
        }
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        assert name != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        if(lore != null) {
            meta.setLore(lore);
        }
        itemStack.setItemMeta(meta);
        return itemStack;
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
