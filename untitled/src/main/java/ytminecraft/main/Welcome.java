package ytminecraft.main;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class Welcome implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String title = TitleManager.getTitle(player.getUniqueId());
        String titleColor = TitleManager.getTitleColor(title);
        String nickname = player.getName();
        // Set the player's display name in chat
        player.setDisplayName(titleColor + "[" + title + "] " + ChatColor.RESET + nickname);

        // Set the player's name in the tab list
        player.setPlayerListName(titleColor + "[" + title + "] " + ChatColor.RESET + nickname);
        player.sendTitle("HAPPY 2025!", "유튜버 소통방에 오신 것을 환영합니다.", 10, 70, 20);
        player.sendMessage(ChatColor.GOLD + "2025년 새해 복 많이 받으세요! -Lucent-");
    }
}