package techsurvival.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ChatMessageType;

import java.util.HashMap;
import java.util.Map;

public class MobCounterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede ser usado por jugadores.");
            return true;
        }

        Player player = (Player) sender;
        int radius = 16;

        if (args.length == 1) {
            try {
                radius = Integer.parseInt(args[0]);
                if (radius > 128) radius = 128;
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Uso: /mobcounter [radio]");
                return true;
            }
        }

        Location center = player.getLocation();
        World world = player.getWorld();
        Entity[] entities = world.getEntities().toArray(new Entity[0]);

        Map<EntityType, Integer> mobCounts = new HashMap<>();

        for (Entity entity : entities) {
            if (entity.getLocation().distanceSquared(center) <= radius * radius) {
                EntityType type = entity.getType();
                mobCounts.put(type, mobCounts.getOrDefault(type, 0) + 1);
            }
        }

        StringBuilder actionBar = new StringBuilder(ChatColor.GOLD + "Mobs: ");
        mobCounts.forEach((type, count) -> actionBar.append(ChatColor.WHITE).append(type.name()).append("=")
                .append(count).append(" "));

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Tu mensaje aquí"));
        return true;
    }
}
