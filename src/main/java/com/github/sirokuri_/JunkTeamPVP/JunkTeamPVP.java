package com.github.sirokuri_.JunkTeamPVP;

import com.github.sirokuri_.JunkTeamPVP.Command.JunkTeamPVPCommand;
import com.github.sirokuri_.JunkTeamPVP.listener.*;
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
        // プラグイン読み込み時に起動するクラス
        getServer().getPluginManager().registerEvents(new JunkTeamPVPJoin(this), this);
        getServer().getPluginManager().registerEvents(new JunkTeamPVPGuard(this), this);
        getServer().getPluginManager().registerEvents(new JunkTeamPVPStartTimer(this), this);
        getServer().getPluginManager().registerEvents(new JunkTeamPVPBlockBreak(this), this);
        getCommand("jtPVP").setExecutor(new JunkTeamPVPCommand(this));
    }

    @Override
    public void onDisable() {
        /* プラグイン無効化時に行う処理
        現状は使わないので無機能
         */
    }
}