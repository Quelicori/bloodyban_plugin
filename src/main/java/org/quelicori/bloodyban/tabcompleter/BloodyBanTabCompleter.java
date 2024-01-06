package org.quelicori.bloodyban.tabcompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BloodyBanTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("bloodyban")) {
            if (args.length == 1) {
                completions.add("exempt");
                completions.add("unexempt");
                completions.add("reload");
            }
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("exempt") || args[0].equalsIgnoreCase("unexempt"))) {
            // Если команда - exempt или unexempt, предложите имена всех онлайн игроков
            for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                completions.add(onlinePlayer.getName());
            }
        }

        return completions;
    }
}
