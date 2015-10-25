package net.ecstasygaming.entity;

import org.bukkit.entity.Entity;

import net.md_5.bungee.api.ChatColor;

public class BattleEntity {
	// Basic
	
		private String name = "";
		
		private int level;
		private boolean elite;
		private boolean boss;
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
		
		private Entity e; // The corresponding player entity which this class connects to
		
		public BattleEntity(Entity e)
		{ // Constructs a Gladiator using a normal player
			this.e = e;
			
			level = 1;
			elite = false;
			boss = false;
			
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
		}
		
		public String getName()
		{
			return this.name;
		}
		public void setName(String name)
		{
			this.name = name;
			
			String str = "" + getHealthColor() + "(" + level + ") " + name + " [" + this.getHealth() + " / " + this.getMaxHealth() + "]";
			
			e.setCustomName(str);
			e.setCustomNameVisible(true);
		}
		
		private ChatColor getHealthColor() {
			double d = health / max_health;
			
			if(d > 0.95) return ChatColor.DARK_GREEN;
			else if(d > 0.80) return ChatColor.GREEN;
			else if(d > 0.60) return ChatColor.YELLOW;
			else if(d > 0.40) return ChatColor.GOLD;
			else if(d > 0.20) return ChatColor.RED;
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
		
		public void setBoss(boolean bool)
		{
			this.boss = bool;
		}
		public void setElite(boolean bool)
		{
			this.elite = bool;
		}
		public boolean isBoss()
		{
			return this.boss;
		}
		public boolean isElite()
		{
			return this.elite;
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
		
		public Entity getEntity()
		{
			return e;
		}
}
