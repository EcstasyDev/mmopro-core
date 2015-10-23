package net.ecstasygaming.entity;

import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.ecstasygaming.MMOPro;
import net.ecstasygaming.objects.EcstasyItem;
import net.ecstasygaming.util.MessageType;
import net.ecstasygaming.util.Messenger;
import net.md_5.bungee.api.ChatColor;

public class Gladiator {
	// Gladiators have the same functions as players, but store more data
	
	private String title;
	// Basic
	private int level;
	private int cls;
	private int xp;
	// Health
	private double health;
	private double max_health;
	// Buffs and Wards
	private double outgoing_damage_multiplier;
	private double incoming_damage_multiplier;
	// Primary Attributes
	private double att_strength;
	private double att_stamina;
	private double att_intellect;
	private double att_discipline;
	private double att_ranged;
	
	// Secondary Attributes (Modifiers)
	private double att_armor;
	private double att_dodge;
	private double att_crit;
	
	private double max_resource;
	private double resource;
	
	private ItemStack[] pastContents = null;
	
	private Player p; // The corresponding player entity which this class connects to
	
	public Gladiator(Player p)
	{ // Constructs a Gladiator using a normal player
		this.p = p;
		
		level = 1;
		cls = -1;
		health = 100.0;
		max_health = 100.0;
		outgoing_damage_multiplier = 1.00;
		incoming_damage_multiplier = 1.00;
		
		att_strength = 0.00;
		att_stamina = 0.00;
		att_intellect = 0.00;
		att_discipline = 0.00;
		att_ranged = 0.00;
		
		att_armor = 0.00;
		att_dodge = 0.00;
		att_crit = 0.00;
		
		resource = 1;
		
		setTitle("",TitlePosition.BEFORE); // Initializes the custom name
	}
	public String getTitle()
	{
		return this.title;
	}
	public void setTitle(String title, TitlePosition tp)
	{
		this.title = title;
		
		String str = "";
		switch(tp)
		{
		case BEFORE:
			str = "" + getHealthColor() + "(" + level + ") " + title + " " + p.getName();
		case AFTER:
			str = "" + getHealthColor() + "(" + level + ") " + p.getName() + " " + title;
			break;
		}
		
		
		// Adds a Gamemaster Tag or Admin Tag
		if(p.isOp() || MMOPro.permission.has(p, "ecstasy.gm"))
		{
			str = str + " " + ChatColor.DARK_PURPLE + "<GM>";
		}
		
		p.setCustomName(str);
		p.setCustomNameVisible(true);
	}
	public void addExperience(int xp)
	{ // Note: Calling addExperience(0) will initiate a standard level check
		
		if(this.level == 50) return; // Maximum Level, prevent adding XP
		
		this.xp+=xp;
		
		Messenger.toPlayer(this.getPlayer(),"+" + xp + " Experience",MessageType.MESSAGE);
		
		// Check for level up
		if(this.xp >= getRequiredLevelExperience(level+1))
		{
			this.level++;
			this.xp = 0;
			
			Messenger.toPlayer(p, "Congratulations, you have advanced to Level " + this.level + "!", MessageType.MESSAGE);
			
			this.resyncAttributes();
			
			this.putInfo();
		}
	}
	public int getExperience()
	{
		return this.xp;
	}
	public int getRequiredLevelExperience(int level)
	{
		int amt = 0;
		
		// Recursively determine the amount of experience needed for the next level
		if(level > 0) amt = ((2 * level) * 175) + getRequiredLevelExperience((level - 1));
		else return amt;
		
		return amt;
	}
	private ChatColor getHealthColor() {
		double d = health / max_health;
		
		if(d > 95.00) return ChatColor.DARK_GREEN;
		else if(d > 80.00) return ChatColor.GREEN;
		else if(d > 60.00) return ChatColor.YELLOW;
		else if(d > 40.00) return ChatColor.GOLD;
		else if(d > 20.00) return ChatColor.RED;
		else return ChatColor.DARK_RED;
	}
	public void setLevel(int level)
	{
		this.level = level;
	}
	public int getLevel()
	{
		return (this.level);
	}
	
