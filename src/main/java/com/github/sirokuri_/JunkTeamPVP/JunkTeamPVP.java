package com.github.sirokuri_.JunkTeamPVP;

import com.github.sirokuri_.JunkTeamPVP.Command.JunkTeamPVPCommand;
import com.github.sirokuri_.JunkTeamPVP.listener.*;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
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
        getCommand("jtPVP").setExecutor(new JunkTeamPVPCommand(this));
    }

    @Override
    public void onDisable() {
        /* プラグイン無効化時に行う処理
        現状は使わないので無機能
         */
    }

    public ItemStack blueTeamHelmet(){
        ItemStack blueHelmet = new ItemStack (Material.LEATHER_HELMET);
        blueHelmet.addEnchantment(Enchantment.BINDING_CURSE,1);
        LeatherArmorMeta leatherHelmetMeta = (LeatherArmorMeta) blueHelmet.getItemMeta();
        leatherHelmetMeta.setUnbreakable(true);
        leatherHelmetMeta.setColor(Color.BLUE);
        blueHelmet.setItemMeta(leatherHelmetMeta);
        return blueHelmet;
    }

    public ItemStack blueTeamChestPlate(){
        ItemStack blueArmor = new ItemStack (Material.LEATHER_CHESTPLATE);
        blueArmor.addEnchantment(Enchantment.BINDING_CURSE,1);
        LeatherArmorMeta leatherChestPlateMeta = (LeatherArmorMeta) blueArmor.getItemMeta();
        leatherChestPlateMeta.setUnbreakable(true);
        leatherChestPlateMeta.setColor(Color.BLUE);
        blueArmor.setItemMeta(leatherChestPlateMeta);
        return blueArmor;
    }

    public ItemStack blueTeamLeggings(){
        ItemStack blueLeggings = new ItemStack (Material.LEATHER_LEGGINGS);
        blueLeggings.addEnchantment(Enchantment.BINDING_CURSE,1);
        LeatherArmorMeta leatherLeggingsMeta = (LeatherArmorMeta) blueLeggings.getItemMeta();
        leatherLeggingsMeta.setUnbreakable(true);
        leatherLeggingsMeta.setColor(Color.BLUE);
        blueLeggings.setItemMeta(leatherLeggingsMeta);
        return blueLeggings;
    }

    public ItemStack blueTeamBoots(){
        ItemStack blueBoots = new ItemStack (Material.LEATHER_BOOTS);
        blueBoots.addEnchantment(Enchantment.BINDING_CURSE,1);
        LeatherArmorMeta leatherBootsMeta = (LeatherArmorMeta) blueBoots.getItemMeta();
        leatherBootsMeta.setUnbreakable(true);
        leatherBootsMeta.setColor(Color.BLUE);
        blueBoots.setItemMeta(leatherBootsMeta);
        return blueBoots;
    }

    public ItemStack redTeamHelmet(){
        ItemStack redHelmet = new ItemStack (Material.LEATHER_HELMET);
        redHelmet.addEnchantment(Enchantment.BINDING_CURSE,1);
        LeatherArmorMeta leatherHelmetMeta = (LeatherArmorMeta) redHelmet.getItemMeta();
        leatherHelmetMeta.setUnbreakable(true);
        leatherHelmetMeta.setColor(Color.RED);
        redHelmet.setItemMeta(leatherHelmetMeta);
        return redHelmet;
    }

    public ItemStack redTeamChestPlate(){
        ItemStack redArmor = new ItemStack (Material.LEATHER_CHESTPLATE);
        redArmor.addEnchantment(Enchantment.BINDING_CURSE,1);
        LeatherArmorMeta leatherChestPlateMeta = (LeatherArmorMeta) redArmor.getItemMeta();
        leatherChestPlateMeta.setUnbreakable(true);
        leatherChestPlateMeta.setColor(Color.RED);
        redArmor.setItemMeta(leatherChestPlateMeta);
        return redArmor;
    }

    public ItemStack redTeamLeggings(){
        ItemStack redLeggings = new ItemStack (Material.LEATHER_LEGGINGS);
        redLeggings.addEnchantment(Enchantment.BINDING_CURSE,1);
        LeatherArmorMeta leatherLeggingsMeta = (LeatherArmorMeta) redLeggings.getItemMeta();
        leatherLeggingsMeta.setUnbreakable(true);
        leatherLeggingsMeta.setColor(Color.RED);
        redLeggings.setItemMeta(leatherLeggingsMeta);
        return redLeggings;
    }

    public ItemStack redTeamBoots(){
        ItemStack redBoots = new ItemStack (Material.LEATHER_BOOTS);
        redBoots.addEnchantment(Enchantment.BINDING_CURSE,1);
        LeatherArmorMeta leatherBootsMeta = (LeatherArmorMeta) redBoots.getItemMeta();
        leatherBootsMeta.setUnbreakable(true);
        leatherBootsMeta.setColor(Color.RED);
        redBoots.setItemMeta(leatherBootsMeta);
        return redBoots;
    }

    public ItemStack jgWeapon(){
        ItemStack jgWeapon = new ItemStack (Material.IRON_SWORD);
        ItemMeta itemMeta = jgWeapon.getItemMeta();
        itemMeta.setUnbreakable(true);
        jgWeapon.setItemMeta(itemMeta);
        return jgWeapon;
    }
}