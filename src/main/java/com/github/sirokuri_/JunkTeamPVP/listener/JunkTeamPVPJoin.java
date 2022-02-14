package com.github.sirokuri_.JunkTeamPVP.listener;

import com.github.sirokuri_.JunkTeamPVP.JunkTeamPVP;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class JunkTeamPVPJoin implements Listener {

    public final JunkTeamPVP plugin;

    public JunkTeamPVPJoin(JunkTeamPVP junkTeamPVP){
        //コンストラクタ内の処理
        this.plugin = junkTeamPVP;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        //ブロックをクリックしたプレイヤー取得
        Player player = event.getPlayer();
        //クリックしたブロックを取得
        Block block = event.getClickedBlock();
        World world = player.getWorld();
        String worldName = plugin.config().getString("worldName");
        int matchPlayers = plugin.config().getInt("matchPlayers");
        if (worldName == null || worldName.equals("Default")) return;
        if (!world.getName().equals(worldName)) return;
        //ブロックがなければ処理を行わずreturnする
        if (block == null) return;
        //クリックした際にメインハンド以外とブロックを左クリックした時は処理を行わずreturnする
        if ((event.getHand() != EquipmentSlot.HAND || event.getAction() == Action.LEFT_CLICK_BLOCK)) return;
        //クリックしたブロックが壁付きオーク看板以外は処理を行わずreturnする
        if (!(block.getType() == Material.OAK_WALL_SIGN)) return;
        //看板の情報を取得
        Sign sign = (Sign) block.getState();
        //看板の行数(一行目)を取得
        String firstSignLine = sign.getLine(0);
        //看板の行数(二行目)を取得
        String secondSignLine = sign.getLine(1);
        //看板の一行目の文字と二行目の文字がそれぞれ一致しない場合処理を行わずreturnする
        if (!(firstSignLine.equals("[JunkTeamPVP]") && secondSignLine.equals("試合に参加する"))) return;
        //看板をクリックしたプレイヤーがリストに入ってなければ処理を実行する
        if (!plugin.onlinePlayers.contains(player)) {
            //試合開始するためのチーム振り分け用のリストにプレイヤーを入れる
            plugin.onlinePlayers.add(player);
            //チーム振り分け用のリストの人数を取得し÷2をしチーム振り分けを行う
            if (plugin.onlinePlayers.size() % 2 == 0) {
                //赤チームに振り分けられたプレイヤーを赤チームのリストに追加する
                plugin.redTeam.add(player);
                ItemStack[] armor = new ItemStack [4];
                armor[0] = plugin.redTeamBoots();
                armor[1] = plugin.redTeamLeggings();
                armor[2] = plugin.redTeamChestPlate();
                armor[3] = plugin.redTeamHelmet();
                player.getInventory().setArmorContents(armor);
                player.getInventory().setItemInMainHand(plugin.jgWeapon());
                player.sendMessage(ChatColor.RED + "赤チームに参加しました");
                //赤チームに振り分けられなかったプレイヤーをもう片方のチームに入れる
            } else {
                //青チームに振り分けられたプレイヤーを青チームのリストに追加する
                plugin.blueTeam.add(player);
                ItemStack [] armor = new ItemStack [4];
                armor[0] = plugin.blueTeamBoots();
                armor[1] = plugin.blueTeamLeggings();
                armor[2] = plugin.blueTeamChestPlate();
                armor[3] = plugin.blueTeamHelmet();
                player.getInventory().setArmorContents(armor);
                player.getInventory().setItemInMainHand(plugin.jgWeapon());
                player.sendMessage(ChatColor.BLUE + "青チームに参加しました");
                //スケジューラーを開始する
                if (plugin.redTeam.size() + plugin.blueTeam.size() == matchPlayers) {
                    startTimer();
                }
            }
            //チームにすでにプレイヤーが入っていた場合処理を行う
        } else if(plugin.redTeam.contains(player) || plugin.blueTeam.contains(player)){
            player.sendMessage(ChatColor.RED + "既にあなたはチームへ参加しています！");
        } else {
            player.sendMessage(ChatColor.RED + "マッチが終了するまで参加できません");
        }
        //デバッグ用の機能
        //player.sendMessage("" + plugin.blueTeam.contains(player) + "\n" + plugin.redTeam.contains(player) + "\n" + plugin.onlinePlayers.contains(player));
    }

    //ゲーム(PVP)の起動部分
    public void startTimer() {
        int gameTimer = plugin.config().getInt("gameTimer");
        int matchPlayers = plugin.config().getInt("matchPlayers");
        String worldName = plugin.config().getString("worldName");
        //ゲームの秒数を変数として作成
        AtomicInteger countdownStarter = new AtomicInteger(gameTimer);
        //ボスバー作成
        BossBar bossBar = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "チーム対抗PVP : マッチ終了まで" + countdownStarter + " 秒", BarColor.PURPLE, BarStyle.SOLID);
        //試合開始人数の指定：現状はデバッグ用に一名で起動する様にしている
        //試合にエントリーしたプレイヤーが赤チームか青チームに入っていて試合開始人数と同じになったら試合を開始する
        if (plugin.redTeam.size() + plugin.blueTeam.size() == matchPlayers) {
            for(Player p : Bukkit.getServer().getOnlinePlayers()){
                if (p.getWorld().getName().equals(worldName)){
                    p.sendMessage(ChatColor.DARK_PURPLE + "[JunkTeamPVP] エントリー数が " + matchPlayers + " 名以上の為ゲームを開始します");
                    if (plugin.redTeam.contains(p)) {
                        p.performCommand("jtPVP warp redSpawn");
                    }
                    if (plugin.blueTeam.contains(p)) {
                        p.performCommand("jtPVP warp blueSpawn");
                    }
                }
            }
            //スケジューラーを変数化する
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            //スケジューラーを開始する
            Runnable runnable = () -> {
                countdownStarter.getAndDecrement();
                //試合のカウントダウンが0秒以上の時処理を実行
                if (countdownStarter.get() > 0) {
                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        //ボスバーにプレイヤーを追加する
                        if (plugin.redTeam.contains(players) || plugin.blueTeam.contains(players)){
                            bossBar.addPlayer(players);
                        }
                    }
                    //カウントダウンをボスバーに反映する
                    bossBar.setTitle(ChatColor.DARK_PURPLE + "チーム対抗PVP : マッチ終了まで" + countdownStarter  + " 秒");
                    //試合のカウントダウンが0秒の時処理を実行
                } else if (countdownStarter.get() == 0) {
                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        if (plugin.redTeam.contains(players) || plugin.blueTeam.contains(players)){
                            players.sendMessage(ChatColor.DARK_PURPLE + "[JunkTeamPVP] 試合時間が0になったので試合を終了します!");
                            if (worldName == null || worldName.equals("Default")) return;
                            if (!players.getWorld().getName().equals(worldName)) return;
                            players.getInventory().setHelmet(new ItemStack(Material.AIR));
                            players.getInventory().setChestplate(new ItemStack(Material.AIR));
                            players.getInventory().setLeggings(new ItemStack(Material.AIR));
                            players.getInventory().setBoots(new ItemStack(Material.AIR));
                            players.getInventory().remove(plugin.jgWeapon());
                            if (plugin.redTeamCount > plugin.blueTeamCount){
                                players.sendTitle("" + ChatColor.RED + plugin.redTeamCount + ChatColor.DARK_PURPLE + " : " + ChatColor.BLUE + plugin.blueTeamCount,ChatColor.RED + "赤チームが勝利しました",10,70,20);
                            }else if (plugin.redTeamCount < plugin.blueTeamCount){
                                players.sendTitle("" + ChatColor.RED + plugin.redTeamCount + ChatColor.DARK_PURPLE + " : " + ChatColor.BLUE + plugin.blueTeamCount,ChatColor.BLUE + "青チームが勝利しました",10,70,20);
                            }else {
                                players.sendTitle("" + ChatColor.RED + plugin.redTeamCount + ChatColor.DARK_PURPLE + " : " + ChatColor.BLUE + plugin.blueTeamCount,ChatColor.DARK_PURPLE + "引き分けになりました",10,70,20);
                            }
                            if (players.getWorld().getName().equals(worldName)){
                                String hoverText = "スポーンに戻るにはこの文字をクリック!!";
                                BaseComponent[] hover = new ComponentBuilder(hoverText).create();
                                HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover);
                                String command = "/spawn";
                                ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND,command);
                                BaseComponent[] message = new ComponentBuilder(hoverText).event(hoverEvent).event(clickEvent).create();


                                String hoverText1 = "チームPVPロビーへ戻る場合はこの文字をクリック!!";
                                BaseComponent[] hover1 = new ComponentBuilder(hoverText1).create();
                                HoverEvent hoverEvent1 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover1);
                                String command1 = "/jtPVP warp lobbySpawn";
                                ClickEvent clickEvent1 = new ClickEvent(ClickEvent.Action.RUN_COMMAND,command1);
                                BaseComponent[] message1 = new ComponentBuilder(hoverText1).event(hoverEvent1).event(clickEvent1).create();

                                String hoverText2 = "他のゲームを遊びたい場合はこの文字をクリック!!";
                                BaseComponent[] hover2 = new ComponentBuilder(hoverText2).create();
                                HoverEvent hoverEvent2 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover2);
                                String command2 = "/jgselector";
                                ClickEvent clickEvent2 = new ClickEvent(ClickEvent.Action.RUN_COMMAND,command2);
                                BaseComponent[] message2 = new ComponentBuilder(hoverText2).event(hoverEvent2).event(clickEvent2).create();
                                players.sendMessage("[JunkTeamPVP] メニュー");
                                players.sendMessage("");
                                players.spigot().sendMessage(message);
                                players.sendMessage("");
                                players.spigot().sendMessage(message1);
                                players.sendMessage("");
                                players.spigot().sendMessage(message2);
                            }
                        }
                    }
                    plugin.redTeamCount = 0;
                    plugin.blueTeamCount = 0;
                    //スケジューラーを閉じる
                    scheduler.shutdown();
                    //ボスバー削除
                    bossBar.removeAll();
                    //プラグイン起動時に作ったリストの中身を削除
                    plugin.onlinePlayers.clear();
                    plugin.redTeam.clear();
                    plugin.blueTeam.clear();
                }
            };
            scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
        }
    }
}