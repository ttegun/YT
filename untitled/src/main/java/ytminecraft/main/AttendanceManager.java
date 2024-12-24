package ytminecraft.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AttendanceManager implements CommandExecutor {
    private static File attendanceFile;
    private static FileConfiguration attendanceConfig;

    public AttendanceManager() {
        attendanceFile = new File("plugins/YTMinecraft/attendance.yml");
        attendanceConfig = YamlConfiguration.loadConfiguration(attendanceFile);
    }

    private static void saveAttendance() {
        try {
            attendanceConfig.save(attendanceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getAttendanceCoins(UUID playerUUID) {
        return attendanceConfig.getInt(playerUUID + ".coins", 0);
    }

    public static void deductAttendanceCoins(UUID playerUUID, int amount) {
        int coins = attendanceConfig.getInt(playerUUID + ".coins", 0) - amount;
        attendanceConfig.set(playerUUID + ".coins", coins);
        saveAttendance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("출석체크")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                UUID playerUUID = player.getUniqueId();
                long currentTime = System.currentTimeMillis();
                long lastAttendanceTime = attendanceConfig.getLong(playerUUID + ".lastAttendanceTime", 0);

                if (currentTime - lastAttendanceTime >= 24 * 60 * 60 * 1000) {
                    int coins = attendanceConfig.getInt(playerUUID + ".coins", 0) + 5;
                    int days = attendanceConfig.getInt(playerUUID + ".days", 0) + 1;

                    attendanceConfig.set(playerUUID + ".coins", coins);
                    attendanceConfig.set(playerUUID + ".days", days);
                    attendanceConfig.set(playerUUID + ".lastAttendanceTime", currentTime);
                    saveAttendance();

                    sender.sendMessage("출석체크 완료! 현재 출석 코인: " + coins + ", 출석 일수: " + days);
                } else {
                    long timeLeft = 24 * 60 * 60 * 1000 - (currentTime - lastAttendanceTime);
                    sender.sendMessage("출석체크는 24시간마다 가능합니다. 남은 시간: " + timeLeft / 1000 / 60 + "분");
                }
            } else {
                sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.");
            }
            return true;
        }
        return false;
    }
}