	public void setClass(int level)
	{
		this.cls = level;
	}
	public int getClassNumber()
	{
		return (this.cls);
	}
	public String getClassString()
	{
		switch(this.cls)
		{
		case 0: return "Warrior";
		case 1: return "Paladin";
		case 2: return "Hunter";
		case 3: return "Mage";
		case 4: return "Priest";
		case -1: return "Loading";
		}
		return "null";
	}
	public double getMaxHealth()
	{
		return (this.max_health);
	}
	public void setMaxHealth(double amount)
	{
		this.max_health = amount;
	}
	public double getHealth()
	{
		return (this.health);
	}
	public void setHealth(double amount)
	{
		this.health = amount;
		
		// TODO: Adjust the proportional amount of hearts they have remaining
		double prop = ((20.0 * amount) / getMaxHealth());
		
		this.getPlayer().setHealth(prop);
		
		if(amount < 0) this.getPlayer().setHealth(0.00);
	}
	public void setOutgoingDamageMultiplier(double multiplier)
	{
		this.outgoing_damage_multiplier = multiplier;
	}
	public void setIncomingDamageMultiplier(double multiplier)
	{
		this.incoming_damage_multiplier = multiplier;
	}
	public double getOutgoingDamageMultiplier()
	{
		return (this.outgoing_damage_multiplier);
	}
	public double getIncomingDamageMultiplier()
	{
		return (this.incoming_damage_multiplier);
	}
	public double getCombatAttribute(PlayerCombatAttribute attribute)
	{
		switch(attribute)
		{
		case STRENGTH: return att_strength;
		case ARMOR: return att_armor;
		case CRITICAL_STRIKE: return att_crit;
		case DISCIPLINE: return att_discipline;
		case DODGE: return att_dodge;
		case INTELLECT: return att_intellect;
		case RANGED: return att_ranged;
		case STAMINA: return att_stamina;
		default: return 0.00;
		}
	}
	public void setCombatAttribute(PlayerCombatAttribute attribute, double value)
	{
		switch(attribute)
		{
		case STRENGTH: att_strength = value;
		case ARMOR: att_armor = value;
		case CRITICAL_STRIKE: att_crit = value;
		case DISCIPLINE: att_discipline = value;
		case DODGE: att_dodge = value;
		case INTELLECT: att_intellect = value;
		case RANGED: att_ranged = value;
		case STAMINA: att_stamina = value;
		}
	}
	public void setResource(double res)
	{
		this.resource = res;
	}
	public double getResource() { return this.resource; }
	public void setMaxResource(double res)
	{
		this.max_resource = res;
	}
	public double getMaxResource() { return this.max_resource; }
	
	
	public void resyncAttributes()
	{ // Resynchronizes the attributes, health, etc. of the player with the current armor, weapon, and status
		
		if(p.getInventory().getArmorContents() != this.pastContents)
		{ // Only resync if the player's armor has changed in the last 1 second
			this.pastContents = p.getInventory().getArmorContents();
			// Ecstasy.log.info("Resynchronizing stats for " + p.getName());
		
			// TODO: Add up the values for the armor and stuff that the player is wearing
			this.att_stamina = 0.0;
			this.att_strength = 0.0;
			this.att_intellect = 0.0;
			this.att_discipline = 0.0;
			this.att_ranged = 0.0;
			this.att_armor = 0.0;
			this.att_crit = 0.0;
			this.att_dodge = 0.0;
			
			for(ItemStack is : p.getInventory().getArmorContents())
			{
				if(is.hasItemMeta())
				{
					// Cycle through items to find it
					EcstasyItem i = null;
					for(String key : MMOPro.items.keySet())
					{
						if(MMOPro.items.get(key).getItemStack().equals(is))
						{
							i = MMOPro.items.get(key);
							break;
						}
					}
					
					if((i.getRequiredLevel() > 0) && (this.getLevel() < i.getRequiredLevel()))
					{ // If the player does not meet the requirements for this
						Messenger.toPlayer(p, "You are not a high enough level to use this equipment.", MessageType.WARNING);
						p.getInventory().remove(is);
						p.getInventory().addItem(is);
						continue; // Prevents stat calculation for this item
					}
					if((i.getRequiredClass() != 0) && (this.getClassNumber() != i.getRequiredClass()))
					{ // If the player does not meet the requirements for this
						Messenger.toPlayer(p, "You lack the required proficiency to use this equipment.", MessageType.WARNING);
						p.getInventory().remove(is);
						p.getInventory().addItem(is);
						continue; // Prevents stat calculation for this item
					}
					
					double dBase = ((20.0/(double)level)+(level)); // Permits a curve for new players
					if(i != null)
					{
						try
						{
							att_strength += i.getCombatAttribute(PlayerCombatAttribute.STRENGTH)+dBase;
							att_stamina += i.getCombatAttribute(PlayerCombatAttribute.STAMINA)+dBase;
							att_intellect += i.getCombatAttribute(PlayerCombatAttribute.INTELLECT)+dBase;
							att_discipline += i.getCombatAttribute(PlayerCombatAttribute.DISCIPLINE)+dBase;
							att_ranged += i.getCombatAttribute(PlayerCombatAttribute.RANGED)+dBase;
							
							att_armor += i.getCombatAttribute(PlayerCombatAttribute.ARMOR);
							att_crit += i.getCombatAttribute(PlayerCombatAttribute.CRITICAL_STRIKE);
							att_dodge += i.getCombatAttribute(PlayerCombatAttribute.DODGE);
						} catch(ConcurrentModificationException e)
						{
							MMOPro.log.severe("Intercepted ConcurrentModificationException / Attempted to modify data concurrently as another source.  Will attempt resync next cycle.");
						}
					}
				}
			}
			
			double prop = (this.getHealth() / this.getMaxHealth());
			
			this.max_health = (att_stamina * 6.5);
			this.health = (prop * this.getMaxHealth());
			
			prop = (this.getResource() / this.getMaxResource());
			
			// Recalculate total mana
			if(this.getClassNumber() == 1 || this.getClassNumber() == 3 || this.getClassNumber() == 4) this.max_resource = (10*((att_intellect/3) + (att_discipline/6)));
			else if(this.getClassNumber() == 2) this.max_resource = (10*((att_ranged/3) + (att_stamina/6)));
			else if(this.getClassNumber() == 0) this.max_resource = (10*((att_strength/3) + (att_stamina/6)));
			else this.max_resource = 0;
			
			this.resource = prop * this.max_resource;
		}
	}
	
