package net.ecstasygaming.util;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.ecstasygaming.Ecstasy;
import net.ecstasygaming.entity.Gladiator;
import net.ecstasygaming.objects.DropType;
import net.ecstasygaming.objects.EcstasyItem;
import net.ecstasygaming.objects.ItemRarity;
import net.md_5.bungee.api.ChatColor;

public class LootManager {

	public static void init()
	{ // Initialize the items in here and add them to the main index
		Ecstasy.items.put("GAMEMASTER HOOD", new EcstasyItem("Gamemaster Hood", ItemRarity.LEGENDARY, 1, 96, Material.LEATHER_HELMET, "Armor", 1, 900.0, 800.0, 700.0, 600.0, 500.0, 750.0, 100.0, 50.0, DropType.ALLOW_DROP));
		Ecstasy.items.put("GAMEMASTER'S ROBE TOP", new EcstasyItem("Gamemaster's Robe Top", ItemRarity.LEGENDARY, 1, 96, Material.LEATHER_CHESTPLATE, "Armor", 1, 900.0, 800.0, 700.0, 600.0, 500.0, 750.0, 100.0, 50.0, DropType.DISALLOW_DROP));
		Ecstasy.items.put("GAMEMASTER'S ROBE BOTTOM", new EcstasyItem("Gamemaster's Robe Bottom", ItemRarity.LEGENDARY, 1, 96, Material.LEATHER_LEGGINGS, "Armor", 1, 900.0, 800.0, 700.0, 600.0, 500.0, 750.0, 100.0, 50.0, DropType.DISALLOW_DROP));
		Ecstasy.items.put("GAMEMASTER'S SLIPPERS", new EcstasyItem("Gamemaster's Slippers", ItemRarity.LEGENDARY, 1, 96, Material.LEATHER_BOOTS, "Armor", 1, 900.0, 800.0, 700.0, 600.0, 500.0, 750.0, 100.0, 50.0, DropType.DISALLOW_DROP));
		Ecstasy.items.put("MYTHIC STEEL SWORD OF THE SAVIOR", new EcstasyItem("Mythic Steel Sword of the Savior", ItemRarity.LEGENDARY, 1, 96, Material.IRON_SWORD, "Sword", 1, 900.0, 800.0, 700.0, 600.0, 500.0, 0.0, 100.0, 50.0, DropType.DISALLOW_DROP,5000.0,10000.0));
		Ecstasy.items.put("MYTHIC COMPOUND BOW OF THE SAVIOR", new EcstasyItem("Mythic Compound Bow of the Savior", ItemRarity.LEGENDARY, 1, 96, Material.IRON_SWORD, "Bow", 1, 900.0, 800.0, 700.0, 600.0, 500.0, 0.0, 100.0, 50.0, DropType.DISALLOW_DROP,5000.0,10000.0));
		
		// Add all the default (Vanilla) items as EcstasyItems
		int i;
		Material m;
		for(i = 1; i < 384; i++)
		{
			m = Material.values()[i];
			switch(m)
			{
			case DIAMOND:
			case EMERALD:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.RARE,1,72,m,"Gem",1,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.ALLOW_DROP));
				break;
			case IRON_INGOT:
			case GOLD_INGOT:
			case REDSTONE:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.UNCOMMON,1,36,m,"Item",1,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.ALLOW_DROP));
				break;
			case WOOD_SPADE:
			case WOOD_PICKAXE:
			case WOOD_AXE:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.POOR,1,1,m,"Tool",1,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.ALLOW_DROP,6,10));
				break;
			case WOOD_SWORD:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.POOR,1,1,m,"Sword",1,20.0,20.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.ALLOW_DROP,25,40));
				break;
			case BOW:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.POOR,1,1,m,"Bow",1,0.0,20.0,0.0,0.0,40.0,0.0,0.0,0.0,DropType.ALLOW_DROP,50,90));
				break;
			case STONE_SPADE:
			case STONE_PICKAXE:
			case STONE_AXE:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.COMMON,5,6,m,"Tool",1,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.ALLOW_DROP,12,20));
				break;
			case STONE_SWORD:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.COMMON,5,6,m,"Sword",1,40.0,40.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.ALLOW_DROP, 50, 80));
				break;
			case IRON_SPADE:
			case IRON_PICKAXE:
			case IRON_AXE:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.COMMON,10,12,m,"Tool",1,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.ALLOW_DROP, 18, 30));
				break;
			case IRON_SWORD:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.COMMON,10,12,m,"Sword",1,60.0,60.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.ALLOW_DROP, 75, 120));
				break;
			case GOLD_SPADE:
			case GOLD_PICKAXE:
			case GOLD_AXE:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.UNCOMMON,20,18,m,"Tool",1,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.DISALLOW_DROP, 24, 40));
				break;
			case GOLD_SWORD:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.UNCOMMON,20,18,m,"Sword",1,80.0,80.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.DISALLOW_DROP, 100, 160));
				break;
			case DIAMOND_SPADE:
			case DIAMOND_PICKAXE:
			case DIAMOND_AXE:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.UNCOMMON,30,24,m,"Tool",1,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.DISALLOW_DROP, 30, 50));
				break;
			case DIAMOND_SWORD:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.UNCOMMON,30,24,m,"Sword",1,100.0,100.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.DISALLOW_DROP, 125, 200));
				break;
			case LEATHER_CHESTPLATE:
			case LEATHER_LEGGINGS:
			case LEATHER_BOOTS:
			case LEATHER_HELMET:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.COMMON,5,6,m,"Armor",1,40.0,40.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.ALLOW_DROP));
				break;
			case IRON_CHESTPLATE:
			case IRON_LEGGINGS:
			case IRON_BOOTS:
			case IRON_HELMET:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.COMMON,10,12,m,"Armor",1,60.0,60.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.ALLOW_DROP));
				break;
			case GOLD_CHESTPLATE:
			case GOLD_LEGGINGS:
			case GOLD_BOOTS:
			case GOLD_HELMET:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.UNCOMMON,20,18,m,"Armor",1,80.0,80.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.DISALLOW_DROP));
				break;
			case DIAMOND_CHESTPLATE:
			case DIAMOND_LEGGINGS:
			case DIAMOND_BOOTS:
			case DIAMOND_HELMET:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.UNCOMMON,30,24,m,"Armor",1,100.0,100.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.DISALLOW_DROP));
				break;
			case CHAINMAIL_CHESTPLATE:
			case CHAINMAIL_LEGGINGS:
			case CHAINMAIL_BOOTS:
			case CHAINMAIL_HELMET:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.UNCOMMON,40,36,m,"Armor",1,160.0,160.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.DISALLOW_DROP));
				break;
			default:
				Ecstasy.items.put(m.toString(), new EcstasyItem(WordUtils.capitalizeFully(m.toString()),ItemRarity.COMMON,1,1,m,"Item",1,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,DropType.ALLOW_DROP));
			}
			
		}
	}

	public static void givePlayerItem(Gladiator g, String itemname, int amount)
	{
		int i = 0;
		while(i < amount)
		{
			g.getPlayer().getInventory().addItem(Ecstasy.items.get(itemname).getItemStack());
			g.getPlayer().sendMessage(ChatColor.YELLOW + "You have received loot: " + Ecstasy.items.get(itemname).getItemTag() + ChatColor.YELLOW + ".");
			i++;
		}
	}
	public static void givePlayerItem(Player p, String itemname, int amount)
	{
		int i = 0;
		while(i < amount)
		{
			p.getInventory().addItem(Ecstasy.items.get(itemname).getItemStack());
			p.sendMessage(ChatColor.YELLOW + "You have received loot: " + Ecstasy.items.get(itemname).getItemTag() + ChatColor.YELLOW + ".");
			i++;
		}
	}
}
