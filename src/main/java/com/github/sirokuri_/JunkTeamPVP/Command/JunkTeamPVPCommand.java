package com.github.sirokuri_.JunkTeamPVP.Command;

import com.github.sirokuri_.JunkTeamPVP.JunkTeamPVP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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
        int matchPlayers = plugin.config().getInt("matchPlayers");
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
            if (args[0].equalsIgnoreCase("set")) {
                if (sender.hasPermission("JunkTeamPVPCommand.permission.Admin")) {
                    if (args.length <= 1) {
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("blue")) {
                        Location location = player.getLocation();
                        World world = player.getWorld();
                        String worldName = world.getName();
                        int x = location.getBlockX();
                        int y = location.getBlockY();
                        int z = location.getBlockZ();
                        int yaw = (int) location.getYaw();
                        int pitch = (int) location.getPitch();
                        plugin.config().set("blueSpawn",worldName + "," + x + "," + y + "," + z + "," + yaw + "," + pitch);
                        plugin.saveConfig();
                    }

                    if (args[1].equalsIgnoreCase("red")) {
                        Location location = player.getLocation();
                        World world = player.getWorld();
                        String worldName = world.getName();
                        int x = location.getBlockX();
                        int y = location.getBlockY();
                        int z = location.getBlockZ();
                        int yaw = (int) location.getYaw();
                        int pitch = (int) location.getPitch();
                        plugin.config().set("redSpawn",worldName + "," + x + "," + y + "," + z + "," + yaw + "," + pitch);
                        plugin.saveConfig();
                    }

                    if (args[1].equalsIgnoreCase("lobby")) {
                        Location location = player.getLocation();
                        World world = player.getWorld();
                        String worldName = world.getName();
                        int x = location.getBlockX();
                        int y = location.getBlockY();
                        int z = location.getBlockZ();
                        int yaw = (int) location.getYaw();
                        int pitch = (int) location.getPitch();
                        plugin.config().set("lobbySpawn",worldName + "," + x + "," + y + "," + z + "," + yaw + "," + pitch);
                        plugin.saveConfig();
                    }
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("warp")) {
                if (args.length <= 1) {
                    return true;
                }
                if (args[1].equalsIgnoreCase("blueSpawn")) {
                    if (plugin.redTeam.size() + plugin.blueTeam.size() >= matchPlayers) {
                        if (plugin.blueTeam.contains(player)){
                            String data = plugin.config().getString(args[1]);
                            if (data == null) return true;
                            String[] loc = data.split(",");
                            World world = Bukkit.getServer().getWorld(loc[0]);
                            double x = Double.parseDouble(loc[1]);
                            double y = Double.parseDouble(loc[2]);
                            double z = Double.parseDouble(loc[3]);
                            int yaw = (int) Double.parseDouble(loc[4]);
                            int pitch = (int) Double.parseDouble(loc[5]);
                            Location location = new Location(world, x, y, z);
                            location.setPitch(pitch);
                            location.setYaw(yaw);
                            player.teleport(location);
                        }else{
                            player.sendMessage(ChatColor.RED + "あなたはこのチームではありません");
                        }
                    }else {
                        player.sendMessage(matchPlayers + "名以上になるまでワープできません");
                    }
                    return true;
                }

                if (args[1].equalsIgnoreCase("redSpawn")) {
                    if (plugin.redTeam.size() + plugin.blueTeam.size() >= matchPlayers) {
                        if (plugin.redTeam.contains(player)){
                            String data = plugin.config().getString(args[1]);
                            if (data == null) return true;
                            String[] loc = data.split(",");
                            World world = Bukkit.getServer().getWorld(loc[0]);
                            double x = Double.parseDouble(loc[1]);
                            double y = Double.parseDouble(loc[2]);
                            double z = Double.parseDouble(loc[3]);
                            int yaw = (int) Double.parseDouble(loc[4]);
                            int pitch = (int) Double.parseDouble(loc[5]);
                            Location location = new Location(world, x, y, z);
                            location.setPitch(pitch);
                            location.setYaw(yaw);
                            player.teleport(location);
                        }else{
                            player.sendMessage(ChatColor.RED + "あなたはこのチームではありません");
                        }
                    }else {
                        player.sendMessage(matchPlayers + "名以上になるまでワープできません");
                    }
                    return true;
                }

                if (args[1].equalsIgnoreCase("lobbySpawn")) {
                    String data = plugin.config().getString(args[1]);
                    if (data == null) return true;
                    String[] loc = data.split(",");
                    World world = Bukkit.getServer().getWorld(loc[0]);
                    double x = Double.parseDouble(loc[1]);
                    double y = Double.parseDouble(loc[2]);
                    double z = Double.parseDouble(loc[3]);
                    int yaw = (int) Double.parseDouble(loc[4]);
                    int pitch = (int) Double.parseDouble(loc[5]);
                    Location location = new Location(world, x, y, z);
                    location.setPitch(pitch);
                    location.setYaw(yaw);
                    player.teleport(location);
                    return true;
                }
            }
            return true;
        }
        return true;
    }
}
