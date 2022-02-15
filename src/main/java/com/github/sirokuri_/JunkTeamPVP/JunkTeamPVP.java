package com.github.sirokuri_.JunkTeamPVP;

import com.github.sirokuri_.JunkTeamPVP.Command.JunkTeamPVPCommand;
import com.github.sirokuri_.JunkTeamPVP.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class JunkTeamPVP extends JavaPlugin {

    public List<Player> onlinePlayers = new ArrayList<Player>();
    public List<Player> redTeam = new ArrayList<Player>();
    public List<Player> blueTeam = new ArrayList<Player>();
    public int blueTeamCount = 0;
    public int redTeamCount = 0;

    @Override
    public void onEnable() {
        // プラグイン読み込み時に起動するクラス
        getServer().getPluginManager().registerEvents(new JunkTeamPVPJoin(this), this);
        getServer().getPluginManager().registerEvents(new JunkTeamPVPGuard(this), this);
        getServer().getPluginManager().registerEvents(new JunkTeamPVPDeathCount(this), this);
        getCommand("jtPVP").setExecutor(new JunkTeamPVPCommand(this));
        saveDefaultConfig();
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

    public ItemStack jgWeapon1(){
        ItemStack jgWeapon = new ItemStack (Material.BOW);
        jgWeapon.addEnchantment(Enchantment.ARROW_INFINITE,1);
        ItemMeta itemMeta = jgWeapon.getItemMeta();
        itemMeta.setUnbreakable(true);
        jgWeapon.setItemMeta(itemMeta);
        return jgWeapon;
    }

    public ItemStack jgWeapon2(){
        return new ItemStack (Material.ARROW);
    }

    public void giveTeamWeapon(){
        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
            if (blueTeam.contains(players)) {
                ItemStack[] armor = new ItemStack [4];
                armor[0] = blueTeamBoots();
                armor[1] = blueTeamLeggings();
                armor[2] = blueTeamChestPlate();
                armor[3] = blueTeamHelmet();
                players.getInventory().setArmorContents(armor);
                players.getInventory().setItemInMainHand(jgWeapon());
                players.getInventory().addItem(jgWeapon1());
                players.getInventory().addItem(jgWeapon2());
            } else if (redTeam.contains(players)) {
                ItemStack[] armor = new ItemStack [4];
                armor[0] = redTeamBoots();
                armor[1] = redTeamLeggings();
                armor[2] = redTeamChestPlate();
                armor[3] = redTeamHelmet();
                players.getInventory().setArmorContents(armor);
                players.getInventory().setItemInMainHand(jgWeapon());
                players.getInventory().addItem(jgWeapon1());
                players.getInventory().addItem(jgWeapon2());
            }
        }
    }

    public void removeTeamWeapon(){
        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
            if (blueTeam.contains(players) || redTeam.contains(players)) {
                players.getInventory().setHelmet(new ItemStack(Material.AIR));
                players.getInventory().setChestplate(new ItemStack(Material.AIR));
                players.getInventory().setLeggings(new ItemStack(Material.AIR));
                players.getInventory().setBoots(new ItemStack(Material.AIR));
                players.getInventory().remove(jgWeapon());
                players.getInventory().remove(jgWeapon1());
                players.getInventory().remove(jgWeapon2());
            }
        }
    }

    private FileConfiguration config = null;

    public FileConfiguration config(){
        load();
        return config;
    }

    public void load() {
        saveDefaultConfig();
        if (config != null) {
            reload();
        }
        config = getConfig();
    }

    public void reload() {
        reloadConfig();
    }
}