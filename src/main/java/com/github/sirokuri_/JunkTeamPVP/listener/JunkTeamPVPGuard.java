package com.github.sirokuri_.JunkTeamPVP.listener;

import com.github.sirokuri_.JunkTeamPVP.JunkTeamPVP;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class JunkTeamPVPGuard implements Listener {
    private final JunkTeamPVP plugin;

    public JunkTeamPVPGuard(JunkTeamPVP junkTeamPVP){
        //コンストラクタ内の処理
        this.plugin = junkTeamPVP;
    }

    @EventHandler
    public void onTeamPVP(EntityDamageByEntityEvent event){
        //ダメージを与えたエンティティやダメージを受けたエンティティを取得
        Entity entity = event.getEntity();
        Entity damage = event.getDamager();
        //ダメージを与えたエンティティやダメージを受けたエンティティがプレイヤー以外なら処理を行わなずreturnする
        if (!(damage instanceof Player)) return;
        if (!(entity instanceof Player)) return;
        //ダメージを与えたプレイヤーやダメージを受けたプレイヤーが青チームや赤チーム出なければダメージを無効化する
        if (plugin.blueTeam.contains(damage) && plugin.blueTeam.contains(entity)) event.setCancelled(true);
        if (plugin.redTeam.contains(damage) && plugin.redTeam.contains(entity)) event.setCancelled(true);
    }
}