package net.ecstasygaming.event;

import java.util.Random;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;

import net.ecstasygaming.MMOPro;
import net.ecstasygaming.combat.PlayerCombatAttribute;
import net.ecstasygaming.combat.Zone;
import net.ecstasygaming.entity.BattleEntity;
import net.ecstasygaming.entity.Gladiator;
import net.ecstasygaming.objects.EcstasyItem;
import net.ecstasygaming.util.MessageType;
import net.ecstasygaming.util.Messenger;

public class MobSpawningHandler implements Listener {
	
	MMOPro plugin;
	public MobSpawningHandler(MMOPro plugin)
	{
		MMOPro.log.info("Linked Mob Spawning handler.");
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		Entity e = event.getEntity();
		if(e instanceof Player) return;
		
		int level = 1;
		double health = 1.00; // base health
		boolean elite = false;
		
		int LMax = 1, LMin = 0;
		
		BattleEntity be = new BattleEntity(e);

		Zone z = null;
		if(MMOPro.zones.size() > 0)
		{
			CuboidRegion c;
			// Retrieve the Zone they spawned inside
			for(String zonekey : MMOPro.zones.keySet())
			{
				c = new CuboidRegion(MMOPro.zones.get(zonekey).getMinPointAsVector(),MMOPro.zones.get(zonekey).getMaxPointAsVector());
				
				// Check if the entity is inside this zone and get the level range if so
				if(c.contains(new Vector(e.getLocation().getX(),e.getLocation().getY(),e.getLocation().getZ())))
				{
					z = MMOPro.zones.get(zonekey);
					LMax = z.getMaximumZoneLevel();
					LMin = z.getMinimumZoneLevel();
					break;
				}
			}
		}
		else
		{
			LMax = 5;
			LMin = 1;
		}
		
		if(z != null)
		{
		
			MMOPro.log.info("Spawning " + e.getType().toString() + " in Zone '" + z.getZoneName() + "'.");
			Random r = new Random();
			level = r.nextInt(Math.abs(LMax-LMin+1))+LMin;
			
			be.setLevel(level);
			
			if(elite)
			{
				be.setCombatAttribute(PlayerCombatAttribute.STAMINA, (level*MMOPro.config_global.getDouble("mobs.multipliers.stamina.elite")));
				be.setCombatAttribute(PlayerCombatAttribute.STRENGTH, (level*MMOPro.config_global.getDouble("mobs.multipliers.strength.elite")));
				be.setCombatAttribute(PlayerCombatAttribute.RANGED, (level*MMOPro.config_global.getDouble("mobs.multipliers.ranged.elite")));
				be.setCombatAttribute(PlayerCombatAttribute.INTELLECT, (level*MMOPro.config_global.getDouble("mobs.multipliers.intellect.elite")));
				be.setCombatAttribute(PlayerCombatAttribute.DISCIPLINE, (level*MMOPro.config_global.getDouble("mobs.multipliers.discipline.elite")));
				
				be.setCombatAttribute(PlayerCombatAttribute.DODGE, (level*MMOPro.config_global.getDouble("mobs.multipliers.dodge.elite")));
				be.setCombatAttribute(PlayerCombatAttribute.CRITICAL_STRIKE, (level*MMOPro.config_global.getDouble("mobs.multipliers.critical-strike.elite")));
			}
			else
			{
				be.setCombatAttribute(PlayerCombatAttribute.STAMINA, (level*MMOPro.config_global.getDouble("mobs.multipliers.stamina.normal")));
				be.setCombatAttribute(PlayerCombatAttribute.STRENGTH, (level*MMOPro.config_global.getDouble("mobs.multipliers.strength.normal")));
				be.setCombatAttribute(PlayerCombatAttribute.RANGED, (level*MMOPro.config_global.getDouble("mobs.multipliers.ranged.normal")));
				be.setCombatAttribute(PlayerCombatAttribute.INTELLECT, (level*MMOPro.config_global.getDouble("mobs.multipliers.intellect.normal")));
				be.setCombatAttribute(PlayerCombatAttribute.DISCIPLINE, (level*MMOPro.config_global.getDouble("mobs.multipliers.discipline.normal")));
				
				be.setCombatAttribute(PlayerCombatAttribute.DODGE, (level*MMOPro.config_global.getDouble("mobs.multipliers.dodge.normal")));
				be.setCombatAttribute(PlayerCombatAttribute.CRITICAL_STRIKE, (level*MMOPro.config_global.getDouble("mobs.multipliers.critical-strike.normal")));
			}
			
			health = be.getCombatAttribute(PlayerCombatAttribute.STAMINA) * MMOPro.config_global.getDouble("mobs.multipliers.health");
			
			be.setElite(elite);
			if(elite) be.setMaxHealth(health);
			else be.setMaxHealth(health);
			
			be.setHealth(health);
			
			be.setName(WordUtils.capitalizeFully(e.getName()));
			
			MMOPro.mobs.put(e.getEntityId(), be);
		}
		else
		{
			MMOPro.log.info("Spawning " + e.getType().toString() + " in Common Zone.");
			Random r = new Random();
			level = r.nextInt(Math.abs(4))+1;
			
			be.setLevel(level);
			
			if(elite)
			{
				be.setCombatAttribute(PlayerCombatAttribute.STAMINA, (level*MMOPro.config_global.getDouble("mobs.multipliers.stamina.elite")));
				be.setCombatAttribute(PlayerCombatAttribute.STRENGTH, (level*MMOPro.config_global.getDouble("mobs.multipliers.strength.elite")));
				be.setCombatAttribute(PlayerCombatAttribute.RANGED, (level*MMOPro.config_global.getDouble("mobs.multipliers.ranged.elite")));
				be.setCombatAttribute(PlayerCombatAttribute.INTELLECT, (level*MMOPro.config_global.getDouble("mobs.multipliers.intellect.elite")));
				be.setCombatAttribute(PlayerCombatAttribute.DISCIPLINE, (level*MMOPro.config_global.getDouble("mobs.multipliers.discipline.elite")));
				
				be.setCombatAttribute(PlayerCombatAttribute.DODGE, (level*MMOPro.config_global.getDouble("mobs.multipliers.dodge.elite")));
				be.setCombatAttribute(PlayerCombatAttribute.CRITICAL_STRIKE, (level*MMOPro.config_global.getDouble("mobs.multipliers.critical-strike.elite")));
			}
			else
			{
				be.setCombatAttribute(PlayerCombatAttribute.STAMINA, (level*MMOPro.config_global.getDouble("mobs.multipliers.stamina.normal")));
				be.setCombatAttribute(PlayerCombatAttribute.STRENGTH, (level*MMOPro.config_global.getDouble("mobs.multipliers.strength.normal")));
				be.setCombatAttribute(PlayerCombatAttribute.RANGED, (level*MMOPro.config_global.getDouble("mobs.multipliers.ranged.normal")));
				be.setCombatAttribute(PlayerCombatAttribute.INTELLECT, (level*MMOPro.config_global.getDouble("mobs.multipliers.intellect.normal")));
				be.setCombatAttribute(PlayerCombatAttribute.DISCIPLINE, (level*MMOPro.config_global.getDouble("mobs.multipliers.discipline.normal")));
				
				be.setCombatAttribute(PlayerCombatAttribute.DODGE, (level*MMOPro.config_global.getDouble("mobs.multipliers.dodge.normal")));
				be.setCombatAttribute(PlayerCombatAttribute.CRITICAL_STRIKE, (level*MMOPro.config_global.getDouble("mobs.multipliers.critical-strike.normal")));
			}
			
			health = be.getCombatAttribute(PlayerCombatAttribute.STAMINA) * MMOPro.config_global.getDouble("mobs.multipliers.health");
			
			be.setElite(elite);
			if(elite) be.setMaxHealth(health);
			else be.setMaxHealth(health);
			
			be.setHealth(health);
			
			be.setName(WordUtils.capitalizeFully(e.getName()));
			
			MMOPro.mobs.put(e.getEntityId(), be);
		}
		
	}	
	@EventHandler (priority = EventPriority.NORMAL)
	public void OnEntityDamage(EntityDamageByEntityEvent event)
	{
		if(event.getEntity() instanceof LivingEntity)
		{
			Entity e = (LivingEntity) event.getEntity();
			
			if(!(e instanceof Player))
			{ // Entity is not a player at all
				// Find the battle entity
				if(MMOPro.mobs.containsKey(e.getEntityId()))
				{
					event.setDamage(0.00); // cancels the native event damage
					BattleEntity be = MMOPro.mobs.get(e.getEntityId());
					
					double baseDamage = 0.00;
					double critChance = 0.00;
					double blockChance = 0.00;
					double maxHit = 0;
					double minHit = 0;
					Random r = new Random();
					if(event.getDamager() instanceof Player)
					{ // Player directly caused the damage
						Player p = (Player) event.getDamager();
						Gladiator g = MMOPro.players.get(p.getName());
						
						
						// Retrieve base damage
						if(p.getItemInHand() != null)
						{
							ItemStack is = p.getItemInHand();
							
							if(is.hasItemMeta())
							{
								if(MMOPro.items.containsKey(is.getItemMeta().getDisplayName().toUpperCase()))
								{
									EcstasyItem i = MMOPro.items.get(is.getItemMeta().getDisplayName().toUpperCase());
									
									if(i.getMinimumDamage() > 0.00) minHit = (int) (i.getMinimumDamage()+1);
									if(i.getMaximumDamage() > 0.00) maxHit = (int) (i.getMaximumDamage()+1);
								}
								else
								{
									minHit = 1;
									maxHit = 1;
								}
							}
							else
							{
								baseDamage = 0.00; // Minecraft vanilla items are boring and take only strength bonus for strength
								// An 'Iron Sword' and an [Iron Sword] are two different items
								// [Iron Sword] has item meta and would have additional damage applied during this effect
							}
						}
						
						baseDamage = (double) (r.nextInt((int) (Math.abs(maxHit-minHit+1)))+minHit)+1.00;
						
						// Apply a strength bonus to the base weapon damage
						// Stronger characters will do more melee damage this way
						// Meaning, better armor (with Strength bonuses) allow player to do more damage than players without armor
						// This puts an emphasis on having decent armor while PVP/PVEing
						// !IMPORTANT! All stat figures during testing are based on a character with all gear slots filled
						// Meaning, if a player is missing a single gear slot, their damage could be reduced by upwards of 20%
						baseDamage = baseDamage + (g.getCombatAttribute(PlayerCombatAttribute.STRENGTH) / 6.00) + (g.getLevel() * 17.25);
						
						
						// Check for enemy block chance
						blockChance = be.getCombatAttribute(PlayerCombatAttribute.DODGE);
						
						if((r.nextInt(100) < blockChance))
						{
							baseDamage = 0.0;
							Messenger.toPlayer(p, "" + be.getName() + " has dodged the attack.", MessageType.COMBAT);
							return; // Hops out of the event handling since it was blocked
						}
						
						// Check for critical chance
						critChance = g.getCombatAttribute(PlayerCombatAttribute.CRITICAL_STRIKE);
						
						if((r.nextInt(100) < critChance))
						{
							Messenger.toPlayer(p, "Critical Strike (Damage increased by +" + ((baseDamage*1.3)-baseDamage) + ")", MessageType.COMBAT);
							baseDamage = baseDamage * 1.3;
						}
						
						// Multiply for outgoing damage buffs
						baseDamage = baseDamage * g.getOutgoingDamageMultiplier();
						
						// Apply incoming damage wards
						baseDamage = baseDamage * be.getIncomingDamageMultiplier();
					
						// Finalize the damage
						if(baseDamage >= 0.00) Messenger.toPlayer(p, "Dealt " + baseDamage + " damage to " + be.getName() + ".", MessageType.COMBAT);
						else Messenger.toPlayer(p, "Healed " + be.getName() + " for " + (baseDamage*-1) + " health.", MessageType.COMBAT);
						
						// Resync the entity's health
						be.setHealth((be.getHealth()-baseDamage));
						be.setName(WordUtils.capitalizeFully(e.getType().toString()));
						
					}
					else if(event.getDamager() instanceof Arrow)
					{
						Arrow a = (Arrow) event.getDamager();
						
						if(a.getShooter() instanceof Player)
						{ // Player shot the arrow
							Player p = (Player) a.getShooter();
							Gladiator g = MMOPro.players.get(p.getName());
							
							// Retrieve base damage
							if(p.getItemInHand() != null)
							{
								ItemStack is = p.getItemInHand();
								
								if(is.hasItemMeta())
								{
									EcstasyItem i = null;
									for(String key : MMOPro.items.keySet())
									{
										if(MMOPro.items.get(key).getItemStack().getItemMeta().getDisplayName() == is.getItemMeta().getDisplayName())
										{
											i = MMOPro.items.get(key);
										}
									}
									
									if(i != null)
									{
										maxHit = i.getMaximumDamage();
										minHit = i.getMinimumDamage();
									}
								}
								else
								{
									baseDamage = 0.00; // Minecraft vanilla items are boring and take only strength bonus for strength
									// An 'Iron Sword' and an [Iron Sword] are two different items
									// [Iron Sword] has item meta and would have additional damage applied during this effect
								}
							}
							
							baseDamage = (double) (r.nextInt((int) (Math.abs(maxHit-minHit+1)))+minHit)+1.00;
							
							// Apply a ranged attribute bonus
							baseDamage = baseDamage + (g.getCombatAttribute(PlayerCombatAttribute.RANGED) / 4.00) + (g.getLevel() * 17.25);
							
							// Check for enemy block chance
							blockChance = be.getCombatAttribute(PlayerCombatAttribute.DODGE);
							
							if((r.nextInt(100) < blockChance))
							{
								baseDamage = 0.0;
								Messenger.toPlayer(p, "" + be.getName() + " has dodged the attack.", MessageType.COMBAT);
								return; // Hops out of the event handling since it was blocked
							}
							
							// Check for critical chance
							critChance = g.getCombatAttribute(PlayerCombatAttribute.CRITICAL_STRIKE);
							
							if((r.nextInt(100) < critChance))
							{
								Messenger.toPlayer(p, "Critical Strike (Damage increased by +" + ((baseDamage*1.3)-baseDamage) + ")", MessageType.COMBAT);
								baseDamage = baseDamage * 1.3;
							}
							
							// Multiply for outgoing damage buffs
							baseDamage = baseDamage * g.getOutgoingDamageMultiplier();
							
							// Apply incoming damage wards
							baseDamage = baseDamage * be.getIncomingDamageMultiplier();
						
							// Finalize the damage
							if(baseDamage >= 0.00) Messenger.toPlayer(p, "Dealt " + baseDamage + " damage to " + be.getName() + ".", MessageType.COMBAT);
							else Messenger.toPlayer(p, "Healed " + be.getName() + " for " + (baseDamage*-1) + " health.", MessageType.COMBAT);
							
							// Resync the entity's health
							be.setHealth((be.getHealth()-baseDamage));
							be.setName(WordUtils.capitalizeFully(e.getType().toString()));
							
						}
						else if(a.getShooter() instanceof LivingEntity)
						{ // NPC Living Entity shot the arrow
							LivingEntity led = (LivingEntity) a.getShooter();
							BattleEntity bd = MMOPro.mobs.get(led.getEntityId());
							
							baseDamage = (double) (r.nextInt((int) (Math.abs(maxHit-minHit+1)))+minHit)+1.00;
							
							// Apply a ranged attribute bonus
							baseDamage = baseDamage + (bd.getCombatAttribute(PlayerCombatAttribute.RANGED) / 2.00) + (bd.getLevel() * 47.25);
							
							// Check for enemy block chance
							blockChance = be.getCombatAttribute(PlayerCombatAttribute.DODGE);
							
							if((r.nextInt(100) < blockChance))
							{
								baseDamage = 0.0;
								return; // Hops out of the event handling since it was blocked
							}
							
							// Check for critical chance
							critChance = bd.getCombatAttribute(PlayerCombatAttribute.CRITICAL_STRIKE);
							
							if((r.nextInt(100) < critChance))
							{
								baseDamage = baseDamage * 1.3;
							}
							
							// Multiply for outgoing damage buffs
							baseDamage = baseDamage * bd.getOutgoingDamageMultiplier();
							
							// Apply incoming damage wards
							baseDamage = baseDamage * be.getIncomingDamageMultiplier();
							
							// Resync the entity's health
							be.setHealth((be.getHealth()-baseDamage));
							be.setName(WordUtils.capitalizeFully(e.getType().toString()));
						}
						else
						{ // Arrow was shot by a cannon or dispenser (or null source)
							be.setHealth(be.getHealth() - (be.getMaxHealth()*0.095));
							// Takes 9.5% damage from an automatically shot arrow (or from null source)
						}
					}
					else if(event.getDamager() instanceof LivingEntity)
					{
						LivingEntity le = (LivingEntity) event.getDamager();
						BattleEntity bd = MMOPro.mobs.get(le.getEntityId());
						
						maxHit = (int) bd.getCombatAttribute(PlayerCombatAttribute.STRENGTH)/9;
						minHit = (int) bd.getCombatAttribute(PlayerCombatAttribute.STRENGTH)/13;
						
						// Determine the base hit
						baseDamage = r.nextInt((int) (Math.abs(maxHit-minHit)+1)) + minHit;
						
						// Check for critical chance
						// Check for enemy block chance
						blockChance = be.getCombatAttribute(PlayerCombatAttribute.DODGE);
						
						if((r.nextInt(100) < blockChance))
						{
							baseDamage = 0.0;
							return; // Hops out of the event handling since it was blocked
						}
						
						// Check for critical chance
						critChance = bd.getCombatAttribute(PlayerCombatAttribute.CRITICAL_STRIKE);
						
						if((r.nextInt(100) < critChance))
						{
							baseDamage = baseDamage * 1.3;
						}
						
						// Multiply for outgoing damage buffs
						baseDamage = baseDamage * bd.getOutgoingDamageMultiplier();
						
						// Apply incoming damage wards
						baseDamage = baseDamage * be.getIncomingDamageMultiplier();
						
						// Resync the entity's health
						be.setHealth((be.getHealth()-baseDamage));
						be.setName(WordUtils.capitalizeFully(e.getType().toString()));
					}
					else
					{
						MMOPro.log.severe("Unable to determine the source of a damage event on Entity " + e.getEntityId());
					}
					// Note: Spell Damage is handled in EntityDamageEvent as effect damage
					// This handler only handles direct physical damage
					
					
					if(be.getHealth() < 0) ((LivingEntity) e).setHealth(0.00);
				}
				else
				{
					MMOPro.log.severe("Mob entered combat without valid attributes.");
					event.getEntity().teleport(new Location(e.getWorld(),-15000.0,1000.0,-15000.0));
					MMOPro.log.severe("BUGSQUASHER: Teleported mob to bugged zone.");
				}
			}
			
		}
	}
	@EventHandler (priority = EventPriority.LOW)
	public void OnEntityDeath(EntityDeathEvent event)
	{
		if((event.getEntity() instanceof LivingEntity) && !(event.getEntity() instanceof Player))
		{
			LivingEntity le = (LivingEntity) event.getEntity();
			
			if(le.getKiller() instanceof Player)
			{
				Player killer = (Player) le.getKiller();
				
				Gladiator g = MMOPro.players.get(killer.getName());
				BattleEntity be = MMOPro.mobs.get(le.getEntityId());
				
				int diff = g.getLevel() - be.getLevel();
				int xp = 0;
				if(diff < -3) xp = 8 * be.getLevel();
				else if(diff >= -3 && diff < -1) xp = 6 * be.getLevel();
				else if(diff >= -1 && diff <= 1) xp = 4 * be.getLevel();
				else if(diff > 1 && diff <= 3) xp = 2 * be.getLevel();
				else if(diff > 3) xp = 0;
				
				if(xp > 0) g.addExperience(xp);
			}
			
			MMOPro.mobs.remove(event.getEntity().getEntityId());
		}
	}
}
