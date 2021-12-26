package com.github.sirokuri_.JunkTeamPVP;

import com.github.sirokuri_.JunkTeamPVP.listener.JunkTeamPVPGuard;
import com.github.sirokuri_.JunkTeamPVP.listener.JunkTeamPVPJoin;
import com.github.sirokuri_.JunkTeamPVP.listener.JunkTeamPVPStartTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class JunkTeamPVP extends JavaPlugin {

    public List<Player> onlinePlayers = new ArrayList<Player>();
    public List<Player> redTeam = new ArrayList<Player>();
    public List<Player> blueTeam = new ArrayList<Player>();
    public BukkitRunnable task = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new JunkTeamPVPJoin(this), this);
        Bukkit.getPluginManager().registerEvents(new JunkTeamPVPGuard(this), this);
        Bukkit.getPluginManager().registerEvents(new JunkTeamPVPStartTimer(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
