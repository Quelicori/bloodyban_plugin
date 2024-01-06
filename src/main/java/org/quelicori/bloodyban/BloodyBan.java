package org.quelicori.bloodyban;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getServer;

public final class BloodyBan extends JavaPlugin  implements Listener, CommandExecutor{

    @Override
    public void onEnable()  {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        getCommand("bloodyban").setExecutor(this);
    }
    private String banCommand = getConfig().getString("banCommand", "tempban");
    public String getBanCommand(){
        return banCommand;
    }
    private int banDurationMinutes = getConfig().getInt("banDurationMinutes", 1);
    private String banMessage = getConfig().getString("banMessage", "Death");
    private int timeBefore = getConfig().getInt("timeBefore", 20);
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();


        // Получение данных из конфига
        String command = banCommand + " " + player.getName() + " " + banDurationMinutes + "m " + banMessage;


        Bukkit.getScheduler().runTaskLater(this, () -> {
            // Выполнение команды от имени консоли
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
                },timeBefore);
    }

@Override
public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (label.equalsIgnoreCase("bloodybanreload")) {

        getServer().getPluginManager().disablePlugin(this);
        getServer().getPluginManager().enablePlugin(this);
        banDurationMinutes = getConfig().getInt("banDurationMinutes", 1);
        banMessage = getConfig().getString("banMessage", "Death");
        timeBefore = getConfig().getInt("timeBefore", 20);

        sender.sendMessage("Плагин успешно перезапущен.");
        return true;
    }
    return false;
}
}

