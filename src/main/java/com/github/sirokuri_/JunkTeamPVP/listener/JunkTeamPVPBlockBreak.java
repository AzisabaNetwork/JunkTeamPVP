package com.github.sirokuri_.JunkTeamPVP.listener;

import com.github.sirokuri_.JunkTeamPVP.JunkTeamPVP;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class JunkTeamPVPBlockBreak implements Listener {

    private final JunkTeamPVP plugin;


    public JunkTeamPVPBlockBreak(JunkTeamPVP junkTeamPVP) {
        this.plugin = junkTeamPVP;
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(block.getType() == Material.EMERALD_ORE) {
            event.setCancelled(true);
            block.setType(Material.DIAMOND_ORE);
            player.sendMessage("鉱石が再生成されました");
        }
    }
}
