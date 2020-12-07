package me.evilterabite.bitscraftingapi.files.recipes;

import me.evilterabite.bitscraftingapi.BitsCraftingAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class RecipeConfig {

    private static File file;
    private static FileConfiguration playerFile;

    public static void setup(){
        Plugin plugin = BitsCraftingAPI.getPlugin(BitsCraftingAPI.class);
        file = new File(plugin.getDataFolder(), "recipes.yml");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException ignored) {

            }
        }
        playerFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return playerFile;
    }

    public static void save(){
        try{
            playerFile.save(file);
        } catch (IOException e){
            Bukkit.getLogger().severe("Could not save recipes.yml, this is a serious issue, please report it!");
        }
    }

    public static void reload(){
        playerFile = YamlConfiguration.loadConfiguration(file);
    }
}