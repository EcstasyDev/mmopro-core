package net.ecstasygaming.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.ecstasygaming.entity.PlayerCombatAttribute;
import net.md_5.bungee.api.ChatColor;

public class EcstasyItem {

	// Basic Info
	private String item_name;
	private String item_type;
	private Material stack;
	private ItemRarity rarity;
	private int item_level;
	private int item_usage_level;
	private String tooltip;
	
	// Damage
	private double min_damage;
	private double max_damage;
	
	// Equippable Attributes (applies only to armor and weapons)
	private double att_strength;
	private double att_stamina;
	private double att_intellect;
	private double att_discipline;
	private double att_ranged;
	
	// Secondary Attributes (Modifiers)
	private double att_armor;
	private double att_dodge;
	private double att_crit;
	
	private DropType droptype = DropType.DISALLOW_DROP;
	
	private ItemStack is;
	
	public EcstasyItem(String name, ItemRarity rarity, int reqLevel, int itemlevel, Material stack, String type, int amount, DropType droptype)
	{
		this.item_name = name;
		this.rarity = rarity;
		this.item_level = itemlevel;
		this.stack = stack;
		this.item_type = type;
		this.item_usage_level = reqLevel;
		
		this.min_damage = 0.00;
		this.max_damage = 0.00;
		
		this.att_strength = 0.00;
		this.att_stamina = 0.00;
		this.att_intellect = 0.00;
		this.att_discipline = 0.00;
		this.att_ranged = 0.00;
		
		this.att_armor = 0.00;
		this.att_dodge = 0.00;
		this.att_crit = 0.00;
		
		this.droptype = droptype;
		
		is = new ItemStack(stack,amount);
		this.applyStats();
	}
	public EcstasyItem(String name, ItemRarity rarity, int reqLevel, int itemlevel, Material stack, String type, int amount, double str, double sta, double intel, double disc, double rng, DropType droptype)
	{
		this.item_name = name;
		this.rarity = rarity;
		this.item_level = itemlevel;
		this.stack = stack;
		this.item_type = type;
		this.item_usage_level = reqLevel;
		
		this.att_strength = str;
		this.att_stamina = sta;
		this.att_intellect = intel;
		this.att_discipline = disc;
		this.att_ranged = rng;
		
		this.att_armor = 0.00;
		this.att_dodge = 0.00;
		this.att_crit = 0.00;
		
		this.droptype = droptype;
		
		is = new ItemStack(stack,amount);
		this.applyStats();
	}
	public EcstasyItem(String name, ItemRarity rarity, int reqLevel, int itemlevel, Material stack, String type, int amount, double str, double sta, double intel, double disc, double rng, double arm, double dodge, double crit, DropType droptype)
	{
		this.item_name = name;
		this.rarity = rarity;
		this.item_level = itemlevel;
		this.stack = stack;
		this.item_type = type;
		this.item_usage_level = reqLevel;
		
		this.att_strength = str;
		this.att_stamina = sta;
		this.att_intellect = intel;
		this.att_discipline = disc;
		this.att_ranged = rng;
		
		this.att_armor = arm;
		this.att_dodge = dodge;
		this.att_crit = crit;
		
		this.droptype = droptype;
		
		is = new ItemStack(stack,amount);
		this.applyStats();
	}
	public EcstasyItem(String name, ItemRarity rarity, int reqLevel, int itemlevel, Material stack, String type, int amount, double str, double sta, double intel, double disc, double rng, double arm, double dodge, double crit, DropType droptype, double minHit, double maxHit)
	{
		this.item_name = name;
		this.rarity = rarity;
		this.item_level = itemlevel;
		this.stack = stack;
		this.item_type = type;
		this.item_usage_level = reqLevel;
		
		this.att_strength = str;
		this.att_stamina = sta;
		this.att_intellect = intel;
		this.att_discipline = disc;
		this.att_ranged = rng;
		
		this.att_armor = arm;
		this.att_dodge = dodge;
		this.att_crit = crit;
		
		this.droptype = droptype;
		
		this.min_damage = minHit;
		this.max_damage = maxHit;
		
		is = new ItemStack(stack,amount);
		this.applyStats();
	}
	
	public String getItemName() { return this.item_name; }
	public int getItemLevel() { return this.item_level; }
	public int getRequiredLevel() { return this.item_usage_level; }
	public Material getMaterial() { return this.stack; }
	public String getItemType() { return this.item_type; }
	public ItemRarity getItemRarity() { return this.rarity; }
	public String getItemTooltip() { return this.tooltip; }
	
	public double getCombatAttribute(PlayerCombatAttribute attribute)
	{
		switch(attribute)
		{
			case STRENGTH: return att_strength;
			case ARMOR: return att_armor;
			case CRITICAL_STRIKE: return att_crit;
			case DISCIPLINE: return att_discipline;
			case DODGE: return att_dodge;
			case INTELLECT: return att_intellect;
			case RANGED: return att_ranged;
			case STAMINA: return att_stamina;
			default: return 0.00;
		}
	}
	public double getMinimumDamage() { return min_damage; }
	public double getMaximumDamage() { return max_damage; }
	
