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
        String worldName = plugin.config().getString("worldName");
        if (worldName == null || worldName.equals("Default")) return;
        World world1 = entity.getWorld();
        World world2 = damage.getWorld();
        if (!world1.getName().equals(worldName) && !world2.getName().equals(worldName)) return;
        if (plugin.blueTeam.contains(entity) && plugin.blueTeam.contains(damage) || plugin.redTeam.contains(entity) && plugin.redTeam.contains(damage)) event.setCancelled(true);
        if (plugin.blueTeam.contains(entity) && plugin.blueTeam.contains(((Projectile) damage).getShooter()) || plugin.redTeam.contains(entity) && plugin.redTeam.contains(((Projectile) damage).getShooter())) event.setCancelled(true);
        if (!(plugin.onlinePlayers.contains(entity))) event.setCancelled(true);
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
        String worldName = plugin.config().getString("worldName");
        if (worldName == null || worldName.equals("Default")) return;
        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
            if (player.getWorld().getName().contains(worldName)) {
                if (plugin.redTeam.contains(player)){
                    players.sendMessage(ChatColor.DARK_PURPLE + "[JunkTeamPVP] " + ChatColor.RED + "赤チームの " + player.getName() + ChatColor.WHITE + "は倒された");
                }else if (plugin.blueTeam.contains(player)){
                    players.sendMessage(ChatColor.DARK_PURPLE + "[JunkTeamPVP] " + ChatColor.BLUE + "青チームの " + player.getName() + ChatColor.WHITE + "は倒された");
                }

            }
        }
        player.performCommand("jtPVP warp lobbySpawn");
    }
}