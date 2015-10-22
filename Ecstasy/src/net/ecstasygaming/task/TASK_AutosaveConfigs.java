package net.ecstasygaming.task;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.ecstasygaming.MMOPro;
import net.ecstasygaming.entity.Gladiator;

public class TASK_AutosaveConfigs implements Runnable {
	
	final MMOPro plugin;
	public TASK_AutosaveConfigs(MMOPro plugin)
	{
		this.plugin = plugin;
		MMOPro.log.info("Registered task: Player Mana Regeneration");
	}

	@Override
	public void run() {
		MMOPro.log.info("Autosaving configuration and player data...");
		try {
			MMOPro.config_global.save(new File(plugin.getDataFolder() + File.separator + "global.yml"));
			MMOPro.log.info("Successfully saved Global Configuration.");
		} catch (IOException e) {
			MMOPro.log.severe("Global Configuration save failed.");
		}
		try {
			MMOPro.config_zones.save(new File(plugin.getDataFolder() + File.separator + "zones.yml"));
			MMOPro.log.info("Successfully saved Zones Configuration.");
		} catch (IOException e) {
			MMOPro.log.severe("Global Configuration save failed.");
		}
		try {
			
			// Dump info
			Gladiator g;
			for(Player pl : Bukkit.getOnlinePlayers())
			{
				if(MMOPro.players.containsKey(pl.getName()))
				{
					g = MMOPro.players.get(pl.getName());
					
					g.putInfo(); // Puts it into the tmp file
				}
			}
			
			// Commits the data and flushes the buffer
			MMOPro.config_players.save(new File(plugin.getDataFolder() + File.separator + "players.yml"));
			MMOPro.log.info("Successfully saved Player Configuration.");
		} catch (IOException e) {
			MMOPro.log.severe("Global Configuration save failed.");
		}
		
	}

}
