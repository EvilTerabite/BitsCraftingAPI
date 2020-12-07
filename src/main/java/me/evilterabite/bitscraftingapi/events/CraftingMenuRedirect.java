package me.evilterabite.bitscraftingapi.events;

import me.evilterabite.bitscraftingapi.gui.crafting.CraftingGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class CraftingMenuRedirect implements Listener {
    @EventHandler
    void onCraftingOpen(InventoryOpenEvent e) {
        InventoryType type = e.getInventory().getType();
        if(type == InventoryType.CRAFTING) {
            if (e.getPlayer() instanceof Player) {
                e.setCancelled(true);
                Player player = (Player) e.getPlayer();
                CraftingGUI.displayMenu(player);
            }
        }
    }
}