	public ItemStack getItemStack() { return is; }
	
	public void applyStats()
	{
		ItemMeta im = this.getItemStack().getItemMeta();
		List<String> lore = new ArrayList<String>();
		
		// Name of the Item
		switch(rarity)
		{
			case COMMON:
				im.setDisplayName("" + ChatColor.WHITE + ChatColor.BOLD + item_name);
				break;
			case EPIC:
				im.setDisplayName("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + item_name);
				break;
			case LEGENDARY:
				im.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + item_name);
				break;
			case POOR:
				im.setDisplayName("" + ChatColor.GRAY + ChatColor.BOLD + item_name);
				break;
			case RARE:
				im.setDisplayName("" + ChatColor.BLUE + ChatColor.BOLD + item_name);
				break;
			case UNCOMMON:
				im.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + item_name);
				break;
			default:
				break;
		}
		
		// Type of item
		lore.add(ChatColor.YELLOW + "Item Level " + item_level);
	
		if(this.getDropType() == DropType.DISALLOW_DROP) lore.add("" + ChatColor.WHITE + "Soulbound");
		else if(this.getDropType() == DropType.ALLOW_DROP) lore.add("" + ChatColor.WHITE + " ");
		
		// lore.add(" ");
		if(att_armor > 0.00)
		{
			lore.add("" + ChatColor.WHITE + "Equipment                 " + (int)att_armor + " Armor");
			lore.add(" ");
		}
		else
		{
			if(item_type.equalsIgnoreCase("sword")) lore.add("" + ChatColor.WHITE + "One-Hand                       " + item_type);
			else if(item_type.equalsIgnoreCase("bow")) lore.add("" + ChatColor.WHITE + "One-Hand                       " + item_type);
			else if(item_type.equalsIgnoreCase("armor")) lore.add("" + ChatColor.WHITE + "                               " + item_type);
			
			if(item_type.equalsIgnoreCase("sword"))
			{
				lore.add("" + ChatColor.WHITE + "" + min_damage + " - " + max_damage + " Damage             Speed 1.00");
				lore.add(" ");
			}
			else if(item_type.equalsIgnoreCase("bow"))
			{
				lore.add("" + ChatColor.WHITE + "" + min_damage + " - " + max_damage + " Damage             Speed 2.20");
				lore.add(" ");
			}
		}
		// Stats
		
		
		if(att_strength > 0.00) lore.add("" + ChatColor.WHITE + "+" + ((int) att_strength) + " Strength");
		if(att_stamina > 0.00) lore.add("" + ChatColor.WHITE + "+" + ((int) att_stamina) + " Stamina");
		if(att_intellect > 0.00) lore.add("" + ChatColor.WHITE + "+" + ((int) att_intellect) + " Intellect");
		if(att_discipline > 0.00) lore.add("" + ChatColor.WHITE + "+" + ((int) att_discipline) + " Discipline");
		if(att_ranged > 0.00) lore.add("" + ChatColor.WHITE + "+" + ((int) att_ranged) + " Ranged");
		if(att_dodge > 0.00) lore.add("" + ChatColor.WHITE + "+" + ((int) att_dodge) + ".0% Dodge");
		if(att_crit > 0.00) lore.add("" + ChatColor.WHITE + "+" + ((int) att_crit) + ".0% Critical Strike");
		if(!item_type.equalsIgnoreCase("item") && !item_type.equalsIgnoreCase("gem")) { lore.add(" ");lore.add(" "); }
		if(item_usage_level > 1) lore.add("" + ChatColor.YELLOW + "Requires Level " + item_usage_level);
		
		im.setLore(lore);
		is.setItemMeta(im);
	}
	public String getItemTag()
	{
		switch(rarity)
		{
			case COMMON:
				return ChatColor.WHITE + "" + ChatColor.ITALIC + "[" + item_name + "]" + ChatColor.RESET;
			case EPIC:
				return ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "[" + item_name + "]" + ChatColor.RESET;
			case LEGENDARY:
				return ChatColor.GOLD + "" + ChatColor.ITALIC + "[" + item_name + "]" + ChatColor.RESET;
			case POOR:
				return ChatColor.GRAY + "" + ChatColor.ITALIC + "[" + item_name + "]" + ChatColor.RESET;
			case RARE:
				return ChatColor.BLUE + "" + ChatColor.ITALIC + "[" + item_name + "]" + ChatColor.RESET;
			case UNCOMMON:
				return ChatColor.GREEN + "" + ChatColor.ITALIC + "[" + item_name + "]" + ChatColor.RESET;
			default:
				return ChatColor.WHITE + "" + ChatColor.ITALIC + "[" + item_name + "]" + ChatColor.RESET;
		}
	}
	public DropType getDropType() { return droptype; }
}
