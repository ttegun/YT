package ytminecraft.main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TitleManager implements CommandExecutor, Listener {
    public static List<String> owner = new ArrayList<>();
    public static List<String> dev = new ArrayList<>();
    public static List<String> user = new ArrayList<>();
    private static Map<String, String> nicknames = new HashMap<>();
    private static Set<UUID> vip = new HashSet<>();
    private static File vipFile;
    private static FileConfiguration vipConfig;

    static {
        owner.add("2ay0ut");
        dev.add("Lucent_1");
    }

    public TitleManager() {
        vipFile = new File("plugins/YTMinecraft/vip.yml");
        vipConfig = YamlConfiguration.loadConfiguration(vipFile);
        loadVIPs();
    }

    private void loadVIPs() {
        List<String> vipList = vipConfig.getStringList("vip");
        for (String uuid : vipList) {
            vip.add(UUID.fromString(uuid));
        }
    }

    private void saveVIPs() {
        List<String> vipList = new ArrayList<>();
        for (UUID uuid : vip) {
            vipList.add(uuid.toString());
        }
        vipConfig.set("vip", vipList);
        try {
            vipConfig.save(vipFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTitle(String playerName) {
        if (owner.contains(playerName)) {
            return "Owner";
        } else if (dev.contains(playerName)) {
            return "Dev";
        } else if (vip.contains(UUID.fromString(playerName))) {
            return "VIP";
        } else if (user.contains(playerName)) {
            return "User";
        } else {
            user.add(playerName);
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
        if (command.getName().equalsIgnoreCase("VIP추가")) {
            if (sender.isOp()) {
                if (args.length == 1) {
                    Player player = sender.getServer().getPlayer(args[0]);
                    if (player != null) {
                        UUID playerUUID = player.getUniqueId();
                        if (!vip.contains(playerUUID)) {
                            vip.add(playerUUID);
                            saveVIPs();
                            sender.sendMessage(player.getName() + " 님이 VIP 목록에 추가되었습니다!");
                        } else {
                            sender.sendMessage(player.getName() + " 는 이미 VIP 목록에 추가되었습니다!");
                        }
                    } else {
                        sender.sendMessage("플레이어를 찾을 수 없습니다.");
                    }
                } else {
                    sender.sendMessage("Usage: /VIP추가 [플레이어 닉네임]");
                }
            } else {
                sender.sendMessage("당신은 이 명령어를 사용 할 권한이 없습니다.");
            }
            return true;
        } else if (command.getName().equalsIgnoreCase("한글닉")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    String newNickname = args[0];
                    String title = getTitle(player.getName());
                    String titleColor = getTitleColor(title);

                    player.setDisplayName(titleColor + "[" + title + "] " + ChatColor.RESET + newNickname);
                    player.setPlayerListName(titleColor + "[" + title + "] " + ChatColor.RESET + newNickname);
                    nicknames.put(player.getName(), newNickname);
                    sender.sendMessage("닉네임이 " + newNickname + " 으로 변경되었습니다.");
                } else {
                    sender.sendMessage("Usage: /한글닉 [텍스트]");
                }
            } else {
                sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.");
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName(); // Use original name for chat
        String title = getTitle(playerName);
        String titleColor = getTitleColor(title);
        String nickname = getNickname(playerName);

        // Modify the chat format
        String message = event.getMessage();
        event.setFormat(titleColor + "[" + title + "] " + ChatColor.RESET + "<" + nickname + "> " + message);
    }
}