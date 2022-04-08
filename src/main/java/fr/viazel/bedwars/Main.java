package fr.viazel.bedwars;

import fr.viazel.bedwars.listeners.InventoryListener;
import fr.viazel.bedwars.listeners.KillListener;
import fr.viazel.bedwars.listeners.OnJoin;
import fr.viazel.bedwars.managers.GameState;
import fr.viazel.bedwars.managers.Player.PlayerManager;
import fr.viazel.bedwars.managers.Team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private static PlayerManager playerManager;
    private static TeamManager teamManager;

    @Override
    public void onEnable() {
        instance = this;
        playerManager = new PlayerManager();
        teamManager = new TeamManager();
        register();
        Bukkit.getWorld("world").setGameRuleValue("keepInventory", "true");
        Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"),44, 26, 63), EntityType.SKELETON);
        GameState.setState(GameState.LOBBY);
    }

    @Override
    public void onDisable() {
        for (Player pls : Bukkit.getOnlinePlayers()){
            pls.kickPlayer("§7Le Serveur reload !\n " +
                    "\n " +
                    "§1Le meilleur développeur c'est Viazel !\n" +
                    "§bLe meilleur Builder c'est Entrax_tYto");
        }
    }

    private void register(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new OnJoin(), this);
        pm.registerEvents(new InventoryListener(), this);
        pm.registerEvents(new KillListener(), this);
    }

    public static TeamManager getTeamManager() {
        return teamManager;
    }

    public static Main getInstance() {
        return instance;
    }

    public static PlayerManager getPlayerManager() {
        return playerManager;
    }
}