	public Player getPlayer()
	{
		return p;
	}
	
	public void loadInfo()
	{ // Loads the player's info from the database
		
		if(MMOPro.config_players.contains(p.getUniqueId().toString()))
		{
			this.level = MMOPro.config_players.getInt(p.getUniqueId().toString() + ".level");
			this.cls = MMOPro.config_players.getInt(p.getUniqueId().toString() + ".class");
			this.xp = MMOPro.config_players.getInt(p.getUniqueId().toString() + ".xp");
			String t = MMOPro.config_players.getString(p.getUniqueId().toString() + ".title.text");
			int tpos = MMOPro.config_players.getInt(p.getUniqueId().toString() + ".title.pos");
			
			if(t != null)
			{
				if(tpos == -1) this.setTitle(t, TitlePosition.BEFORE);
				else if(tpos == 1) this.setTitle(t, TitlePosition.AFTER);
			}
			
			resyncAttributes();
		}
		else
		{
			MMOPro.log.info("Player '" + p.getName() + "' does not have a configuration saved.  Creating...");
			saveInfo();
			loadInfo();
		}
	}
	public void saveInfo()
	{ // Pushes the player's info to the database
		MMOPro.config_players.set(p.getUniqueId().toString() + ".level", this.level);
		MMOPro.config_players.set(p.getUniqueId().toString() + ".cls", this.cls);
		MMOPro.config_players.set(p.getUniqueId().toString() + ".xp", this.xp);
		MMOPro.config_players.set(p.getUniqueId().toString() + ".title.text", this.title);
		MMOPro.config_players.set(p.getUniqueId().toString() + ".title.pos", 0);
		
		try {
			MMOPro.config_players.save(new File(Bukkit.getPluginManager().getPlugin("MMOPro").getDataFolder() + File.separator + "players.yml"));
		} catch (IOException e) {
			MMOPro.log.severe("Failed to save player configuration information.");
		}
	}
	public void putInfo()
	{ // Pushes the player's info to the database
		MMOPro.config_players.set(p.getUniqueId().toString() + ".level", this.level);
		MMOPro.config_players.set(p.getUniqueId().toString() + ".cls", this.cls);
		MMOPro.config_players.set(p.getUniqueId().toString() + ".xp", this.xp);
		MMOPro.config_players.set(p.getUniqueId().toString() + ".title.text", this.title);
		MMOPro.config_players.set(p.getUniqueId().toString() + ".title.pos", 0);
	}
}
