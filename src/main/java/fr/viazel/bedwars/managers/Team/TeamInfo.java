package fr.viazel.bedwars.managers.Team;

import fr.viazel.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

        public class TeamInfo {
            private boolean blue;
            private boolean yellow;
            private Player player;

            public TeamInfo(Player player){
                this.player = player;
                this.blue = false;
                this.yellow = false;
                Main.getTeamManager().setTeamInfo(player, this);
            }

            public void setBlue() {
                if (isYellow()){
                    this.yellow = false;
                    Main.getTeamManager().removePlayerNumber();
                }
        this.blue = true;
        Main.getTeamManager().addPlayernumber();
        player.setDisplayName("§1" + player.getName());
        player.setPlayerListName("§f[§1Bleu§f] §1" + player.getName());
        Location location = new Location(Bukkit.getWorld("world"),73, 25, 63);
        Block block = location.getBlock();
        Main.getPlayerManager().getPlayerInfo(player).setBedBlock(block);
        Location spawn = new Location(Bukkit.getWorld("world"), 84, 24, 63, 90f, 0f);
        Main.getPlayerManager().getPlayerInfo(player).setSpawn(spawn);
    }

    public void setYellow() {
        if (isBlue()){
            this.blue = false;
            Main.getTeamManager().removePlayerNumber();
        }
        Main.getTeamManager().addPlayernumber();
        this.yellow = true;
        player.setDisplayName("§e" + player.getName());
        player.setPlayerListName("§f[§eJaune§f] §e" + player.getName());
        Block block = new Location(Bukkit.getWorld("world"), 14, 25, 63).getBlock();
        Main.getPlayerManager().getPlayerInfo(player).setBedBlock(block);
        Location spawn = new Location(Bukkit.getWorld("world"), 3, 24, 63, -90f, 0f);
        Main.getPlayerManager().getPlayerInfo(player).setSpawn(spawn);
    }

    public void none(){
        this.blue = false;
        this.yellow = false;
        player.setDisplayName("§c" + player.getName());
        player.setPlayerListName("§f[§cRien§f] §c" + player.getName());
    }

    public boolean isYellow() {
        return yellow;
    }

    public boolean isBlue() {
        return blue;
    }

}