package com.github.sirokuri_.JunkTeamPVP.listener;

import com.github.sirokuri_.JunkTeamPVP.JunkTeamPVP;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

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
        //取得したワールドがjgTutorial以外は処理を行わずreturnする
        World world = player.getWorld();
        if (!world.getName().equals("jgTutorial")) return;
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
                player.sendMessage(ChatColor.RED + "赤チームに参加しました");
            //赤チームに振り分けられなかったプレイヤーをもう片方のチームに入れる
            } else {
                //青チームに振り分けられたプレイヤーを青チームのリストに追加する
                plugin.blueTeam.add(player);
                player.sendMessage(ChatColor.BLUE + "青チームに参加しました");
            }
        //チームにすでにプレイヤーが入っていた場合処理を行う
        } else if(plugin.redTeam.contains(player) || plugin.blueTeam.contains(player)){
            player.sendMessage(ChatColor.RED + "既にあなたはチームへ参加しています！");
        } else {
            return;
        }/*ここはデバッグ用に用意したコード
        player.sendMessage("" + plugin.blueTeam.contains(player) + "\n" + plugin.redTeam.contains(player) + "\n" + plugin.onlinePlayers.contains(player));
        */
    }
}