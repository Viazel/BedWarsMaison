package fr.viazel.bedwars.managers;

import fr.viazel.bedwars.Main;
import fr.viazel.bedwars.managers.Team.TeamInfo;
import fr.viazel.bedwars.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class GameManager {

    public static void start(){
        GameState.setState(GameState.GAME);
        ItemBuilder iron_sword = new ItemBuilder(Material.IRON_SWORD).setInfinityDurability();
        ItemBuilder yellow_leggings = new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.YELLOW).setInfinityDurability();
        ItemBuilder yellow_boots = new ItemBuilder(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.YELLOW).setInfinityDurability();
        ItemBuilder blue_leggins = new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.BLUE).setInfinityDurability();
        ItemBuilder blue_boots = new ItemBuilder(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.BLUE).setInfinityDurability();
        ItemBuilder sandstone = new ItemBuilder(Material.SANDSTONE, 64);
        for (Player pls : Bukkit.getOnlinePlayers()){
            pls.getInventory().clear();
            pls.getInventory().setItem(0, iron_sword.toItemStack());
            for (int i = 1; i <= 8; i++){
                pls.getInventory().setItem(i, sandstone.toItemStack());
            }
            TeamInfo playerInfo = Main.getTeamManager().getTeamInfo(pls);
            if (playerInfo.isYellow()){
                pls.getInventory().setLeggings(yellow_leggings.toItemStack());
                pls.getInventory().setBoots(yellow_boots.toItemStack());
            }
            if (playerInfo.isBlue()){
                pls.getInventory().setLeggings(blue_leggins.toItemStack());
                pls.getInventory().setBoots(blue_boots.toItemStack());
            }
            pls.teleport(Main.getPlayerManager().getPlayerInfo(pls).getSpawn());
        }
    }

    public static void end(Player player){
        GameState.setState(GameState.END);
        if (player == null){
            ChatUtilities.broadcast("Aucun gagnant n'a été trouvé !");
            return;
        }
        for (Player pls : Bukkit.getOnlinePlayers()){
            if (pls != player){
                pls.setGameMode(GameMode.SPECTATOR);
                pls.teleport(player);
            }
            pls.getInventory().clear();
        }
        ChatUtilities.broadcast("§a" + player.getName() + " a gagné la partie !");
        player.setGameMode(GameMode.CREATIVE);
    }

}
