package net.ecstasygaming.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.Selection;

import net.ecstasygaming.MMOPro;
import net.ecstasygaming.entity.BattleEntity;
import net.ecstasygaming.entity.PlayerCombatAttribute;

public class Zone {

	private int maxLevel, minLevel;
	private boolean eliteMonsters;
	private boolean isRaidZone;
	
	private BattleEntity zoneBoss;
	
	protected World world;
	protected Location minPoint;
	protected Location maxPoint;
	
	protected String mobPrefix = "";
	
	private String zoneName;
	
	// For initializing new zones in-game
	public Zone(String zoneName, Selection s)
	{
		this.zoneName = zoneName;
		this.world = s.getWorld();
		this.minPoint = s.getMinimumPoint();
		this.maxPoint = s.getMaximumPoint();
	}
	// For initializing zones from config (Existing Zones)
	public Zone(String identifier)
	{
		MMOPro.log.info("Attempting to load zone from config: '" + identifier + "'");
		if(MMOPro.config_zones.contains(identifier))
		{
			this.zoneName = MMOPro.config_zones.getString(identifier.toUpperCase() + ".name");
			this.world = Bukkit.getWorld(MMOPro.config_zones.getString(identifier.toUpperCase() + ".world"));
			double minX = MMOPro.config_zones.getDouble(identifier.toUpperCase() + ".bounds.min.x");
			double minY = MMOPro.config_zones.getDouble(identifier.toUpperCase() + ".bounds.min.y");
			double minZ = MMOPro.config_zones.getDouble(identifier.toUpperCase() + ".bounds.min.z");
			double maxX = MMOPro.config_zones.getDouble(identifier.toUpperCase() + ".bounds.max.x");
			double maxY = MMOPro.config_zones.getDouble(identifier.toUpperCase() + ".bounds.max.y");
			double maxZ = MMOPro.config_zones.getDouble(identifier.toUpperCase() + ".bounds.max.z");
			
			this.setMinimumZoneLevel(MMOPro.config_zones.getInt(this.zoneName + ".level.min"));
			this.setMaximumZoneLevel(MMOPro.config_zones.getInt(this.zoneName + ".level.max"));
			this.setRaidZone(MMOPro.config_zones.getBoolean(this.zoneName + ".props.raid-zone"));
			this.setEliteMonstersAllowed(MMOPro.config_zones.getBoolean(this.zoneName + ".props.elite-zone"));
			
			this.minPoint = new Location(this.world,minX,minY,minZ);
			this.maxPoint = new Location(this.world,maxX,maxY,maxZ);
			
			MMOPro.log.info("Successfully loaded Zone '" + identifier.toUpperCase() + "' from config.");
		}
		else
		{
			MMOPro.log.severe("Attempted to load a zone ('" + identifier.toUpperCase() + "') that does not exist within records.");
		}
	}
	
	public Vector getMinPointAsVector()
	{
		Vector v;
		
		v = new Vector(this.minPoint.getX(),this.minPoint.getY(),this.minPoint.getZ());
		
		return v;
	}
	public Vector getMaxPointAsVector()
	{
		Vector v;
		
		v = new Vector(this.maxPoint.getX(),this.maxPoint.getY(),this.maxPoint.getZ());
		
		return v;
	}
	public String getZoneName() { return this.zoneName; }
	public void setMaximumZoneLevel(int n){ this.maxLevel = n; }
	public int getMaximumZoneLevel(){ return this.maxLevel; }
	public void setMinimumZoneLevel(int n){ this.minLevel = n; }
	public int getMinimumZoneLevel(){ return this.minLevel; }
	public void setRaidZone(boolean b){ this.isRaidZone = b; }
	public boolean isRaidZone(){ return this.isRaidZone; }
	public void setEliteMonstersAllowed(boolean b){ this.eliteMonsters = b; }
	public boolean isEliteMonstersAllowed(){ return this.eliteMonsters; }
	public void setZoneBoss(BattleEntity e)
	{
		// Kills the existing zone boss
		if(zoneBoss != null) zoneBoss.getEntity().teleport(new Location(zoneBoss.getEntity().getWorld(),-15000,255,-15000));
		
		this.zoneBoss = e;
		
		// Resync Attributes
		e.setElite(true);
		e.setHealth((e.getCombatAttribute(PlayerCombatAttribute.STAMINA) * 6.25));
		e.setBoss(true);
		e.setHealth(e.getHealth()*20.0);
		
		e.setName(e.getName() + " (BOSS)");
	}
	public BattleEntity getZoneBoss()
	{
		return this.zoneBoss;
	}
	public static void saveZones()
	{
		
		MMOPro plugin = (MMOPro) Bukkit.getServer().getPluginManager().getPlugin("MMOPro");
		
		Zone z = null;
		List<String> strList = new ArrayList<String>();
		for(String key : MMOPro.zones.keySet())
		{
			z = MMOPro.zones.get(key);
			
			MMOPro.config_zones.set(z.getZoneName() + ".bounds.min.x", z.getMinPointAsVector().getBlockX());
			MMOPro.config_zones.set(z.getZoneName() + ".bounds.min.y", z.getMinPointAsVector().getBlockY());
			MMOPro.config_zones.set(z.getZoneName() + ".bounds.min.z", z.getMinPointAsVector().getBlockZ());
			MMOPro.config_zones.set(z.getZoneName() + ".bounds.max.x", z.getMinPointAsVector().getBlockX());
			MMOPro.config_zones.set(z.getZoneName() + ".bounds.max.y", z.getMinPointAsVector().getBlockY());
			MMOPro.config_zones.set(z.getZoneName() + ".bounds.max.z", z.getMinPointAsVector().getBlockZ());
			
			MMOPro.config_zones.set(z.getZoneName() + ".level.min", z.getMinimumZoneLevel());
			MMOPro.config_zones.set(z.getZoneName() + ".level.max", z.getMaximumZoneLevel());
			MMOPro.config_zones.set(z.getZoneName() + ".props.raid-zone", z.isRaidZone());
			MMOPro.config_zones.set(z.getZoneName() + ".props.elite-zone", z.isEliteMonstersAllowed());
			
			strList.add(key);
		}
		
		try {
			MMOPro.config_zones.save(new File(plugin.getDataFolder() + File.separator + "zones.yml"));
		} catch (IOException e) {
			MMOPro.log.severe("Failed to save the zone configuration.");
		}
		
		MMOPro.config_zones.set("zones", strList);
		
	}
}
