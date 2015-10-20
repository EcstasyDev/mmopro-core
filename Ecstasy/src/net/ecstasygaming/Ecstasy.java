package net.ecstasygaming;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.ecstasygaming.command.CMD_Item;
import net.ecstasygaming.entity.BattleEntity;
import net.ecstasygaming.entity.Gladiator;
import net.ecstasygaming.event.InventoryEvents;
import net.ecstasygaming.event.MobSpawningHandler;
import net.ecstasygaming.event.PlayerCombatEventHandler;
import net.ecstasygaming.objects.EcstasyItem;
import net.ecstasygaming.objects.LootManager;
import net.ecstasygaming.task.TASK_ResyncPlayer;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Ecstasy extends JavaPlugin {
	
	public static Logger log;
	
	public static Permission permission = null;
	public static Economy economy = null;
	
	// Monsters and Players
	public static final HashMap<Integer,BattleEntity> mobs = new HashMap<Integer,BattleEntity>(); 
	public static final HashMap<String,Gladiator> players = new HashMap<String,Gladiator>();
	
	// Items
	public static final HashMap<String,EcstasyItem> items = new HashMap<String,EcstasyItem>();

	@Override
	public void onEnable()
	{
		log = Logger.getLogger("Minecraft");
		
		log.info("Initializing Ecstasy MMORPG Plugin v" + this.getDescription().getVersion());
		log.info("Developer: Zuthara | Website: ECSTASYGAMING.NET");
		log.info("Release: Public/Normal Build (Not Sponsored)");
		
		log.info("Registering server events.");
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new MobSpawningHandler(this), this);
		pm.registerEvents(new PlayerCombatEventHandler(this), this);
		pm.registerEvents(new InventoryEvents(this), this);
		
		// Vault Setup
		log.info("Setting up Vault.");
		if(!setupPermissions()) log.severe("Could not set up permissions. Provider not found.");
		else log.info("Found permissions plugin. Linked.");
			
		if(!setupEconomy()) log.severe("Could not set up economy. Provider not found.");
		else log.info("Found economy plugin. Linked.");
		
		// Register commands
		log.info("Registering commands.");
		this.getCommand("item").setExecutor(new CMD_Item(this));
		
		LootManager.init();
		
		// Kick all players (for reloads)
		for(Player pl : this.getServer().getOnlinePlayers())
		{
			pl.kickPlayer("You have been disconnected from the server.");
		}
		
		// Clear out existing mobs from the world (for reloads)
		// This would not affect pets/mounts since they can be respawned using abilities
		for(World w : this.getServer().getWorlds())
		{
			for(Entity e : w.getEntities())
			{
				if((e instanceof LivingEntity))
				{
					((LivingEntity) e).setHealth(0.00);
				}
			}
		}
		// Following this, mobs would be able to respawn using combined vanilla and custom mechanics
		
		
		// Tasks
		log.info("Registering global tasks and timers...");
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TASK_ResyncPlayer(), 0L, 20L);
		
	}
	@Override
	public void onDisable()
	{
		
	}
	
	private boolean setupPermissions()
	{
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		
		if(permissionProvider != null) permission = permissionProvider.getProvider();
		
		return (permission != null);
	}
	private boolean setupEconomy()
	{
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		
		if(economyProvider != null) economy = economyProvider.getProvider();
		
		return (permission != null);
	}
	public static String build(int start, String[] args) {
		
		String str = "";
		int i = 0;
		for(String s : args)
		{
			if(i > (start-1)) str = str + " " + s;
			i++;
		}
		
		str = str.trim();
		
		return str;
	}
}
