package fr.viazel.bedwars.managers;

import org.bukkit.scheduler.BukkitRunnable;

public class TimeOut extends BukkitRunnable {

    private int time = 10;
    @Override
    public void run() {
        if (time == 10){
            ChatUtilities.broadcast("La partie va commencer dans §a10s");
        }
        if (time <= 5 && !(time == 0)){
            ChatUtilities.broadcast("La partie commence dans §a" + time + "s");
        }
        if (time == 0){
            cancel();
            GameManager.start();
            ChatUtilities.broadcast("La partie commence !");
        }
        time--;
    }
}
