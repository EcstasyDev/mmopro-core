package net.ecstasygaming.command;

import org.apache.commons.lang.WordUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ecstasygaming.MMOPro;
import net.ecstasygaming.util.LootManager;
import net.ecstasygaming.util.MessageType;
import net.ecstasygaming.util.Messenger;

public class CMD_Item implements CommandExecutor {

	MMOPro plugin;
	public CMD_Item(MMOPro plugin) {
		this.plugin = plugin;
		
		MMOPro.log.info("Registered command 'item'.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("item"))
		{
			Player p = (Player) sender;
			if(MMOPro.permission.has(sender, "mmopro.mod.item"))
			{
				if(args.length < 2)
				{
					Messenger.toPlayer(p, "Syntax: /item [amount] [item name]", MessageType.ERROR);
					return true;
				}
				else
				{
					String itemname = WordUtils.capitalizeFully(MMOPro.build(1,args));
					Messenger.toPlayer(p, "Looking up item '" + itemname + "' ...", MessageType.MESSAGE);
					if(MMOPro.items.containsKey(itemname.toUpperCase())) LootManager.givePlayerItem(p, itemname.toUpperCase(), Integer.parseInt(args[0]));
					else Messenger.toPlayer(p, "Item '" + itemname + "' does not exist.", MessageType.ERROR);
					return true;
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
