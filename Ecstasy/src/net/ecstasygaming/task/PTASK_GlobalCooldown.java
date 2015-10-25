package net.ecstasygaming.task;

import net.ecstasygaming.entity.Gladiator;

public class PTASK_GlobalCooldown implements Runnable {
	
	Gladiator g;
	int seconds;
	public PTASK_GlobalCooldown(Gladiator g, int seconds)
	{
		this.g = g;
		this.seconds = seconds;
		
		g.global_cooldown = true;
	}
	@Override
	public void run() {
		g.global_cooldown = false;
	}

}
