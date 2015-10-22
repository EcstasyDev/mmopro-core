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

import net.ecstasygaming.Ecstasy;
import net.ecstasygaming.entity.BattleEntity;
import net.ecstasygaming.entity.Gladiator;
import net.ecstasygaming.entity.PlayerCombatAttribute;
import net.ecstasygaming.objects.EcstasyItem;
import net.ecstasygaming.util.MessageType;
import net.ecstasygaming.util.Messenger;

public class MobSpawningHandler implements Listener {
	
	Ecstasy plugin;
	public MobSpawningHandler(Ecstasy plugin)
	{
		Ecstasy.log.info("Linked Mob Spawning handler.");
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
		
		int LMax = 50, LMin = 1;
		
		BattleEntity be = new BattleEntity(e);
		
		// TODO: Generate level based on zone restrictions
		// TODO: FIRST CREATE A SYSTEM TO DEFINE ZONES
		
		Random r = new Random();
		level = r.nextInt(Math.abs(LMax-LMin+1))+LMin;
		
		be.setLevel(level);
		
		if(elite)
		{
			be.setCombatAttribute(PlayerCombatAttribute.STAMINA, (level*184.5));
			be.setCombatAttribute(PlayerCombatAttribute.STRENGTH, (level*184.5));
			be.setCombatAttribute(PlayerCombatAttribute.RANGED, (level*184.5));
			be.setCombatAttribute(PlayerCombatAttribute.INTELLECT, (level*184.5));
			be.setCombatAttribute(PlayerCombatAttribute.DISCIPLINE, (level*184.5));
			
			be.setCombatAttribute(PlayerCombatAttribute.DODGE, (level*184.5));
			be.setCombatAttribute(PlayerCombatAttribute.CRITICAL_STRIKE, (level*184.5));
		}
		else
		{
			be.setCombatAttribute(PlayerCombatAttribute.STAMINA, (level*65.5));
			be.setCombatAttribute(PlayerCombatAttribute.STRENGTH, (level*65.5));
			be.setCombatAttribute(PlayerCombatAttribute.RANGED, (level*65.5));
			be.setCombatAttribute(PlayerCombatAttribute.INTELLECT, (level*65.5));
			be.setCombatAttribute(PlayerCombatAttribute.DISCIPLINE, (level*65.5));
			
			be.setCombatAttribute(PlayerCombatAttribute.DODGE, (level*65.5));
			be.setCombatAttribute(PlayerCombatAttribute.CRITICAL_STRIKE, (level*65.5));
		}
		
		health = be.getCombatAttribute(PlayerCombatAttribute.STAMINA) * 6.25;
		
		be.setElite(elite);
		if(elite) be.setMaxHealth(health);
		else be.setMaxHealth(health);
		
		be.setHealth(health);
		
		be.setName(WordUtils.capitalizeFully(e.getName()));
		
		Ecstasy.mobs.put(e.getEntityId(), be);
		
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
				if(Ecstasy.mobs.containsKey(e.getEntityId()))
				{
					event.setDamage(0.00); // cancels the native event damage
					BattleEntity be = Ecstasy.mobs.get(e.getEntityId());
					
					double baseDamage = 0.00;
					double critChance = 0.00;
					double blockChance = 0.00;
					int maxHit = 0;
					int minHit = 0;
					Random r = new Random();
					if(event.getDamager() instanceof Player)
					{ // Player directly caused the damage
						Player p = (Player) event.getDamager();
						Gladiator g = Ecstasy.players.get(p.getName());
						
						
						// Retrieve base damage
						if(p.getItemInHand() != null)
						{
							ItemStack is = p.getItemInHand();
							
							if(is.hasItemMeta())
							{
								if(Ecstasy.items.containsKey(is.getItemMeta().getDisplayName().toUpperCase()))
								{
									EcstasyItem i = Ecstasy.items.get(is.getItemMeta().getDisplayName().toUpperCase());
									
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
						
						baseDamage = (double) (r.nextInt((Math.abs(maxHit-minHit+1)))+minHit)+1.00;
						
						// Apply a strength bonus to the base weapon damage
						// Stronger characters will do more melee damage this way
						// Meaning, better armor (with Strength bonuses) allow player to do more damage than players without armor
						// This puts an emphasis on having decent armor while PVP/PVEing
						// !IMPORTANT! All stat figures during testing are based on a character with all gear slots filled
						// Meaning, if a player is missing a single gear slot, their damage could be reduced by upwards of 20%
						baseDamage = baseDamage + (g.getCombatAttribute(PlayerCombatAttribute.STRENGTH) / 6.00) + (g.getLevel() * 17.25);
						
						
						// Check for enemy block chance
						blockChance = g.getCombatAttribute(PlayerCombatAttribute.DODGE);
						
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
							// Player p = (Player) a.getShooter();
							// Gladiator g = Ecstasy.players.get(p.getName());
						}
						else if(event.getDamager() instanceof LivingEntity)
						{ // NPC Living Entity shot the arrow
							
						}
						else
						{ // Arrow was shot by a cannon or dispenser (or null source)
							
						}
					}
					else if(event.getDamager() instanceof LivingEntity)
					{
						LivingEntity le = (LivingEntity) event.getDamager();
						be = Ecstasy.mobs.get(le.getEntityId());
						
						maxHit = (int) be.getCombatAttribute(PlayerCombatAttribute.STRENGTH)/9;
						minHit = (int) be.getCombatAttribute(PlayerCombatAttribute.STRENGTH)/13;
						
						// Determine the base hit
						baseDamage = r.nextInt((Math.abs(maxHit-minHit)+1)) + minHit;
						
						// Check for critical chance
					}
					else
					{
						Ecstasy.log.severe("Unable to determine the source of a damage event on Entity " + e.getEntityId());
					}
					// Note: Spell Damage is handled in EntityDamageEvent as effect damage
					// This handler only handles direct physical damage
					
					
					if(be.getHealth() < 0) ((LivingEntity) e).setHealth(0.00);
				}
				else
				{
					Ecstasy.log.severe("Mob entered combat without valid attributes.");
					event.getEntity().teleport(new Location(e.getWorld(),-15000.0,1000.0,-15000.0));
					Ecstasy.log.severe("BUGSQUASHER: Teleported mob to bugged zone.");
				}
			}
			else
			{ // Player is the victim
				Player p = (Player) e;
				
				if(Ecstasy.players.containsKey(p.getName()))
				{
					Gladiator g = Ecstasy.players.get(p.getName());
					
					if(event.getDamager() instanceof Player)
					{
						Player damager = (Player) event.getDamager();
						Gladiator h = Ecstasy.players.get(damager.getName());
						
						
					}
					else if(event.getDamager() instanceof Arrow)
					{
						Arrow a = (Arrow) event.getDamager();
						
						if(a.getShooter() instanceof Player)
						{ // Player shot the arrow
							// Player p = (Player) a.getShooter();
							// Gladiator g = Ecstasy.players.get(p.getName());
						}
						else if(event.getDamager() instanceof LivingEntity)
						{ // NPC Living Entity shot the arrow
							
						}
						else
						{ // Arrow was shot by a cannon or dispenser (or null source)
							
						}
					}
					else if(event.getDamager() instanceof LivingEntity)
					{
						
					}
					else
					{
						Ecstasy.log.severe("Unable to determine the source of a damage event on Player " + p.getName());
					}
				}
			}
		}
	}
	@EventHandler (priority = EventPriority.LOW)
	public void OnEntityDeath(EntityDeathEvent event)
	{
		
	}
}
