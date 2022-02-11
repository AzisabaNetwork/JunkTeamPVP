package com.github.sirokuri_.JunkTeamPVP.Command;

import com.github.sirokuri_.JunkTeamPVP.JunkTeamPVP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class JunkTeamPVPCommand implements CommandExecutor {

    private final JunkTeamPVP plugin;

    public JunkTeamPVPCommand(JunkTeamPVP junkTeamPVP) {
        this.plugin = junkTeamPVP;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("jtPVP")) {
            if (args.length <= 0) {
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("JunkTeamPVPCommand.permission.Admin")) {
                    plugin.reload();
                    sender.sendMessage("config reload...");
                }
                return true;
            }
        }
        return true;
    }
}
