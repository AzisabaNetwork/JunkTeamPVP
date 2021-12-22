package com.github.sirokuri_.JunkTeamPVP;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class JunkTeamPVP extends JavaPlugin implements Listener {

    List<Player> onlinePlayers = new ArrayList<Player>();
    List<Player> redTeam = new ArrayList<Player>();
    List<Player> blueTeam = new ArrayList<Player>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new JunkTeamPVPJoin(this), this);
        Bukkit.getPluginManager().registerEvents(new JunkTeamPVPGuard(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
