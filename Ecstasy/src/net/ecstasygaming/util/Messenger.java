package net.ecstasygaming.util;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Messenger {

	public static Logger log = Logger.getLogger("Minecraft");
	
	public static void toPlayer(Player target, String message, MessageType t)
	{
		switch(t)
		{
		case COMBAT:
			log.info("[Combat][Player: " + target.getName() + "] " + message);
			break;
		case ERROR:
			log.info("[Error][Player: " + target.getName() + "] " + message);
			break;
		case MESSAGE:
			log.info("[Info][Player: " + target.getName() + "] " + message);
			break;
		case WARNING:
			log.info("[Warning][Player: " + target.getName() + "] " + message);
			break;
		default:
			break;
		}
		
		target.sendMessage(getPrefix(t) + message);
	}
	public static void toServer(String message, MessageType t)
	{
		switch(t)
		{
		case COMBAT:
			log.info("[Combat][Server] " + message);
			break;
		case ERROR:
			log.info("[Error][Server] " + message);
			break;
		case MESSAGE:
			log.info("[Info][Server] " + message);
			break;
		case WARNING:
			log.info("[Warning][Server] " + message);
			break;
		default:
			break;
		}
		
		Bukkit.broadcastMessage(getPrefix(t) + message);
	}
	private static String getPrefix(MessageType t)
	{
		switch(t)
		{
		case COMBAT:
			return "" + ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Combat" + ChatColor.DARK_GRAY + "]: " + ChatColor.GRAY;
		case ERROR:
			return "" + ChatColor.DARK_RED + "[" + ChatColor.RED + "!" + ChatColor.DARK_RED + "]: " + ChatColor.RED;
		case MESSAGE:
			return "" + ChatColor.DARK_BLUE + "[" + ChatColor.BLUE + "!" + ChatColor.DARK_BLUE + "]: " + ChatColor.BLUE;
		case WARNING:
			return "" + ChatColor.GOLD + "[" + ChatColor.YELLOW + "!" + ChatColor.GOLD + "]: " + ChatColor.YELLOW;
		default:
			return "" + ChatColor.DARK_BLUE + "[" + ChatColor.BLUE + "!" + ChatColor.DARK_BLUE + "]: " + ChatColor.BLUE;
		}
	}
}
