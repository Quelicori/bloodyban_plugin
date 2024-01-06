package org.quelicori.bloodyban;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.quelicori.bloodyban.tabcompleter.BloodyBanTabCompleter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.bukkit.Bukkit.getServer;

public final class BloodyBan extends JavaPlugin implements Listener, CommandExecutor {

    private List<String> exemptPlayers = new ArrayList<>();
    private File configFile;


    @Override

    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("bloodyban").setExecutor(this);
        getCommand("bloodyban").setTabCompleter(new BloodyBanTabCompleter());
        configFile = new File(getDataFolder(), "exemptPlayers.yml");
        saveDefaultConfig();
        loadExemptPlayers();

    }


    private String banCommand = getConfig().getString("banCommand", "tempban");

    private String getBanCommand() {
        return banCommand;
    }

    private int banDurationMinutes = getConfig().getInt("banDurationMinutes", 1);
    private String banMessage = getConfig().getString("banMessage", "Death");
    private int timeBefore = getConfig().getInt("timeBefore", 20);

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();
        String playerName = player.getName();

        if (!exemptPlayers.contains(playerName)) {


            // Получение данных из конфига
            String command = banCommand + " " + player.getName() + " " + banDurationMinutes + "m " + banMessage;

            Bukkit.getScheduler().runTaskLater(this, () -> {
                // Выполнение команды от имени консоли
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            }, timeBefore);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("bloodyban")) {
            if (args[0].equalsIgnoreCase("reload")) {
                reloadConfig(); //  перезагрузка конфига

                // обновление данных
                banCommand = getConfig().getString("banCommand", "tempban");
                banDurationMinutes = getConfig().getInt("banDurationMinutes", 1);
                banMessage = getConfig().getString("banMessage", "Death");
                timeBefore = getConfig().getInt("timeBefore", 20);


                sender.sendMessage( ChatColor.RED +"ʙ" +ChatColor.GREEN   +"ʙᴀɴ "+ ChatColor.GRAY +"|" + ChatColor.WHITE +" Плагин успешно перезапущен.");
                return true;
            }
            if (args[0].equalsIgnoreCase("exempt")) {
                // Пользователь ввел /bloodyban exempt playerName
                addExemptPlayer(args[1], sender);
                return true;
            } else if (args.length == 2 && args[0].equalsIgnoreCase("unexempt")) {
                // Пользователь ввел /bloodyban unexempt playerName
                removeExemptPlayer(args[1], sender);
                return true;
            }else if (args.length == 0){
                sender.sendMessage(ChatColor.RED +"ʙ" +ChatColor.GREEN   +"ʙᴀɴ "+ ChatColor.GRAY +"|" + ChatColor.RED + " Неверная команда");
            }
        }
        return false;
    }

    private void addExemptPlayer(String playerName, CommandSender sender) {
        if (exemptPlayers.contains(playerName)) {
            sender.sendMessage(ChatColor.YELLOW + " Игрок " + playerName + " уже находится в списке исключений.");
            return;
        }

        exemptPlayers.add(playerName);
        sender.sendMessage(ChatColor.RED +"ʙ" +ChatColor.GREEN   +"ʙᴀɴ "+ ChatColor.GRAY +"|" + ChatColor.WHITE +" Игрок " +ChatColor.RED+ playerName +ChatColor.WHITE + " добавлен в список исключений.");
        saveExemptPlayers();
    }
    private void removeExemptPlayer(String playerName, CommandSender sender) {
        // Проверка наличия игрока в списке исключений
        if (!exemptPlayers.contains(playerName)) {
            sender.sendMessage(ChatColor.RED +"ʙ" +ChatColor.GREEN   +"ʙᴀɴ "+ ChatColor.GRAY +"|" + ChatColor.WHITE +" Игрок " +ChatColor.RED+ playerName +ChatColor.WHITE + " не найден в списке исключений.");
            return;
        }

        exemptPlayers.remove(playerName);
        sender.sendMessage(ChatColor.RED +"ʙ" +ChatColor.GREEN   +"ʙᴀɴ "+ ChatColor.GRAY +"|" + ChatColor.WHITE +" Игрок " +ChatColor.RED+ playerName +ChatColor.WHITE + " удален из списка исключений.");
        saveExemptPlayers();
    }

    private void saveExemptPlayers() {
        try {
            YamlConfiguration yamlConfig = new YamlConfiguration();
            yamlConfig.set("exemptPlayers", exemptPlayers);
            yamlConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadExemptPlayers() {
        if (configFile.exists()) {
            try {
                YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(configFile);
                exemptPlayers = yamlConfig.getStringList("exemptPlayers");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

