package com.github.sirokuri_.JunkTeamPVP.listener;

import com.github.sirokuri_.JunkTeamPVP.JunkTeamPVP;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;

public class JunkTeamPVPGuard implements Listener {
    private final JunkTeamPVP plugin;

    public JunkTeamPVPGuard(JunkTeamPVP junkTeamPVP){
        this.plugin = junkTeamPVP;
    }

    @EventHandler
    public void onTeamPVP(EntityDamageByEntityEvent event){
        Entity entity = event.getEntity();
        Entity damage = event.getDamager();
        if (!(damage instanceof Player || damage instanceof Arrow)) return;
        //ダメージを与えたエンティティがプレイヤー以外なら処理を行わなずreturnする
        String worldName = plugin.config().getString("worldName");
        if (worldName == null || worldName.equals("Default")) return;
        World world1 = entity.getWorld();
        World world2 = damage.getWorld();
        if (!world1.getName().equals(worldName) && !world2.getName().equals(worldName)) return;
        if (plugin.blueTeam.contains(entity) && plugin.redTeam.contains(damage)) return;
        if (plugin.redTeam.contains(entity) && plugin.blueTeam.contains(damage)) return;
        if (plugin.blueTeam.contains(entity) && plugin.redTeam.contains(((Projectile) damage).getShooter())) return;
        if (plugin.redTeam.contains(entity) && plugin.blueTeam.contains(((Projectile) damage).getShooter())) return;
        event.setCancelled(true);

    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        String worldName = plugin.config().getString("worldName");
        if (worldName == null || worldName.equals("Default")) return;
        if (player.getWorld().getName().contains(worldName)) {
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
            player.getInventory().setChestplate(new ItemStack(Material.AIR));
            player.getInventory().setLeggings(new ItemStack(Material.AIR));
            player.getInventory().setBoots(new ItemStack(Material.AIR));
            player.getInventory().remove(plugin.jgBlueWeapon());
            player.getInventory().remove(plugin.jgRedWeapon());
            player.getInventory().remove(plugin.jgWeapon1());
            player.getInventory().remove(plugin.jgWeapon2());
        }else {
            plugin.blueTeam.remove(player);
            plugin.redTeam.remove(player);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        if (plugin.redTeam.contains(player)){
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[JunkTeamPVP] " + ChatColor.RED + "赤チームの " + player.getName() + ChatColor.WHITE + "は倒された");
        }else if (plugin.blueTeam.contains(player)){
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[JunkTeamPVP] " + ChatColor.BLUE + "青チームの " + player.getName() + ChatColor.WHITE + "は倒された");
        }
    }
}