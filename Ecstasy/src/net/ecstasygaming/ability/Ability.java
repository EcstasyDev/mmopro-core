package net.ecstasygaming.ability;

public abstract interface Ability {
	
	public int level = 1;
	public double cooldown = 1.0; // The global cooldown
	public double cooldown_rem = 0.0; // Decrementing cooldown
	
	public void cast();

}
