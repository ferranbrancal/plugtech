package techsurvival.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Particle;
import java.util.Random;

public class ChunkInfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede ser usado por jugadores.");
            return true;
        }

        Player player = (Player) sender;
        Location loc = player.getLocation();
        Chunk chunk = loc.getChunk();

        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        int entityCount = chunk.getEntities().length;
        boolean isSlimeChunk = isSlimeChunk(chunkX, chunkZ, player.getWorld().getSeed());

        String slimeInfo = isSlimeChunk ? ChatColor.GREEN + "Sí" : ChatColor.RED + "No";
        String message = ChatColor.AQUA + "Chunk [" + chunkX + ", " + chunkZ + "] " + ChatColor.WHITE +
                "Slime: " + slimeInfo + ChatColor.WHITE + " | Entidades: " + entityCount;

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Texto aquí"));
        highlightChunkBorder(chunk, player);
        return true;
    }

    private boolean isSlimeChunk(int chunkX, int chunkZ, long seed) {
        Random rnd = new Random(seed + (chunkX * chunkX * 4987142) + (chunkX * 5947611) +
                (chunkZ * chunkZ * 4392871) + (chunkZ * 389711) ^ 987234911L);
        return rnd.nextInt(10) == 0;
    }

    private void highlightChunkBorder(Chunk chunk, Player player) {
        World world = chunk.getWorld();
        int y = player.getLocation().getBlockY();
        int startX = chunk.getX() << 4;
        int startZ = chunk.getZ() << 4;

        for (int dx = 0; dx < 16; dx++) {
            for (int dz = 0; dz < 16; dz++) {
                if (dx == 0 || dx == 15 || dz == 0 || dz == 15) {
                    Location particleLoc = new Location(world, startX + dx + 0.5, y + 1.2, startZ + dz + 0.5);
                    world.spawnParticle(Particle.VILLAGER_HAPPY, particleLoc, 2, 0, 0, 0, 0);
                }
            }
        }
    }
}
