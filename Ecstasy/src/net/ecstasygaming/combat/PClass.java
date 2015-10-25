package net.ecstasygaming.combat;

import java.util.ArrayList;

import org.bukkit.Material;

import net.ecstasygaming.MMOPro;
import net.ecstasygaming.ability.Ability;

public class PClass {

	ArrayList<Ability> abilities;
	
	protected int classNumber = -1;
	protected String className = "";
	
	public PClass()
	{
		this.classNumber = -1;
		this.className = "Spirit";
		
		abilities = new ArrayList<Ability>();
	}

	public PClass(int classnumber) {
		this.classNumber = classnumber;
		
		this.className = MMOPro.config_players.getStringList("classes.list").get(classNumber);
		
		abilities = new ArrayList<Ability>();
		
		// 
	}

	public int getClassNumber() {
		return this.classNumber;
	}
	public String getClassName() {
		return this.className;
	}
	
	public boolean hasAbility(Ability a)
	{
		if(classNumber == -1) return false;
		int i;
		for(i = 0; i < abilities.size(); i++)
		{
			if(abilities.get(i).equals(a)) return true;
		}
		return false;
	}
	
	// General Proficiency Check
	// I.E. Does a Priest have the ability to use a sword at all
	// They might have the proper level and it might be unrestricted but they cannot wield it bc they are magic users
	public boolean canUse(Material m)
	{
		if(classNumber == -1) return false;
		for(String s : MMOPro.config_players.getStringList("classes." + className.toLowerCase() + ".materials"))
		{
			if(Material.getMaterial(s.toUpperCase()).equals(m)) return true;
		}
		return false;
	}
}
