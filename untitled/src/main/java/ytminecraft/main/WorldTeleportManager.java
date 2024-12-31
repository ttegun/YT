package ytminecraft.main;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class WorldTeleportManager implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("워프")) {
            if (args.length != 1) {
                sender.sendMessage("Usage: /워프 <월드 폴더 이름>");
                return false;
            }

            String worldName = args[0];
            World world = Bukkit.getWorld(worldName);

            if (world == null) {
                sender.sendMessage("The world with this name does not exist.");
                return false;
            }

            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.teleport(world.getSpawnLocation());
                sender.sendMessage("Teleported to world " + worldName);

                if (worldName.equalsIgnoreCase("world")) {
                    player.setGameMode(GameMode.CREATIVE);
                } else {
                    player.setGameMode(GameMode.SURVIVAL);
                }

                if (worldName.equalsIgnoreCase("cpvp")) {
                    setupScoreboard(player);
                    giveSwords(player);
                    giveDiamondArmor(player);
                }
            } else {
                sender.sendMessage("This command can only be used by players.");
            }

            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        World respawnWorld = player.getWorld();

        if (respawnWorld.getName().equalsIgnoreCase("world_nether") || respawnWorld.getName().equalsIgnoreCase("world_end")) {
            World survivalWorld = Bukkit.getWorld("survival");
            if (survivalWorld != null) {
                event.setRespawnLocation(survivalWorld.getSpawnLocation());
            }
        } else {
            event.setRespawnLocation(respawnWorld.getSpawnLocation());
        }
    }

    private void setupScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("stats", "dummy", "Player Stats");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore("Kills").setScore(0);
        objective.getScore("Deaths").setScore(0);

        player.setScoreboard(board);
    }

    private void giveSwords(Player player) {
        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta diamondMeta = diamondSword.getItemMeta();
        if (diamondMeta != null) {
            diamondMeta.setDisplayName("COMBO");
            diamondSword.setItemMeta(diamondMeta);
        }

        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
        ItemMeta stoneMeta = stoneSword.getItemMeta();
        if (stoneMeta != null) {
            stoneMeta.setDisplayName("PRACTICE");
            stoneSword.setItemMeta(stoneMeta);
        }

        player.getInventory().addItem(diamondSword, stoneSword);
        player.sendMessage("You have received a COMBO diamond sword and a PRACTICE stone sword.");
    }

    private void giveDiamondArmor(Player player) {
        ItemStack[] armor = new ItemStack[4];
        armor[0] = new ItemStack(Material.DIAMOND_BOOTS);
        armor[1] = new ItemStack(Material.DIAMOND_LEGGINGS);
        armor[2] = new ItemStack(Material.DIAMOND_CHESTPLATE);
        armor[3] = new ItemStack(Material.DIAMOND_HELMET);

        for (ItemStack item : armor) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.PROTECTION, 4, true);
                item.setItemMeta(meta);
            }
        }

        player.getInventory().setArmorContents(armor);
        player.sendMessage("You have received a full set of diamond armor with Protection IV.");
    }
}