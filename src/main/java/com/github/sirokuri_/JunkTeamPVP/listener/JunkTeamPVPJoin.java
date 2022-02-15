package com.github.sirokuri_.JunkTeamPVP.listener;

import com.github.sirokuri_.JunkTeamPVP.JunkTeamPVP;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class JunkTeamPVPJoin implements Listener {

    public final JunkTeamPVP plugin;

    public JunkTeamPVPJoin(JunkTeamPVP junkTeamPVP){
        this.plugin = junkTeamPVP;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        World world = player.getWorld();
        String worldName = plugin.config().getString("worldName");
        int matchPlayers = plugin.config().getInt("matchPlayers");
        if (worldName == null || worldName.equals("Default")) return;
        if (!world.getName().equals(worldName)) return;
        if (block == null) return;
        if ((event.getHand() != EquipmentSlot.HAND || event.getAction() == Action.LEFT_CLICK_BLOCK)) return;
        if (!(block.getType() == Material.OAK_WALL_SIGN)) return;
        Sign sign = (Sign) block.getState();
        String firstSignLine = sign.getLine(0);
        String secondSignLine = sign.getLine(1);
        if (!(firstSignLine.equals("[JunkTeamPVP]") && secondSignLine.equals("試合に参加する"))) return;
        if (!plugin.onlinePlayers.contains(player)) {
            plugin.onlinePlayers.add(player);
            if (plugin.onlinePlayers.size() % 2 == 0) {
                plugin.redTeam.add(player);
                ItemStack[] armor = new ItemStack [4];
                armor[0] = plugin.redTeamBoots();
                armor[1] = plugin.redTeamLeggings();
                armor[2] = plugin.redTeamChestPlate();
                armor[3] = plugin.redTeamHelmet();
                player.getInventory().setArmorContents(armor);
                player.getInventory().setItemInMainHand(plugin.jgRedWeapon());
                player.getInventory().addItem(plugin.jgWeapon1());
                player.getInventory().addItem(plugin.jgWeapon2());
                player.sendMessage(ChatColor.RED + "赤チームに参加しました");
                if (plugin.redTeam.size() + plugin.blueTeam.size() == matchPlayers) {
                    startTimer();
                }
            } else {
                plugin.blueTeam.add(player);
                ItemStack[] armor = new ItemStack [4];
                armor[0] = plugin.blueTeamBoots();
                armor[1] = plugin.blueTeamLeggings();
                armor[2] = plugin.blueTeamChestPlate();
                armor[3] = plugin.blueTeamHelmet();
                player.getInventory().setArmorContents(armor);
                player.getInventory().setItemInMainHand(plugin.jgBlueWeapon());
                player.getInventory().addItem(plugin.jgWeapon1());
                player.getInventory().addItem(plugin.jgWeapon2());
                player.sendMessage(ChatColor.BLUE + "青チームに参加しました");
            }
        } else if(plugin.redTeam.contains(player) || plugin.blueTeam.contains(player)){
            player.sendMessage(ChatColor.RED + "既にあなたはチームへ参加しています！");
        } else {
            player.sendMessage(ChatColor.RED + "マッチが終了するまで参加できません");
        }
        //デバッグ用の機能
        //player.sendMessage("" + plugin.blueTeam.contains(player) + "\n" + plugin.redTeam.contains(player) + "\n" + plugin.onlinePlayers.contains(player));
    }

    public void startTimer() {
        int gameTimer = plugin.config().getInt("gameTimer");
        int matchPlayers = plugin.config().getInt("matchPlayers");
        String worldName = plugin.config().getString("worldName");
        AtomicInteger countdownStarter = new AtomicInteger(gameTimer);
        BossBar bossBar = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "チーム対抗PVP : マッチ終了まで" + countdownStarter + " 秒", BarColor.PURPLE, BarStyle.SOLID);
        if (plugin.redTeam.size() + plugin.blueTeam.size() == matchPlayers) {
            for(Player p : Bukkit.getServer().getOnlinePlayers()){
                if (p.getWorld().getName().equals(worldName)){
                    p.sendMessage(ChatColor.DARK_PURPLE + "[JunkTeamPVP] エントリー数が " + matchPlayers + " 名以上の為ゲームを開始します");
                }
            }
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            Runnable runnable = () -> {
                countdownStarter.getAndDecrement();
                if (countdownStarter.get() > 0) {
                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        if (plugin.redTeam.contains(players) || plugin.blueTeam.contains(players)){
                            bossBar.addPlayer(players);
                        }
                    }
                    bossBar.setTitle(ChatColor.DARK_PURPLE + "チーム対抗PVP : マッチ終了まで" + countdownStarter  + " 秒");
                } else if (countdownStarter.get() == 0) {
                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        if (plugin.redTeam.contains(players) || plugin.blueTeam.contains(players)){
                            players.sendMessage(ChatColor.DARK_PURPLE + "[JunkTeamPVP] 試合時間が0になったので試合を終了します!");
                            players.getInventory().setHelmet(new ItemStack(Material.AIR));
                            players.getInventory().setChestplate(new ItemStack(Material.AIR));
                            players.getInventory().setLeggings(new ItemStack(Material.AIR));
                            players.getInventory().setBoots(new ItemStack(Material.AIR));
                            players.getInventory().remove(plugin.jgBlueWeapon());
                            players.getInventory().remove(plugin.jgRedWeapon());
                            players.getInventory().remove(plugin.jgWeapon1());
                            players.getInventory().remove(plugin.jgWeapon2());
                            if (worldName == null || worldName.equals("Default")) return;
                            if (!players.getWorld().getName().equals(worldName)) return;
                            if (plugin.redTeamCount > plugin.blueTeamCount){
                                players.sendTitle("" + ChatColor.RED + plugin.redTeamCount + ChatColor.DARK_PURPLE + " : " + ChatColor.BLUE + plugin.blueTeamCount,ChatColor.RED + "赤チームが勝利しました",10,70,20);
                            }else if (plugin.redTeamCount < plugin.blueTeamCount){
                                players.sendTitle("" + ChatColor.RED + plugin.redTeamCount + ChatColor.DARK_PURPLE + " : " + ChatColor.BLUE + plugin.blueTeamCount,ChatColor.BLUE + "青チームが勝利しました",10,70,20);
                            }else {
                                players.sendTitle("" + ChatColor.RED + plugin.redTeamCount + ChatColor.DARK_PURPLE + " : " + ChatColor.BLUE + plugin.blueTeamCount,ChatColor.DARK_PURPLE + "引き分けになりました",10,70,20);
                            }
                            if (players.getWorld().getName().equals(worldName)){
                                String hoverText1 = "チームPVPロビーへ戻る場合はこの文字をクリック!!";
                                BaseComponent[] hover1 = new ComponentBuilder(ChatColor.GREEN+ hoverText1).create();
                                HoverEvent hoverEvent1 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover1);
                                String command1 = "/jtPVP warp lobbySpawn";
                                ClickEvent clickEvent1 = new ClickEvent(ClickEvent.Action.RUN_COMMAND,command1);
                                BaseComponent[] message1 = new ComponentBuilder(ChatColor.UNDERLINE + hoverText1).event(hoverEvent1).event(clickEvent1).create();

                                String hoverText2 = "他のゲームを遊びたい場合はこの文字をクリック!!";
                                BaseComponent[] hover2 = new ComponentBuilder(ChatColor.GREEN + hoverText2).create();
                                HoverEvent hoverEvent2 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover2);
                                String command2 = "/jgselector";
                                ClickEvent clickEvent2 = new ClickEvent(ClickEvent.Action.RUN_COMMAND,command2);
                                BaseComponent[] message2 = new ComponentBuilder(ChatColor.UNDERLINE + hoverText2).event(hoverEvent2).event(clickEvent2).create();
                                players.sendMessage(ChatColor.GREEN + "[JunkTeamPVP] メニュー");
                                players.sendMessage("");
                                players.spigot().sendMessage(message1);
                                players.sendMessage("");
                                players.spigot().sendMessage(message2);
                            }
                        }
                    }
                    plugin.redTeamCount = 0;
                    plugin.blueTeamCount = 0;
                    scheduler.shutdown();
                    bossBar.removeAll();
                    plugin.onlinePlayers.clear();
                    plugin.redTeam.clear();
                    plugin.blueTeam.clear();
                }
            };
            scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
        }
    }
}