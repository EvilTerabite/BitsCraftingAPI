package me.evilterabite.bitscraftingapi.api;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemHandler {
    public static ItemStack deserializeItem(String item, FileConfiguration configuration) {
        String name = configuration.getString("Items.%item%.name".replace("%item%", item));
        String itemName =  configuration.getString("Items.%item%.item".replace("%item%", item));
        List<String> enchantList = configuration.getStringList("Items.%item%.enchants".replace("%item%", item));
        int amount = configuration.getInt("Items.%item%.amount".replace("%item%", item));
        List<String> lore = configuration.getStringList("Items.%item%.lore".replace("%item%", item));
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


    public static void serializeItem(String configName, ItemStack item, FileConfiguration configuration) {
        Map<Enchantment, Integer> enchantmentMap = item.getEnchantments();
        List<Enchantment> enchantments = new ArrayList<>(enchantmentMap.keySet());
        List<String> enchantStrings = new ArrayList<>();
        for(Enchantment enchantment : enchantments) {
            String name = String.valueOf(enchantment.getKey());
            int level = enchantmentMap.get(enchantment);
            String compiledEnchantment = name + ":" + level;
            enchantStrings.add(compiledEnchantment);
        }

        ItemMeta meta = item.getItemMeta();
        configuration.set("Items.%item%.enchants".replace("%item%", configName), enchantStrings);
        assert meta != null;
        configuration.set("Items.%item%.name".replace("%item%", configName), meta.getDisplayName());
        configuration.set("Items.%item%.item".replace("%item%", configName), String.valueOf(item.getType()));
        configuration.set("Items.%item%.amount".replace("%item%", configName), item.getAmount());
        configuration.set("Items.%item%.lore".replace("%item%", configName), meta.getLore());
    }

    public static void serEnchants(ItemStack item){
        Map<Enchantment, Integer> enchantmentMap = item.getEnchantments();
        List<Enchantment> enchantments = new ArrayList<>(enchantmentMap.keySet());
        List<String> enchantStrings = new ArrayList<>();
        for(Enchantment enchantment : enchantments) {
            String name = String.valueOf(enchantment.getKey());
            int level = enchantmentMap.get(enchantment);
            StringBuilder compiledEnchantment = new StringBuilder(name);
            compiledEnchantment.append(":");
            compiledEnchantment.append(level);
            enchantStrings.add(compiledEnchantment.toString());
        }
    }


    /*public static ShapedRecipe createRecipe(String configRecipe) {
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
    }*/
}
