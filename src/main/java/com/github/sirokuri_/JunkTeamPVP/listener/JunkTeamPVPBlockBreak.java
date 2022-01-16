package com.github.sirokuri_.JunkTeamPVP.listener;

import com.github.sirokuri_.JunkTeamPVP.JunkTeamPVP;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class JunkTeamPVPBlockBreak implements Listener {

    private final JunkTeamPVP plugin;


    public JunkTeamPVPBlockBreak(JunkTeamPVP junkTeamPVP) {
        this.plugin = junkTeamPVP;
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        //ブロックを壊したプレイヤー取得
        Player player = event.getPlayer();
        //壊されたブロックを取得
        Block block = event.getBlock();
        //取得したワールドがjgTutorial以外は処理を行わずreturnする
        World world = player.getWorld();
        if (!world.getName().equals("jgTutorial")) return;
        //壊されたブロックがダイヤモンド原石なら実行
        if(block.getType() == Material.EMERALD_ORE) {
            event.setCancelled(true);
            //StartTimerに似た処理なので説明割愛
            AtomicInteger countdownStarter = new AtomicInteger(20);
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            Runnable runnable = () -> {
                countdownStarter.getAndDecrement();
                if (countdownStarter.get() > 0) {
                    block.setType(Material.DIAMOND_ORE);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&l鉱石が再生成されました"));
                }
            };
            scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
        }
    }
}
