package fr.viazel.bedwars.listeners;

import fr.viazel.bedwars.managers.Player.ItemManager;
import fr.viazel.bedwars.managers.Player.PlayerInfo;
import fr.viazel.bedwars.managers.Team.TeamInfo;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class OnJoin implements Listener {

    private World world = Bukkit.getWorld("world");
    private Location lobby = new Location(world, 44, 64, 63, 90F, 0F);

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        PlayerInfo playerInfo = new PlayerInfo(p);
        TeamInfo teamInfo = new TeamInfo(p);
        teamInfo.none();
        p.getInventory().clear();
        p.getInventory().setLeggings(null);
        p.getInventory().setChestplate(null);
        p.getInventory().setBoots(null);
        p.getInventory().setHelmet(null);
        p.getInventory().setItem(4, ItemManager.getStartItem());
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.teleport(lobby);
        setWorldSpawn();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        e.setFormat(e.getPlayer().getPlayerListName() + "§f: " + e.getMessage());
    }

    private void setWorldSpawn(){
        world.getSpawnLocation().setX(lobby.getX());
        world.getSpawnLocation().setY(lobby.getY());
        world.getSpawnLocation().setZ(lobby.getZ());
        world.getSpawnLocation().setYaw(lobby.getYaw());
        world.getSpawnLocation().setPitch(lobby.getPitch());
    }
}