package fr.viazel.bedwars.managers.Player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private Map<Player, PlayerInfo> playerInfo;

    public PlayerManager() {
        this.playerInfo = new HashMap<>();
    }

    public PlayerInfo getPlayerInfo(Player player){
        return playerInfo.get(player);
    }

    public void setPlayerInfo(Player player, PlayerInfo playerInfo){
        if (this.playerInfo.containsKey(player)){
            return;
        }
        this.playerInfo.put(player, playerInfo);
    }
}
