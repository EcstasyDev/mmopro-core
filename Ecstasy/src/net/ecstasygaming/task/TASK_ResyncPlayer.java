package net.ecstasygaming.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.ecstasygaming.MMOPro;

public class TASK_ResyncPlayer implements Runnable {
	
	public TASK_ResyncPlayer()
	{
		MMOPro.log.info("Registered task: Player Resync");
	}

	@Override
	public void run() {
		for(Player pl : Bukkit.getOnlinePlayers())
		{
			if(MMOPro.players.containsKey(pl.getName()))
			{
				MMOPro.players.get(pl.getName()).resyncAttributes();
			}
		}
	}

}
