package com.github.sirokuri_.JunkTeamPVP.listener;

import com.github.sirokuri_.JunkTeamPVP.JunkTeamPVP;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;


public class JunkTeamPVPDeathCount implements Listener {

    public final JunkTeamPVP plugin;

    public JunkTeamPVPDeathCount(JunkTeamPVP junkTeamPVP){
        //コンストラクタ内の処理
        this.plugin = junkTeamPVP;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        if (player == null) return;
        World world = player.getLocation().getWorld();
        if (world == null) return;
        if (!world.getName().equals("jgTutorial")) return;
        if (plugin.redTeam.contains(player)){
            plugin.blueTeamDeathCount++;
            Bukkit.broadcastMessage("" + plugin.blueTeamDeathCount);
        }else if (plugin.blueTeam.contains(player)){
            plugin.redTeamDeathCount++;
            Bukkit.broadcastMessage("" + plugin.redTeamDeathCount);
        }
    }
}
