package fr.viazel.bedwars.managers.Player;

import fr.viazel.bedwars.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemManager {
    private static String getNameStartItem;
    public static ItemStack getStartItem(){
        ItemBuilder itemBuilder = new ItemBuilder(Material.COMPASS).setName("§aChoisi ta team !");
        getNameStartItem = itemBuilder.toItemStack().getItemMeta().getDisplayName();
        return itemBuilder.toItemStack();
    }
}
