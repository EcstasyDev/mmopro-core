package net.ecstasygaming.objects;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.ecstasygaming.Ecstasy;
import net.ecstasygaming.entity.Gladiator;
import net.md_5.bungee.api.ChatColor;

public class LootManager {

	public static void init()
	{ // Initialize the items in here and add them to the main index
		Ecstasy.items.put("GAMEMASTER HOOD", new EcstasyItem("Gamemaster Hood", ItemRarity.LEGENDARY, 1, 96, Material.LEATHER_HELMET, "Armor", 1, 900.0, 800.0, 700.0, 600.0, 500.0, 750.0, 100.0, 50.0, DropType.ALLOW_DROP));
		Ecstasy.items.put("GAMEMASTER ROBE TOP", new EcstasyItem("Gamemaster's Robe Top", ItemRarity.LEGENDARY, 1, 96, Material.LEATHER_CHESTPLATE, "Armor", 1, 900.0, 800.0, 700.0, 600.0, 500.0, 750.0, 100.0, 50.0, DropType.DISALLOW_DROP));
		Ecstasy.items.put("GAMEMASTER ROBE BOTTOM", new EcstasyItem("Gamemaster's Robe Bottom", ItemRarity.LEGENDARY, 1, 96, Material.LEATHER_LEGGINGS, "Armor", 1, 900.0, 800.0, 700.0, 600.0, 500.0, 750.0, 100.0, 50.0, DropType.DISALLOW_DROP));
		Ecstasy.items.put("GAMEMASTER SLIPPERS", new EcstasyItem("Gamemaster's Slippers", ItemRarity.LEGENDARY, 1, 96, Material.LEATHER_BOOTS, "Armor", 1, 900.0, 800.0, 700.0, 600.0, 500.0, 750.0, 100.0, 50.0, DropType.DISALLOW_DROP));
	}

	public static void givePlayerItem(Gladiator g, String itemname, int amount)
	{
		g.getPlayer().getInventory().addItem(Ecstasy.items.get(itemname).getItemStack());
		g.getPlayer().sendMessage(ChatColor.YELLOW + "You have received loot: " + Ecstasy.items.get(itemname).getItemTag() + ChatColor.YELLOW + ".");
	}
	public static void givePlayerItem(Player p, String itemname, int amount)
	{
		p.getInventory().addItem(Ecstasy.items.get(itemname).getItemStack());
		p.sendMessage(ChatColor.YELLOW + "You have received loot: " + Ecstasy.items.get(itemname).getItemTag() + ChatColor.YELLOW + ".");
	}
}
