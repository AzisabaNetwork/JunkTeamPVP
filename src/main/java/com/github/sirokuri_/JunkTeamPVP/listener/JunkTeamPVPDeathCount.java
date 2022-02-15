package com.github.sirokuri_.JunkTeamPVP.listener;

import com.github.sirokuri_.JunkTeamPVP.JunkTeamPVP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;


public class JunkTeamPVPDeathCount implements Listener {

    public final JunkTeamPVP plugin;

    public JunkTeamPVPDeathCount(JunkTeamPVP junkTeamPVP){
        this.plugin = junkTeamPVP;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        if (player == null) return;
        if (plugin.redTeam.contains(player)){
            plugin.blueTeamCount++;
            for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                if (plugin.redTeam.contains(players) || plugin.blueTeam.contains(players)) {
                    players.sendMessage(ChatColor.BLUE + "青チームが " + plugin.blueTeamCount + " ポイント");
                }
            }
        }else if (plugin.blueTeam.contains(player)){
            plugin.redTeamCount++;
            for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                if (plugin.redTeam.contains(players) || plugin.blueTeam.contains(players)) {
                    players.sendMessage(ChatColor.RED + "赤チームが " + plugin.redTeamCount + " ポイント");
                }
            }
        }
    }
}
