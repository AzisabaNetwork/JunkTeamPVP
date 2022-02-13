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

    @SuppressWarnings("SuspiciousMethodCalls")
    @EventHandler
    public void onTeamPVP(EntityDamageByEntityEvent event){
        //ダメージを与えたエンティティやダメージを受けたエンティティを取得
        Entity entity = event.getEntity();
        Entity damage = event.getDamager();
        if (!(entity instanceof Player || damage instanceof Player)) return;
        //ダメージを与えたエンティティやダメージを受けたエンティティがプレイヤー以外なら処理を行わなずreturnする
        String worldName = plugin.config().getString("worldName");
        if (worldName == null || worldName.equals("Default")) return;
        //取得したワールドがjgTutorial以外は処理を行わずreturnする
        World world1 = entity.getWorld();
        World world2 = damage.getWorld();
        if (!world1.getName().equals(worldName) && !world2.getName().equals(worldName)) return;
        if (!(plugin.blueTeam.contains(entity) && plugin.redTeam.contains(damage)) && !(plugin.redTeam.contains(entity) && plugin.blueTeam.contains(damage))) event.setCancelled(true);
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        String worldName = plugin.config().getString("worldName");
        if (worldName == null || worldName.equals("Default")) return;
        if (player.getWorld().getName().contains(worldName)) {
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