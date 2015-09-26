package darva.shadowcraft.handlers;


import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darva.shadowcraft.Main;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class BonemealHandler {
	
	
	public BonemealHandler()
	{
		
	}
	@SubscribeEvent
    public void onUseBonemeal(BonemealEvent event)
    {
    	int x,y,z;
    	x = event.x;
    	y = event.y;
    	z = event.z;
    	
    	if (event.world.getBlock(x, y, z) == Main.SaplingBlock)
    	{
    		event.world.addBlockEvent(x, y, z, Main.SaplingBlock, 1, 0);
    		event.setResult(Event.Result.ALLOW);
    		
    	}
    }
}
