package ytminecraft.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

public class ShopManager implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("상점")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                openMainShop(player);
            } else {
                sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.");
            }
            return true;
        }
        return false;
    }

    private void openMainShop(Player player) {
        Inventory shop = Bukkit.createInventory(null, 9, "Main Shop");

        ItemStack diamond = new ItemStack(Material.DIAMOND);
        ItemMeta diamondMeta = diamond.getItemMeta();
        diamondMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "칭호 상점");
        diamond.setItemMeta(diamondMeta);
        shop.setItem(4, diamond);

        player.openInventory(shop);
    }

    private void openTitleShop(Player player) {
        Inventory titleShop = Bukkit.createInventory(null, 9, "Title Shop");

        ItemStack vip = new ItemStack(Material.IRON_INGOT);
        ItemMeta vipMeta = vip.getItemMeta();
        vipMeta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Vip");
        vipMeta.setLore(Arrays.asList("Vip칭호를 구매합니다. (50코인)"));
        vip.setItemMeta(vipMeta);
        titleShop.setItem(1, vip);

        ItemStack vipPlus = new ItemStack(Material.GOLD_INGOT);
        ItemMeta vipPlusMeta = vipPlus.getItemMeta();
        vipPlusMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Vip+");
        vipPlusMeta.setLore(Arrays.asList("Vip+칭호를 구매합니다. (100코인)"));
        vipPlus.setItemMeta(vipPlusMeta);
        titleShop.setItem(3, vipPlus);

        ItemStack mvp = new ItemStack(Material.DIAMOND);
        ItemMeta mvpMeta = mvp.getItemMeta();
        mvpMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Mvp");
        mvpMeta.setLore(Arrays.asList("Mvp칭호를 구매합니다. (200코인)"));
        mvp.setItemMeta(mvpMeta);
        titleShop.setItem(5, mvp);

        ItemStack mvpPlus = new ItemStack(Material.EMERALD);
        ItemMeta mvpPlusMeta = mvpPlus.getItemMeta();
        mvpPlusMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Mvp+");
        mvpPlusMeta.setLore(Arrays.asList("Mvp+칭호를 구매합니다. (500코인)"));
        mvpPlus.setItemMeta(mvpPlusMeta);
        titleShop.setItem(7, mvpPlus);

        player.openInventory(titleShop);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Main Shop")) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.DIAMOND) {
                Player player = (Player) event.getWhoClicked();
                openTitleShop(player);
            }
        } else if (event.getView().getTitle().equals("Title Shop")) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null) {
                Player player = (Player) event.getWhoClicked();
                UUID playerUUID = player.getUniqueId();
                int coins = AttendanceManager.getAttendanceCoins(playerUUID);
                String title = event.getCurrentItem().getItemMeta().getDisplayName();

                if (title.equals(ChatColor.WHITE + "" + ChatColor.BOLD + "Vip") && coins >= 50) {
                    if (TitleManager.addTitle(playerUUID, "Vip")) {
                        AttendanceManager.deductAttendanceCoins(playerUUID, 50);
                        player.sendMessage("Vip 칭호를 구매하였습니다!");
                    } else {
                        player.sendMessage("이미 구매하였거나 더 높은랭크를 가지고 있습니다!");
                    }
                } else if (title.equals(ChatColor.GOLD + "" + ChatColor.BOLD + "Vip+") && coins >= 100) {
                    if (TitleManager.addTitle(playerUUID, "Vip+")) {
                        AttendanceManager.deductAttendanceCoins(playerUUID, 100);
                        player.sendMessage("Vip+ 칭호를 구매하였습니다!");
                    } else {
                        player.sendMessage("이미 구매하였거나 더 높은랭크를 가지고 있습니다!");
                    }
                } else if (title.equals(ChatColor.AQUA + "" + ChatColor.BOLD + "Mvp") && coins >= 200) {
                    if (TitleManager.addTitle(playerUUID, "Mvp")) {
                        AttendanceManager.deductAttendanceCoins(playerUUID, 200);
                        player.sendMessage("Mvp 칭호를 구매하였습니다!");
                    } else {
                        player.sendMessage("이미 구매하였거나 더 높은랭크를 가지고 있습니다!");
                    }
                } else if (title.equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Mvp+") && coins >= 500) {
                    if (TitleManager.addTitle(playerUUID, "Mvp+")) {
                        AttendanceManager.deductAttendanceCoins(playerUUID, 500);
                        player.sendMessage("Mvp+ 칭호를 구매하였습니다!");
                    } else {
                        player.sendMessage("이미 구매하였거나 더 높은랭크를 가지고 있습니다!");
                    }
                } else {
                    player.sendMessage("코인이 부족합니다.");
                }
            }
        }
    }
}