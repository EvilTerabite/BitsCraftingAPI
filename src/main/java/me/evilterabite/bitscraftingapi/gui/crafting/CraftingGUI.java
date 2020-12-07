package me.evilterabite.bitscraftingapi.gui.crafting;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.mask.RecipeMask;
import org.ipvp.canvas.type.ChestMenu;


public class CraftingGUI implements Listener {


    public static Menu getMenu(){
        return ChestMenu.builder(6)
                .title(ChatColor.RED + "Crafting").build();
    }

    public static void displayMenu(Player player) {
        Menu menu = getMenu();
        Mask mask = RecipeMask.builder(menu)
                .item('1', new ItemStack(Material.BLACK_STAINED_GLASS_PANE))
                .item('i', new ItemStack(Material.RED_STAINED_GLASS_PANE))
                .pattern("111111111")
                .pattern("100011111")
                .pattern("100011011")
                .pattern("100011111")
                .pattern("111111111")
                .pattern("iiii1iiii")
                .build();
        mask.apply(menu);
        menu.open(player);
    }
}
