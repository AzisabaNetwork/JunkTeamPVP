package com.github.sirokuri_.JunkTeamPVP;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class JunkTeamPVPGuard implements Listener {
    private final JunkTeamPVP plugin;

    public JunkTeamPVPGuard(JunkTeamPVP junkTeamPVP){
        this.plugin = junkTeamPVP;
    }

    @EventHandler
    public void onTeamPVP(EntityDamageByEntityEvent event){
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        if (!(damager instanceof Player)) return;
        if (!(entity instanceof Player)) return;
        if (plugin.blueTeam.contains(damager) && plugin.blueTeam.contains(entity)) event.setCancelled(true);
        if (plugin.redTeam.contains(damager) && plugin.redTeam.contains(entity)) event.setCancelled(true);
    }
}
