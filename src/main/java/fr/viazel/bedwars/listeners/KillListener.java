package fr.viazel.bedwars.listeners;

import fr.viazel.bedwars.Main;
import fr.viazel.bedwars.managers.ChatUtilities;
import fr.viazel.bedwars.managers.GameManager;
import fr.viazel.bedwars.managers.GameState;
import fr.viazel.bedwars.managers.Player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class KillListener implements Listener {
    @EventHandler
    public void onKill(EntityDamageByEntityEvent e){
        Player p = (Player) e.getEntity();
        if (GameState.isState(GameState.LOBBY)){
            e.setCancelled(true);
        }
        p.getItemInHand().setDurability((short) 0);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        if (GameState.isState(GameState.GAME)){
            e.setDeathMessage(null);
            ChatUtilities.broadcast("§a" + p.getName() + " §fa été EZ par §a" + p.getKiller().getName());
            for (Player pls : Bukkit.getOnlinePlayers()){
                if (pls != p){
                    Main.getPlayerManager().getPlayerInfo(pls).addKill();
                }
            }
            Main.getPlayerManager().getPlayerInfo(p).addDeath();
            for (Player pls : Bukkit.getOnlinePlayers()){
                pls.sendMessage("§aVous avez §b" + Main.getPlayerManager().getPlayerInfo(pls).getKill() + " kills");
                pls.sendMessage("§aVous avez §b" + Main.getPlayerManager().getPlayerInfo(pls).getDeath() + " morts");
            }
            if (!Main.getPlayerManager().getPlayerInfo(p).isBed()){
                for (Player pls : Bukkit.getOnlinePlayers()){
                    if (pls != p){
                        GameManager.end(pls);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        PlayerInfo playerInfo = Main.getPlayerManager().getPlayerInfo(p);
        if (playerInfo.isBed()){
            e.setRespawnLocation(playerInfo.getSpawn());
        } else {
            p.setGameMode(GameMode.SPECTATOR);
            for (Player pls : Bukkit.getOnlinePlayers()){
                if (pls != p){
                    e.setRespawnLocation(pls.getLocation());
                }
            }
        }
    }

    @EventHandler
    public void onbreak(PlayerItemBreakEvent e){
        Player p = e.getPlayer();
        if (GameState.isState(GameState.GAME)){
            ItemStack item = e.getBrokenItem();
            item.setDurability((short) 0);
            p.getInventory().setItem(0, item);
        }
    }
}
