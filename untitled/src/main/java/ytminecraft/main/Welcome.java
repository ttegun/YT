package ytminecraft.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Welcome implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String title = TitleManager.getTitle(playerName);
        String titleColor = TitleManager.getTitleColor(title);
        String nickname = TitleManager.getNickname(playerName);

        // Set the player's display name in chat
        player.setDisplayName(titleColor + "[" + title + "] " + ChatColor.RESET + nickname);

        // Set the player's name in the tab list
        player.setPlayerListName(titleColor + "[" + title + "] " + ChatColor.RESET + nickname);

        // Send a welcome title
        player.sendTitle(ChatColor.RED + "유튜버 소통방에 오신 것을 환영합니다!", "", 10, 70, 20);
    }
}