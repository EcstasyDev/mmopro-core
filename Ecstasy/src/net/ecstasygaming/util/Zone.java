package net.ecstasygaming.util;

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
		if(MMOPro.config_zones.contains(identifier))
		{
			this.zoneName = MMOPro.config_zones.getString(identifier + ".name");
			this.world = Bukkit.getWorld(MMOPro.config_zones.getString(identifier + ".world"));
			double minX = MMOPro.config_zones.getDouble(identifier + ".min.x");
			double minY = MMOPro.config_zones.getDouble(identifier + ".min.y");
			double minZ = MMOPro.config_zones.getDouble(identifier + ".min.z");
			double maxX = MMOPro.config_zones.getDouble(identifier + ".max.x");
			double maxY = MMOPro.config_zones.getDouble(identifier + ".max.y");
			double maxZ = MMOPro.config_zones.getDouble(identifier + ".max.z");
			
			this.minPoint = new Location(this.world,minX,minY,minZ);
			this.maxPoint = new Location(this.world,maxX,maxY,maxZ);
		}
		else
		{
			MMOPro.log.severe("Attempted to load a zone that does not exist within records.");
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
	public static void saveZones() {
		
	}
}
