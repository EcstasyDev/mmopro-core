package net.ecstasygaming.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.ecstasygaming.Ecstasy;
import net.ecstasygaming.entity.Gladiator;
import net.ecstasygaming.entity.PlayerCombatAttribute;
import net.ecstasygaming.util.MessageType;
import net.ecstasygaming.util.Messenger;

public class TASK_RegenMana implements Runnable {
	
	public TASK_RegenMana()
	{
		Ecstasy.log.info("Registered task: Player Mana Regeneration");
	}

	@Override
	public void run() {
		double regen_amt = 0.0;
		Gladiator g;
		for(Player pl : Bukkit.getOnlinePlayers())
		{
			if(Ecstasy.players.containsKey(pl.getName()))
			{
				g = Ecstasy.players.get(pl.getName());
				
				if(g.getResource() < g.getMaxResource())
				{
					switch(g.getClassNumber())
					{
					case 0:
						regen_amt = 5*(g.getCombatAttribute(PlayerCombatAttribute.STRENGTH)/16);
						break;
					case 1:
						regen_amt = 5*(g.getCombatAttribute(PlayerCombatAttribute.DISCIPLINE)/16);
						break;
					case 2:
						regen_amt = 5*(g.getCombatAttribute(PlayerCombatAttribute.RANGED)/16);
						break;				
					case 3:
					case 4:
						regen_amt = 5*(g.getCombatAttribute(PlayerCombatAttribute.DISCIPLINE)/12);
						break;
					}
					
					if(g.getResource() > g.getMaxResource()) g.setResource(g.getMaxResource());
					
					if(regen_amt > 0.0)
					{
						g.setResource((g.getResource() + regen_amt));
						Messenger.toPlayer(pl, "+" + regen_amt + " mana (" + g.getResource() + " / " + g.getMaxResource() + ")",MessageType.MESSAGE);
					}
				}
			}
		}
	}

}
