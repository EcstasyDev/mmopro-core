package net.ecstasygaming.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import net.ecstasygaming.Ecstasy;
import net.ecstasygaming.entity.Gladiator;

public class PlayerEventHandler implements Listener {

	Ecstasy plugin;
	public PlayerEventHandler(Ecstasy plugin)
	{
		Ecstasy.log.info("Linked Player Combat handler.");
		this.plugin = plugin;
	}
	
	// Handles damage by the environment and other sources
	// Base of 9.5% damage
	// Falling is 1.5x (14.5% + (Add. 5.5% per 6 blocks))
	@EventHandler (priority = EventPriority.HIGH)
	public void onPlayerTakeDamage(EntityDamageEvent event)
	{
		
	}
	
	// Handles player taking damage from mob
	@EventHandler (priority = EventPriority.HIGH)
	public void onPlayerTakeDamage(EntityDamageByEntityEvent event)
	{
		
	}
	@EventHandler (priority = EventPriority.LOW)
	public void OnPlayerDeath(PlayerDeathEvent event)
	{
		
	}
	@EventHandler (priority = EventPriority.LOW)
	public void OnPlayerRespawn(PlayerRespawnEvent event)
	{
	}
	@EventHandler (priority = EventPriority.LOW)
	public void OnPlayerConnect(PlayerJoinEvent event)
	{
		// Gladiator class extends Player indirectly
		Gladiator g = new Gladiator(event.getPlayer());
		Ecstasy.players.put(event.getPlayer().getName(), g);
		
		g.loadInfo();
		
	}
}
