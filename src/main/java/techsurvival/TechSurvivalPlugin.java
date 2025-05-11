package techsurvival;

import org.bukkit.plugin.java.JavaPlugin;
import techsurvival.commands.ChunkInfoCommand;
import techsurvival.commands.MobCounterCommand;

public class TechSurvivalPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("chunkinfo").setExecutor(new ChunkInfoCommand());
        this.getCommand("mobcounter").setExecutor(new MobCounterCommand());
    }
}
