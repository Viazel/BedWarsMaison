package fr.viazel.bedwars.managers.Team;

import fr.viazel.bedwars.managers.ChatUtilities;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TeamManager {
    private Map<Player, TeamInfo> teamInfoMap;
    private int playernumber;

    public TeamManager(){
        this.teamInfoMap = new HashMap<>();
        this.playernumber = 0;
    }

    public void setTeamInfo(Player player, TeamInfo teamInfo){
        if (this.teamInfoMap.containsKey(player)){
            return;
        }
        this.teamInfoMap.put(player, teamInfo);
    }

    public TeamInfo getTeamInfo(Player player){
        if (!this.teamInfoMap.containsKey(player)){
            return null;
        }
        return this.teamInfoMap.get(player);
    }

    public int getPlayernumber() {
        return playernumber;
    }

    public void addPlayernumber() {
        this.playernumber = this.playernumber + 1;
    }

    public void removePlayerNumber(){
        this.playernumber = this.playernumber - 1;
    }
}
