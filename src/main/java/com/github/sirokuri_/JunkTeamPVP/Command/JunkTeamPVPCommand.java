package com.github.sirokuri_.JunkTeamPVP.Command;

import com.github.sirokuri_.JunkTeamPVP.JunkTeamPVP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class JunkTeamPVPCommand implements CommandExecutor {

    private final JunkTeamPVP plugin;

    public JunkTeamPVPCommand(JunkTeamPVP junkTeamPVP) {
        this.plugin = junkTeamPVP;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("sb")) {
            if (args.length <= 0) {
                return true;
            }
            if (args[0].equalsIgnoreCase("create")) {
                //OP以外起動しないように設定
                if (sender.hasPermission("JunkTeamPVPCommand.permission.Admin")) {
                    player.sendMessage("テスト");
                }
                return true;
            }
        }
        return true;
    }
}
