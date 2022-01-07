package com.github.sirokuri_.JunkTeamPVP.listener;

import com.github.sirokuri_.JunkTeamPVP.JunkTeamPVP;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JunkTeamPVPStartTimer implements Listener {

    private final JunkTeamPVP plugin;

    public JunkTeamPVPStartTimer(JunkTeamPVP junkTeamPVP) {
        this.plugin = junkTeamPVP;
        startTimer();
    }

    public void startTimer() {
        plugin.task = new BukkitRunnable() {
            int countdownStarter = 20;
            BossBar bossBar = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "チーム対抗PVP : マッチ終了まで" + countdownStarter + " 秒", BarColor.PURPLE, BarStyle.SOLID);
            int matchPlayers = 1;
            @Override
            public void run() {
                if (plugin.redTeam.size() + plugin.blueTeam.size() == matchPlayers) {
                    plugin.getServer().broadcastMessage(ChatColor.DARK_PURPLE + "エントリーが" + matchPlayers + "以上の為ゲームを開始します");
                    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            countdownStarter--;
                            if (countdownStarter > 0) {
                                for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                                    bossBar.addPlayer(players);
                                }
                                bossBar.setTitle(ChatColor.DARK_PURPLE + "チーム対抗PVP : マッチ終了まで" + countdownStarter  + " 秒");
                            } else if (countdownStarter == 0) {
                                plugin.getServer().broadcastMessage(ChatColor.DARK_PURPLE + "試合時間が0になったので試合を終了します!");
                                for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                                    bossBar.removePlayer(players);
                                }
                                scheduler.shutdown();
                                plugin.redTeam.clear();
                                plugin.blueTeam.clear();
                                plugin.onlinePlayers.clear();
                            }
                            if(countdownStarter <= 5){
                                for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                                    players.getWorld().playSound(players.getLocation(),Sound.BLOCK_NOTE_BLOCK_HAT,1, 1);
                                }
                            }
                        }
                    };
                    scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
                }
            }
        };
        plugin.task.runTaskTimer(plugin, 0, 1000);
    }
}