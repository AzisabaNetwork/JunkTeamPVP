package com.github.sirokuri_.JunkTeamPVP.JunkTeamPVPJoin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class JunkTeamPVPJoin implements Listener {
    private final JunkTeamPVP plugin;

    public JunkTeamPVPJoin(JunkTeamPVP junkTeamPVP){
        this.plugin = junkTeamPVP;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        if(block == null) return;
        if(block.getType() == Material.OAK_WALL_SIGN){
            Sign sign = (Sign) block.getState();
            String firstSignLine = sign.getLine(0);
            String secondSignLine = sign.getLine(1);
            if(firstSignLine.equals("[JunkTeamPVP]") && secondSignLine.equals("試合に参加する")){
                if (!plugin.onlinePlayers.contains(player)) {
                    player.sendMessage(ChatColor.GREEN + "ゲームスタートロビーに移動しました");
                    plugin.onlinePlayers.add(player);
                    if (plugin.onlinePlayers.size() % 2 == 0) {
                        plugin.redTeam.add(player);
                        player.sendMessage(ChatColor.RED + "赤チームに参加しました");
                    } else {
                        plugin.blueTeam.add(player);
                        player.sendMessage(ChatColor.BLUE + "青チームに参加しました");
                    }
                } else if(plugin.redTeam.contains(player)){
                    player.sendMessage(ChatColor.RED + "既にあなたは赤チームへ参加しています！");
                } else if(plugin.blueTeam.contains(player)){
                    player.sendMessage(ChatColor.BLUE + "既にあなたは青チームへ参加しています！");
                } else {
                    return;
                }
            }
        }
    }
}