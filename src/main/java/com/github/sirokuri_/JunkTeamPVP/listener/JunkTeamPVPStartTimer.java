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
        /*コンストラクタ内の処理
        ここでは毎試合ゲーム起動する処理を行っている
         */
        this.plugin = junkTeamPVP;
        plugin.task = new BukkitRunnable() {
            @Override
            public void run() {
                startTimer();
            }
        };
        plugin.task.runTaskTimer(plugin, 0, 1000);
    }

    //ゲーム(PVP)の起動部分
    public void startTimer() {
        //ゲームの秒数を変数として作成
        AtomicInteger countdownStarter = new AtomicInteger(20);
        //ボスバー作成
        BossBar bossBar = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "チーム対抗PVP : マッチ終了まで" + countdownStarter + " 秒", BarColor.PURPLE, BarStyle.SOLID);
        //試合開始人数の指定：現状はデバッグ用に一名で起動する様にしている
        int matchPlayers = 1;
        //試合にエントリーしたプレイヤーが赤チームか青チームに入っていて試合開始人数と同じになったら試合を開始する
        if (plugin.redTeam.size() + plugin.blueTeam.size() == matchPlayers) {
            plugin.getServer().broadcastMessage(ChatColor.DARK_PURPLE + "[JunkTeamPVP] エントリーが" + matchPlayers + "以上の為ゲームを開始します");
            //スケジューラーを変数化する
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            //スケジューラーを開始する
            Runnable runnable = () -> {
                countdownStarter.getAndDecrement();
                //試合のカウントダウンが0秒以上の時処理を実行
                if (countdownStarter.get() > 0) {
                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        //ボスバーにプレイヤーを追加する
                        bossBar.addPlayer(players);
                    }
                    //カウントダウンをボスバーに反映する
                    bossBar.setTitle(ChatColor.DARK_PURPLE + "チーム対抗PVP : マッチ終了まで" + countdownStarter  + " 秒");
                    //試合のカウントダウンが0秒の時処理を実行
                } else if (countdownStarter.get() == 0) {
                    plugin.getServer().broadcastMessage(ChatColor.DARK_PURPLE + "[JunkTeamPVP] 試合時間が0になったので試合を終了します!");
                    //スケジューラーを閉じる
                    scheduler.shutdown();
                    //ボスバー削除
                    bossBar.removeAll();
                    //プラグイン起動時に作ったリストを削除
                    plugin.onlinePlayers.clear();
                    plugin.redTeam.clear();
                    plugin.blueTeam.clear();
                }
            };
            scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
        }
    }
}