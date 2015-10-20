package net.ecstasygaming.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.ecstasygaming.Ecstasy;

public class TASK_ResyncPlayer implements Runnable {
	
	public TASK_ResyncPlayer()
	{
		Ecstasy.log.info("Registered task: Player Resync");
	}

	@Override
	public void run() {
		for(Player pl : Bukkit.getOnlinePlayers())
		{
			if(Ecstasy.players.containsKey(pl.getName()))
			{
				Ecstasy.players.get(pl.getName()).resyncAttributes();
			}
		}
	}

}
