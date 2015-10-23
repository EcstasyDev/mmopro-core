package net.ecstasygaming.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ecstasygaming.MMOPro;
import net.ecstasygaming.util.MessageType;
import net.ecstasygaming.util.Messenger;
import net.ecstasygaming.util.Zone;

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
								Zone z = new Zone(MMOPro.build(3, args), MMOPro.worldedit.getSelection(p));
								
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
								catch(NumberFormatException e) { Messenger.toPlayer(p, "Error: Invalid level bound.", MessageType.ERROR); }
								
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
							Messenger.toPlayer(p, "Syntax: /zone delete (id)", MessageType.ERROR);
							return true;
						}
						else
						{ // Execute the command
							
						}
					}
					else if(args[0].equalsIgnoreCase("set"))
					{
						if(!MMOPro.permission.has(sender, "mmopro.mod.zone.set") && !MMOPro.permission.has(sender, "mmopro.mod.zone.*"))
						{
							Messenger.toPlayer(p, "Error: You do not have permission.", MessageType.ERROR);
							return true;
						}
						
						if(args.length < 4)
						{
							Messenger.toPlayer(p, "Syntax: /zone set (min/max/name/raidzone/elites) (value)", MessageType.ERROR);
							return true;
						}
						else
						{ // Execute the command
							
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
							
						}
					}
					else if(args[0].equalsIgnoreCase("info"))
					{
						if(!MMOPro.permission.has(sender, "mmopro.mod.zone.info") && !MMOPro.permission.has(sender, "mmopro.mod.zone.*"))
						{
							Messenger.toPlayer(p, "Error: You do not have permission.", MessageType.ERROR);
							return true;
						}
						if(args.length < 1)
						{
							Messenger.toPlayer(p, "Syntax: /zone set (min/max/name) (value)", MessageType.ERROR);
							return true;
						}
						else
						{ // Execute the command
							
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
