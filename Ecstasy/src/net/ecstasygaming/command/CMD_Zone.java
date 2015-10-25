package net.ecstasygaming.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ecstasygaming.MMOPro;
import net.ecstasygaming.combat.Zone;
import net.ecstasygaming.util.MessageType;
import net.ecstasygaming.util.Messenger;

public class CMD_Zone implements CommandExecutor {

	MMOPro plugin;
	public CMD_Zone(MMOPro plugin) {
		this.plugin = plugin;
		
		MMOPro.log.info("Registered command 'zone'.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("zone"))
		{
			Player p = (Player) sender;
			if(MMOPro.permission.has(sender, "mmopro.mod.zone"))
			{
				if(args.length < 1)
				{
					Messenger.toPlayer(p, "Syntax: /zone [option] (params)", MessageType.ERROR);
					return true;
				}
				else
				{
					if(args[0].equalsIgnoreCase("create"))
					{
						if(!MMOPro.permission.has(sender, "mmopro.mod.zone.create") && !MMOPro.permission.has(sender, "mmopro.mod.zone.*"))
						{
							Messenger.toPlayer(p, "Error: You do not have permission.", MessageType.ERROR);
							return true;
						}
						
						if(args.length < 4)
						{
							Messenger.toPlayer(p, "Syntax: /zone create (minLevel) (maxLevel) (name)", MessageType.ERROR);
							return true;
						}
						else
						{ // Execute the command
							if(MMOPro.worldedit.getSelection(p) == null)
							{
								Messenger.toPlayer(p, "Error: You do not have a region selected.", MessageType.ERROR);
								return true;
							}
							else
							{
								
								if(MMOPro.zones.containsKey(args[3].toUpperCase()))
								{
									Messenger.toPlayer(p, "Error: Zone already exists with this name.", MessageType.ERROR);
									return true;
								}
								else if(args[3].equalsIgnoreCase("zones"))
								{
									Messenger.toPlayer(p, "Error: Zone Name 'zones' is reserved.", MessageType.ERROR);
									return true;
								}
								
								Zone z = new Zone(args[3].toUpperCase(), MMOPro.worldedit.getSelection(p));
								
								try
								{ 
									if(Integer.valueOf(args[1]) >= Integer.valueOf(args[2]))
									{
										Messenger.toPlayer(p, "Error: Maximum level bound must exceed minimum level bound.", MessageType.ERROR);
										return true;
									}
									z.setMinimumZoneLevel(Integer.valueOf(args[1]));
									z.setMaximumZoneLevel(Integer.valueOf(args[2]));
								}
								catch(NumberFormatException e) { 
									Messenger.toPlayer(p, "Error: Invalid level bound.", MessageType.ERROR);
									return true;
								}
								
								MMOPro.zones.put(z.getZoneName(), z);
								Zone.saveZones();
								
								Messenger.toPlayer(p, "Created new region: '" + z.getZoneName() + "' (Level Bounds: " + z.getMinimumZoneLevel() + "/" + z.getMaximumZoneLevel() + ")", MessageType.MESSAGE);
								return true;
							}
						}
					}
					else if(args[0].equalsIgnoreCase("delete"))
					{
						if(!MMOPro.permission.has(sender, "mmopro.mod.zone.delete") && !MMOPro.permission.has(sender, "mmopro.mod.zone.*"))
						{
							Messenger.toPlayer(p, "Error: You do not have permission.", MessageType.ERROR);
							return true;
						}
						
						if(args.length < 2)
						{
							Messenger.toPlayer(p, "Syntax: /zone delete (name)", MessageType.ERROR);
							return true;
						}
						else
						{ // Execute the command
							if(MMOPro.zones.containsKey(args[1].toUpperCase()))
							{
								MMOPro.zones.remove(args[1]);
								List<String> zones = MMOPro.config_zones.getStringList("zones");
								zones.remove(args[1]);
								MMOPro.config_zones.set("zones",zones);
								
								Messenger.toPlayer(p, "Deleted zone: '" + args[1].toUpperCase() +"'", MessageType.MESSAGE);
								return true;
							}
							else
							{
								Messenger.toPlayer(p, "Error: Zone '" + args[1].toUpperCase() +"' does not exist.", MessageType.MESSAGE);
								return true;
							}
						}
					}
					else if(args[0].equalsIgnoreCase("set"))
					{
						if(!MMOPro.permission.has(sender, "mmopro.mod.zone.set") && !MMOPro.permission.has(sender, "mmopro.mod.zone.*"))
						{
							Messenger.toPlayer(p, "Error: You do not have permission.", MessageType.ERROR);
							return true;
						}
						
						if(args.length < 3)
						{
							Messenger.toPlayer(p, "Syntax: /zone set (zone) (min/max/name/raidzone/elites) (value)", MessageType.ERROR);
							return true;
						}
						else
						{ // Execute the command
							if(args[2].equalsIgnoreCase("min"))
							{
								if(args.length < 3)
								{
									Messenger.toPlayer(p, "Syntax: /zone set (zone) " + args[1].toLowerCase() + " (value)", MessageType.ERROR);
									Messenger.toPlayer(p, "Syntax: Controls the minimum level possible for monsters spawning here.", MessageType.ERROR);
									return true;
								}
								else
								{
									int n = 0;
									try
									{
										n = Integer.valueOf(args[3]);
									}
									catch(NumberFormatException e)
									{
										Messenger.toPlayer(p, "Error: Input must be numeric.", MessageType.ERROR);
										return true;
									}
									finally
									{
										n = 0;
									}
									
									MMOPro.zones.get(args[1].toUpperCase());
									
									Zone zx = MMOPro.zones.get(args[1].toUpperCase());
									zx.setMinimumZoneLevel(n);
									MMOPro.zones.put(args[1].toUpperCase(), zx);
									
									Messenger.toPlayer(p, "Modified zone: '" + args[1].toUpperCase() +"'", MessageType.MESSAGE);
									
									Zone.saveZones();
								}
							}
							else if(args[2].equalsIgnoreCase("max"))
							{
								if(args.length < 3)
								{
									Messenger.toPlayer(p, "Syntax: /zone set (zone) " + args[1].toLowerCase() + " (value)", MessageType.ERROR);
									Messenger.toPlayer(p, "Syntax: Controls the maximum level possible for monsters spawning here.", MessageType.ERROR);
									return true;
								}
								else
								{
									int n = 0;
									try
									{
										n = Integer.valueOf(args[3]);
									}
									catch(NumberFormatException e)
									{
										Messenger.toPlayer(p, "Error: Input must be numeric.", MessageType.ERROR);
										return true;
									}
									finally
									{
										n = 0;
									}
									
									MMOPro.zones.get(args[1].toUpperCase());
									
									Zone zx = MMOPro.zones.get(args[1].toUpperCase());
									zx.setMaximumZoneLevel(n);
									MMOPro.zones.put(args[1].toUpperCase(), zx);
									
									Messenger.toPlayer(p, "Modified zone: '" + args[1].toUpperCase() +"'", MessageType.MESSAGE);
									
									Zone.saveZones();
								}
							}
							else if(args[2].equalsIgnoreCase("raidzone"))
							{
								if(args.length < 3)
								{
									Messenger.toPlayer(p, "Syntax: /zone set (zone) " + args[1].toLowerCase() + " (value)", MessageType.ERROR);
									Messenger.toPlayer(p, "Syntax: (1) for Raid Zone / (0) for Normal Zone", MessageType.ERROR);
									Messenger.toPlayer(p, "Syntax: Controls whether this zone is a raid zone with enhanced loot.", MessageType.ERROR);
									return true;
								}
								else
								{
									int n = 0;
									try
									{
										n = Integer.valueOf(args[3]);
									}
									catch(NumberFormatException e)
									{
										Messenger.toPlayer(p, "Error: Input must be numeric.", MessageType.ERROR);
										return true;
									}
									finally
									{
										n = 0;
									}
									
									MMOPro.zones.get(args[1].toUpperCase());
									
									Zone zx = MMOPro.zones.get(args[1].toUpperCase());
									if(n == 1) zx.setRaidZone(true);
									else zx.setRaidZone(false);
									MMOPro.zones.put(args[1].toUpperCase(), zx);
									
									Messenger.toPlayer(p, "Modified zone: '" + args[1].toUpperCase() +"'", MessageType.MESSAGE);
									
									Zone.saveZones();
								}
							}
							else if(args[2].equalsIgnoreCase("elites"))
							{
								if(args.length < 3)
								{
									Messenger.toPlayer(p, "Syntax: /zone set (zone) " + args[1].toLowerCase() + " (value)", MessageType.ERROR);
									Messenger.toPlayer(p, "Syntax: Controls whether this zone will spawn only elite monsters.", MessageType.ERROR);
									return true;
								}
								else
								{
									int n = 0;
									try
									{
										n = Integer.valueOf(args[3]);
									}
									catch(NumberFormatException e)
									{
										Messenger.toPlayer(p, "Error: Input must be numeric.", MessageType.ERROR);
										return true;
									}
									
									MMOPro.zones.get(args[1].toUpperCase());
									
									Zone zx = MMOPro.zones.get(args[1].toUpperCase());
									if(n == 1) zx.setRaidZone(true);
									else zx.setRaidZone(false);
									MMOPro.zones.put(args[1].toUpperCase(), zx);
									
									Messenger.toPlayer(p, "Modified zone: '" + args[1].toUpperCase() +"'", MessageType.MESSAGE);
									
									Zone.saveZones();
								}
							}
							else
							{
								Messenger.toPlayer(p, "Error: Property not found.", MessageType.ERROR);
								return true;
							}
						}
					}
					else if(args[0].equalsIgnoreCase("list"))
					{
						if(!MMOPro.permission.has(sender, "mmopro.mod.zone.list") && !MMOPro.permission.has(sender, "mmopro.mod.zone.*"))
						{
							Messenger.toPlayer(p, "Error: You do not have permission.", MessageType.ERROR);
							return true;
						}
						else
						{
							Object[] keys = MMOPro.zones.keySet().toArray();
							if(args.length < 3)
							{
								int i;
								Messenger.toPlayer(p, "Syntax: /zone list (start) (end)", MessageType.ERROR);
								for(i = 0; i < MMOPro.zones.size(); i++)
								{
									Messenger.toPlayer(p, i + ". Zone: '" + keys[i] +"'", MessageType.MESSAGE);
								}
								
								return true;
							}
							else
							{ // Between two indices
								int i = 0;
								int s = 0, e = 1;
								
								try
								{
									s = Integer.valueOf(args[1]);
									e = Integer.valueOf(args[2]);
								} catch(NumberFormatException ex)
								{
									Messenger.toPlayer(p, "Error: Bounds must be numeric.", MessageType.ERROR);
									return true;
								}
								
								if(e > MMOPro.zones.size()) e = MMOPro.zones.size();
								if(e < s) e = s + 1;
								if(s >= e) s = e - 1;
								
								if(s < 0) s = 0;
								
								Messenger.toPlayer(p, "Syntax: /zone list (start) (end)", MessageType.ERROR);
								for(i = s; i < e; i++)
								{
									Messenger.toPlayer(p, i + ". Zone: '" + keys[i] +"'", MessageType.MESSAGE);
								}
								
								return true;
							}
						}
					}
					else if(args[0].equalsIgnoreCase("info"))
					{
						if(!MMOPro.permission.has(sender, "mmopro.mod.zone.info") && !MMOPro.permission.has(sender, "mmopro.mod.zone.*"))
						{
							Messenger.toPlayer(p, "Error: You do not have permission.", MessageType.ERROR);
							return true;
						}
						if(args.length < 2)
						{
							Messenger.toPlayer(p, "Syntax: /zone info (zone)", MessageType.ERROR);
							return true;
						}
						else
						{ // Execute the command
							Messenger.toPlayer(p, "Zone Info: '" + args[1].toUpperCase() + "'", MessageType.MESSAGE);
							Zone z = MMOPro.zones.get(args[1].toUpperCase());
							Messenger.toPlayer(p, "Level Bounds: (" + z.getMinimumZoneLevel() + "," + z.getMaximumZoneLevel() + ")", MessageType.MESSAGE);
							Messenger.toPlayer(p, "Raid Zone: " + Boolean.toString(z.isRaidZone()).toUpperCase() + "", MessageType.MESSAGE);
							Messenger.toPlayer(p, "Elite Zone: " + Boolean.toString(z.isEliteMonstersAllowed()).toUpperCase() + "", MessageType.MESSAGE);
							return true;
						}
					}
				}
			}
			else
			{
				Messenger.toPlayer(p, "Error: You do not have permission.", MessageType.ERROR);
				return true;
			}
		}
		
		return false;
	}

	
}
