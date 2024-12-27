package ytminecraft.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TitleManager implements Listener, CommandExecutor {
    private static File titleFile;
    private static FileConfiguration titleConfig;
    private static Set<UUID> vip = new HashSet<>();
    private static Set<UUID> vipPlus = new HashSet<>();
    private static Set<UUID> mvp = new HashSet<>();
    private static Set<UUID> mvpPlus = new HashSet<>();
    private static Map<String, String> nicknames = new HashMap<>();
    private static List<UUID> owners = new ArrayList<>();
    private static List<UUID> devs = new ArrayList<>();

    public TitleManager() {
        titleFile = new File("plugins/YTMinecraft/titles.yml");
        titleConfig = YamlConfiguration.loadConfiguration(titleFile);
        loadTitles();

        // Add Owner title to 2ay0ut
        owners.add(UUID.fromString("e07fe73e-b1ed-4fa5-b64a-ad4aadf6b1a7"));

        // Add Dev title to Lucent_1
        devs.add(UUID.fromString("e0c977ae-aa5f-4ecc-9f51-a129886e5abd"));
    }

    private void loadTitles() {
        for (String uuid : titleConfig.getStringList("vip")) {
            vip.add(UUID.fromString(uuid));
        }
        for (String uuid : titleConfig.getStringList("vipPlus")) {
            vipPlus.add(UUID.fromString(uuid));
        }
        for (String uuid : titleConfig.getStringList("mvp")) {
            mvp.add(UUID.fromString(uuid));
        }
        for (String uuid : titleConfig.getStringList("mvpPlus")) {
            mvpPlus.add(UUID.fromString(uuid));
        }
    }

    private static void saveTitles() {
        titleConfig.set("vip", convertUUIDSetToStringList(vip));
        titleConfig.set("vipPlus", convertUUIDSetToStringList(vipPlus));
        titleConfig.set("mvp", convertUUIDSetToStringList(mvp));
        titleConfig.set("mvpPlus", convertUUIDSetToStringList(mvpPlus));
        try {
            titleConfig.save(titleFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<String> convertUUIDSetToStringList(Set<UUID> uuidSet) {
        Set<String> stringList = new HashSet<>();
        for (UUID uuid : uuidSet) {
            stringList.add(uuid.toString());
        }
        return stringList;
    }

    public static boolean addTitle(UUID playerUUID, String title) {
        if (title.equals("Owner")) {
            if (!owners.contains(playerUUID)) {
                owners.add(playerUUID);
                return true;
            }
        } else if (title.equals("Dev")) {
            if (!devs.contains(playerUUID)) {
                devs.add(playerUUID);
                return true;
            }
        } else if (title.equals("Vip")) {
            if (!vip.contains(playerUUID) && !vipPlus.contains(playerUUID) && !mvp.contains(playerUUID) && !mvpPlus.contains(playerUUID)) {
                vip.add(playerUUID);
                saveTitles();
                return true;
            }
        } else if (title.equals("Vip+")) {
            if (!vipPlus.contains(playerUUID) && !mvp.contains(playerUUID) && !mvpPlus.contains(playerUUID)) {
                vipPlus.add(playerUUID);
                saveTitles();
                return true;
            }
        } else if (title.equals("Mvp")) {
            if (!mvp.contains(playerUUID) && !mvpPlus.contains(playerUUID)) {
                mvp.add(playerUUID);
                saveTitles();
                return true;
            }
        } else if (title.equals("Mvp+")) {
            if (!mvpPlus.contains(playerUUID)) {
                mvpPlus.add(playerUUID);
                saveTitles();
                return true;
            }
        }
        return false;
    }

    public static String getTitle(UUID playerUUID) {
        if (owners.contains(playerUUID)) {
            return "Owner";
        } else if (devs.contains(playerUUID)) {
            return "Dev";
        } else if (mvpPlus.contains(playerUUID)) {
            return "Mvp+";
        } else if (mvp.contains(playerUUID)) {
            return "Mvp";
        } else if (vipPlus.contains(playerUUID)) {
            return "Vip+";
        } else if (vip.contains(playerUUID)) {
            return "Vip";
        } else {
            return "User";
        }
    }

    public static String getTitleColor(String title) {
        switch (title) {
            case "Owner":
                return "§f"; // White
            case "Dev":
                return "§c"; // Red
            case "VIP":
                return "§a"; // Green
            case "User":
                return "§7"; // Gray
            default:
                return "§7"; // Default to Gray
        }
    }

    public static String getNickname(String playerName) {
        return nicknames.getOrDefault(playerName, playerName);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("addTitle")) {
            if (args.length != 2) {
                sender.sendMessage("Usage: /addTitle <player> <title>");
                return false;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("Player not found.");
                return false;
            }
            String title = args[1];
            if (addTitle(target.getUniqueId(), title)) {
                sender.sendMessage("Added title " + title + " to " + target.getName());
            } else {
                sender.sendMessage("Failed to add title. The player may already have a title.");
            }
            return true;
        }
        return false;
    }
}