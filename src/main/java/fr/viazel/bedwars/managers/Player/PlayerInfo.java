package fr.viazel.bedwars.managers.Player;

import fr.viazel.bedwars.Main;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PlayerInfo {
    private int kill;
    private int death;
    private boolean bed;
    private Block bedBlock;
    private Player player;
    private Location spawn;

    public PlayerInfo(Player player) {
        this.player = player;
        this.kill = 0;
        this.death = 0;
        this.bed = true;
        Main.getPlayerManager().setPlayerInfo(player, this);
    }

    public boolean isBed() {
        return bed;
    }

    public void setBed(boolean bed) {
        this.bed = bed;
    }

    public int getKill() {
        return kill;
    }

    public void addKill(){
        this.kill = this.kill + 1;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getDeath() {
        return death;
    }

    public void addDeath() {
        this.death = this.death + 1;
    }

    public Block getBedBlock() {
        return bedBlock;
    }

    public void setBedBlock(Block bedBlock) {
        this.bedBlock = bedBlock;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }
}
