package com.github.sirokuri_.JunkTeamPVP.listener;

import com.github.sirokuri_.JunkTeamPVP.JunkTeamPVP;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;

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
        //取得したワールドがjgTutorial以外は処理を行わずreturnする
        World world1 = entity.getWorld();
        World world2 = damage.getWorld();
        if (!world1.getName().equals("jgTutorial") && !world2.getName().equals("jgTutorial")) return;
        if (!(plugin.blueTeam.contains(entity) && plugin.redTeam.contains(damage)) && !(plugin.redTeam.contains(entity) && plugin.blueTeam.contains(damage))) event.setCancelled(true);
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        if (player.getWorld().getName().contains("jgTutorial")) {
            player.sendMessage("ゲームが終了するまで再度エントリーができません");
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
            player.getInventory().setChestplate(new ItemStack(Material.AIR));
            player.getInventory().setLeggings(new ItemStack(Material.AIR));
            player.getInventory().setBoots(new ItemStack(Material.AIR));
            player.getInventory().remove(plugin.jgWeapon());
        }else {
            plugin.blueTeam.remove(player);
            plugin.redTeam.remove(player);
        }
    }
}
