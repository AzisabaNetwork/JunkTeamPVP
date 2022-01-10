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
import java.util.concurrent.atomic.AtomicInteger;

public class JunkTeamPVPStartTimer implements Listener {

    private final JunkTeamPVP plugin;

    public JunkTeamPVPStartTimer(JunkTeamPVP junkTeamPVP) {
        this.plugin = junkTeamPVP;
        plugin.task = new BukkitRunnable() {
            @Override
            public void run() {
                startTimer();
            }
        };
        plugin.task.runTaskTimer(plugin, 0, 1000);
    }

    public void startTimer() {
        AtomicInteger countdownStarter = new AtomicInteger(20);
        BossBar bossBar = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "チーム対抗PVP : マッチ終了まで" + countdownStarter + " 秒", BarColor.PURPLE, BarStyle.SOLID);
        int matchPlayers = 1;
        if (plugin.redTeam.size() + plugin.blueTeam.size() == matchPlayers) {
            plugin.getServer().broadcastMessage(ChatColor.DARK_PURPLE + "エントリーが" + matchPlayers + "以上の為ゲームを開始します");
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            Runnable runnable = () -> {
                countdownStarter.getAndDecrement();
                if (countdownStarter.get() > 0) {
                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        bossBar.addPlayer(players);
                    }
                    bossBar.setTitle(ChatColor.DARK_PURPLE + "チーム対抗PVP : マッチ終了まで" + countdownStarter  + " 秒");
                } else if (countdownStarter.get() == 0) {
                    plugin.getServer().broadcastMessage(ChatColor.DARK_PURPLE + "試合時間が0になったので試合を終了します!");
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