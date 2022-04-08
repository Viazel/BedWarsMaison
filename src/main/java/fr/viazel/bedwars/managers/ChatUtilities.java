package fr.viazel.bedwars.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatUtilities {
    private ChatUtilities(){

    }

    public static void broadcast(String name){
        for (Player pls : Bukkit.getOnlinePlayers()){
            pls.sendMessage("§f[§cBedwars§f] §b" + name);
        }
    }
}
