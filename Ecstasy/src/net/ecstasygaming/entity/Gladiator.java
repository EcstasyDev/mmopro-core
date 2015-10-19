package net.ecstasygaming.entity;

import org.bukkit.entity.Player;

import net.ecstasygaming.Ecstasy;
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
		if(p.isOp() || Ecstasy.permission.has(p, "ecstasy.gm"))
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
		}
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
		case 1: return "Scout";
		case 2: return "Mage";
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
	
	public void resyncAttributes()
	{ // Resynchronizes the attributes, health, etc. of the player with the current armor, weapon, and status
		Ecstasy.log.info("Resynchronizing stats for " + p.getName());
		
		// TODO: Add up the values for the armor and stuff that the player is wearing
	}
	
	public Player getPlayer()
	{
		return p;
	}
	
	public void loadInfo()
	{ // Loads the player's info from the database
		
	}
	public void saveInfo()
	{ // Pushes the player's info to the database
		
	}
}
