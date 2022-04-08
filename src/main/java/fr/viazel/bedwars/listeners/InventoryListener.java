package fr.viazel.bedwars.listeners;

import fr.viazel.bedwars.Main;
import fr.viazel.bedwars.managers.ChatUtilities;
import fr.viazel.bedwars.managers.GameState;
import fr.viazel.bedwars.managers.Player.PlayerInfo;
import fr.viazel.bedwars.managers.Team.TeamInfo;
import fr.viazel.bedwars.managers.TimeOut;
import fr.viazel.bedwars.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class InventoryListener implements Listener {
    @EventHandler
    public void onInventoryClicked(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (p.getGameMode().equals(GameMode.CREATIVE)){
            return;
        }

        if (GameState.isState(GameState.LOBBY)){
            e.setCancelled(true);
            p.closeInventory();
            TimeOut timeOut = new TimeOut();
            switch (item.getItemMeta().getDisplayName()){
                case "§1Bleu":
                    for (Player pls : Bukkit.getOnlinePlayers()){
                        if (Main.getTeamManager().getTeamInfo(pls).isBlue()){
                            if (pls == p){
                                p.sendMessage("§cVous êtes déja dans cette team !");
                                return;
                            }
                            p.sendMessage("§cCette team est déja prise !");
                            return;
                        }
                    }
                    Main.getTeamManager().getTeamInfo(p).setBlue();
                    ChatUtilities.broadcast("§a" + p.getName() + " §fa rejoint l'équipe §1Bleu §f(§d" + Main.getTeamManager().getPlayernumber() + "§f/§d2§f)");
                    break;

                case "§eJaune":
                    for (Player pls : Bukkit.getOnlinePlayers()){
                        if (Main.getTeamManager().getTeamInfo(pls).isYellow()){
                            if (pls == p){
                                p.sendMessage("§cVous êtes déja dans cette team !");
                                return;
                            }
                            p.sendMessage("§cCette team est déja prise !");
                            return;
                        }
                    }
                    Main.getTeamManager().getTeamInfo(p).setYellow();
                    ChatUtilities.broadcast("§a" +p.getName() + " §fa rejoint l'équipe §eJaune §f(§d" + Main.getTeamManager().getPlayernumber() + "§f/§d2§f)");
                    break;

                case "§cRien":
                    if (!Main.getTeamManager().getTeamInfo(p).isBlue() && !Main.getTeamManager().getTeamInfo(p).isYellow()){
                        p.sendMessage("§cVous êtes déja dans cette team !");
                        return;
                    }
                    Main.getTeamManager().getTeamInfo(p).none();
                    Main.getTeamManager().removePlayerNumber();
                    ChatUtilities.broadcast("§a" + p.getName() + " §fa rejoint l'équipe §cRien §f(§d" + Main.getTeamManager().getPlayernumber() + "§f/§d2§f)");
                    break;

                default: break;
            }
            if (Main.getTeamManager().getPlayernumber() == 2){
                timeOut.runTaskTimer(Main.getInstance(), 0, 20);
            }
        }else if (GameState.isState(GameState.GAME)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        ItemStack itemStack = e.getItem();
        if (p.getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        if (GameState.isState(GameState.LOBBY)){
            e.setCancelled(true);
            p.closeInventory();
            if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {return;}
            if (itemStack.getItemMeta().getDisplayName().equals("§aChoisi ta team !")){
                Inventory inv = Bukkit.createInventory(null, 27, "§aChoisi ta team !");
                ItemBuilder blue = new ItemBuilder(Material.WOOL).setWoolColor(DyeColor.BLUE).setName("§1Bleu");
                ItemBuilder yellow = new ItemBuilder(Material.WOOL).setWoolColor(DyeColor.YELLOW).setName("§eJaune");
                ItemBuilder none = new ItemBuilder(Material.BARRIER).setName("§cRien");
                inv.setItem(12, blue.toItemStack());
                inv.setItem(14, yellow.toItemStack());
                inv.setItem(22, none.toItemStack());
                p.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if (p.getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        if (GameState.isState(GameState.LOBBY)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if (GameState.isState(GameState.END)){
            e.setCancelled(true);
        }
        if (p.getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        if (GameState.isState(GameState.GAME)){
            if (e.getBlock().getType().equals(Material.BED_BLOCK)){
                TeamInfo teamInfo = Main.getTeamManager().getTeamInfo(p);
                PlayerInfo playerInfo = Main.getPlayerManager().getPlayerInfo(p);
                Block block = e.getBlock();
                Block bedblock = playerInfo.getBedBlock();
                Block bedblocktop = new Location(bedblock.getWorld(), bedblock.getX() + 1, bedblock.getY(), bedblock.getZ()).getBlock();
                Block bedblocktopyellow = new Location(bedblock.getWorld(), bedblock.getX() - 1, bedblock.getY(), bedblock.getZ()).getBlock();

                if (block.equals(bedblock) || block.equals(bedblocktop) || block.equals(bedblocktopyellow)){
                    e.setCancelled(true);
                    p.sendMessage("§cVous ne pouvez pas casser votre propre lit !");
                    return;
                }
                p.sendMessage("§a§lVous avez cassé le lit de l'équipe adverse !");
                for (Player pls : Bukkit.getOnlinePlayers()){
                    if (teamInfo.isBlue()){
                        if (Main.getTeamManager().getTeamInfo(pls).isYellow()){
                            pls.sendMessage("§c§lVotre lit a été cassé !");
                            Main.getPlayerManager().getPlayerInfo(pls).setBed(false);
                            return;
                        }
                    } else if (teamInfo.isYellow()){
                        if (Main.getTeamManager().getTeamInfo(pls).isBlue()){
                            pls.sendMessage("§c§lVotre lit a été cassé !");
                            Main.getPlayerManager().getPlayerInfo(pls).setBed(false);
                            return;
                        }
                    }
                }
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        if (GameState.isState(GameState.LOBBY)){
            e.setCancelled(true);
            return;
        }
        if (GameState.isState(GameState.END)){
            e.setCancelled(true);
            return;
        }
        Block block = e.getBlock();
        e.getPlayer().getItemInHand().setAmount(64);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                setWool(DyeColor.ORANGE, block);
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        setWool(DyeColor.RED, block);
                        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                block.setType(Material.AIR);
                            }
                        }, 20);
                    }
                }, 20);
            }
        }, (20 * 3));
    }

    public void setWool(DyeColor dyeColor, Block block) {
        block.setType(Material.WOOL);
        BlockState blockState = block.getState();
        Wool wool = (Wool) blockState.getData();
        wool.setColor(dyeColor);
        blockState.update();
    }
}