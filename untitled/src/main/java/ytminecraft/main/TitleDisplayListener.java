package ytminecraft.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class TitleDisplayListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String title = TitleManager.getTitle(player.getUniqueId());
        String titleColor = TitleManager.getTitleColor(title);
        String defaulttitleColor = "§f";
        event.setFormat(titleColor + "[" + title + "] " + defaulttitleColor + "<" + player.getName() + ">" + ": " + event.getMessage());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String title = TitleManager.getTitle(player.getUniqueId());
        String titleColor = TitleManager.getTitleColor(title);
        String defaulttitleColor = "§f";
        player.setPlayerListName(titleColor + "[" + title + "]" + defaulttitleColor + " " + player.getName());
    }
}