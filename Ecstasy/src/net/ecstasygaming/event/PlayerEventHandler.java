package net.ecstasygaming.event;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import net.ecstasygaming.MMOPro;
import net.ecstasygaming.combat.PlayerCombatAttribute;
import net.ecstasygaming.entity.BattleEntity;
import net.ecstasygaming.entity.Gladiator;
import net.ecstasygaming.objects.EcstasyItem;
import net.ecstasygaming.util.MessageType;
import net.ecstasygaming.util.Messenger;
import net.md_5.bungee.api.ChatColor;

public class PlayerEventHandler implements Listener {

	MMOPro plugin;
	public PlayerEventHandler(MMOPro plugin)
	{
		MMOPro.log.info("Linked Player Combat handler.");
		this.plugin = plugin;
	}
	
	// Handles damage by the environment and other sources
	// Base of 9.5% damage
	// Falling is 1.5x (14.5% + (Add. 5.5% per 6 blocks))
	@EventHandler (priority = EventPriority.NORMAL)
	public void onPlayerTakeDamage(EntityDamageEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player p = (Player) event.getEntity();
			Gladiator g = MMOPro.players.get(p.getName());
			
			double baseDamage = 0.0;
			switch(event.getCause())
			{
			case BLOCK_EXPLOSION:
				baseDamage += (g.getMaxHealth() * 0.35);
				event.setDamage(0.00);
				break;
			case CONTACT:
				baseDamage += (g.getMaxHealth() * 0.01);
				event.setDamage(0.00);
				break;
			case CUSTOM:
				break;
			case DROWNING:
				baseDamage += (g.getMaxHealth() * 0.1);
				event.setDamage(0.00);
				break;
			case ENTITY_ATTACK:
				break;
			case ENTITY_EXPLOSION:
				baseDamage += (g.getMaxHealth() * 0.35);
				event.setDamage(0.00);
				break;
			case FALL:
				baseDamage += (g.getMaxHealth() * 0.145);
				event.setDamage(0.00);
				break;
			case FALLING_BLOCK:
				baseDamage += (g.getMaxHealth() * 0.05);
				event.setDamage(0.00);
				break;
			case FIRE:
				baseDamage += (g.getMaxHealth() * 0.05);
				event.setDamage(0.00);
				break;
			case FIRE_TICK:
				baseDamage += (g.getMaxHealth() * 0.05);
				event.setDamage(0.00);
				break;
			case LAVA:
				baseDamage += (g.getMaxHealth() * 0.1);
				event.setDamage(0.00);
				break;
			case LIGHTNING:
				baseDamage += (g.getMaxHealth() * 0.25);
				event.setDamage(0.00);
				break;
			case MAGIC:
				break;
			case MELTING:
				break;
			case POISON:
				break;
			case PROJECTILE:
				break;
			case STARVATION:
				baseDamage += (g.getMaxHealth() * 0.075);
				event.setDamage(0.00);
				break;
			case SUFFOCATION:
				baseDamage += (g.getMaxHealth() * 0.145);
				event.setDamage(0.00);
				break;
			case SUICIDE:
				baseDamage += (g.getMaxHealth() * 1.0);
				event.setDamage(0.00);
				break;
			case THORNS:
				break;
			case VOID:
				break;
			case WITHER:
				baseDamage += (g.getMaxHealth() * 0.005);
				event.setDamage(0.00);
				break;
			default:
				break;
			
			}
			
			g.setHealth((g.getHealth() - baseDamage));
		}
	}
	
	// Handles player taking damage from mob
	@SuppressWarnings("unused")
	@EventHandler (priority = EventPriority.NORMAL)
	public void onPlayerTakeDamage(EntityDamageByEntityEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player victim = (Player) event.getEntity();
			Gladiator v = MMOPro.players.get(victim.getName());
			
			Random r = new Random();
			double baseDamage = 0.00;
			double blockChance = 0.00;
			double critChance = 0.00;
			double maxHit = 0;
			double minHit = 0;
			
			if(event.getDamager() instanceof Player)
			{ // Handle PVP Damage
				Player damager = (Player) event.getDamager();
				Gladiator d = MMOPro.players.get(victim.getName());
				
				// Retrieve base damage
				if(damager.getItemInHand() != null)
				{
					ItemStack is = damager.getItemInHand();
					
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
				
				// Check for enemy block chance
				blockChance = v.getCombatAttribute(PlayerCombatAttribute.DODGE);
				
				if((r.nextInt(100) < blockChance))
				{
					baseDamage = 0.0;
					return; // Hops out of the event handling since it was blocked
				}
				
				// Check for critical chance
				critChance = d.getCombatAttribute(PlayerCombatAttribute.CRITICAL_STRIKE);
				
				if((r.nextInt(100) < critChance))
				{
					baseDamage = baseDamage * 1.3;
				}
				
				// Multiply for outgoing damage buffs
				baseDamage = baseDamage * d.getOutgoingDamageMultiplier();
				
				// Apply incoming damage wards
				baseDamage = baseDamage * v.getIncomingDamageMultiplier();
				
				Messenger.toPlayer(damager, "Dealt " + baseDamage + " damage to " + victim.getName() + " (", MessageType.COMBAT);
				Messenger.toPlayer(victim, "Received " + baseDamage + " damage from " + damager.getName(), MessageType.COMBAT);
				
				// Resync the entity's health
				v.setHealth((v.getHealth()-baseDamage));
				
			}
			else if(event.getDamager() instanceof LivingEntity)
			{ // Handle EVP Damage
				LivingEntity damager = (LivingEntity) event.getDamager();
				BattleEntity d = MMOPro.mobs.get(damager.getEntityId());
			}
			else if(event.getDamager() instanceof Arrow)
			{ // Handle (NLE)VP Damage
				LivingEntity damager = (LivingEntity) event.getDamager();
				BattleEntity d = MMOPro.mobs.get(damager.getEntityId());
			}
		}
	}
	@EventHandler (priority = EventPriority.LOW)
	public void OnPlayerDeath(PlayerDeathEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player victim = (Player) event.getEntity();
			
			if(victim.getKiller() instanceof Player)
			{
				Player killer = (Player) victim.getKiller();
				Gladiator g = MMOPro.players.get(killer.getName());
				Gladiator v = MMOPro.players.get(victim.getName());
				
				int diff = g.getLevel() - v.getLevel();
				int xp = 0;
				if(diff < -3) xp = 12 * v.getLevel();
				else if(diff >= -3 && diff < -1) xp = 9 * v.getLevel();
				else if(diff >= -1 && diff <= 1) xp = 6 * v.getLevel();
				else if(diff > 1 && diff <= 3) xp = 3 * v.getLevel();
				else if(diff > 3) xp = 0;
				
				killer.sendMessage(ChatColor.DARK_RED + "You have defeated " + ChatColor.RED + victim.getName() + " in combat!");
				
				if(xp > 0) g.addExperience(xp);
			}
			
			event.setDeathMessage("");
			// Determine the closest graveyard to respawn at
			Location closest = null;
			if(MMOPro.graveyards.size() > 0)
			{
				
				
				int i = 0;
				do
				{
					if(closest == null)
					{
						closest = MMOPro.graveyards.get(i);
					}
					else
					{
						if(MMOPro.graveyards.get(i).distance(victim.getLocation()) < closest.distance(victim.getLocation()))
						{
							closest = MMOPro.graveyards.get(i);
						}
					}
					
				} while(i < MMOPro.graveyards.size());
			}
			else
			{
				if(victim.getBedSpawnLocation() != null)
				{
					closest = victim.getBedSpawnLocation();
				}
				else
				{
					closest = victim.getWorld().getSpawnLocation();
				}
			}
			
			MMOPro.respawnLocation.put(victim.getName(), closest);
			
		}
	}
	@EventHandler (priority = EventPriority.LOW)
	public void OnPlayerRespawn(PlayerRespawnEvent event)
	{
		if(MMOPro.respawnLocation.containsKey(event.getPlayer().getName()))
		{
			event.getPlayer().teleport(MMOPro.respawnLocation.get(event.getPlayer().getName()));
			Messenger.toPlayer(event.getPlayer(), "You have been respawned at the nearest graveyard.", MessageType.MESSAGE);
		}
	}
	@EventHandler (priority = EventPriority.HIGH)
	public void OnPlayerConnect(PlayerJoinEvent event)
	{
		// Gladiator class extends Player indirectly
		Gladiator g = new Gladiator(event.getPlayer());
		MMOPro.players.put(event.getPlayer().getName(), g);
		
		g.loadInfo();
		
	}
	@EventHandler (priority = EventPriority.HIGH)
	public void OnPlayerDisconnect(PlayerQuitEvent event)
	{
		// Gladiator class extends Player indirectly
		Gladiator g = new Gladiator(event.getPlayer());
		
		g.saveInfo();
		
	}
}
