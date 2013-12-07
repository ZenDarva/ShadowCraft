package darva.shadowcraft.handlers;

import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import darva.shadowcraft.Main;

public class BonemealHandler {
	
	
	public BonemealHandler()
	{
		
	}
	@ForgeSubscribe
    public void onUseBonemeal(BonemealEvent event)
    {
    	int x,y,z;
    	x = event.X;
    	y = event.Y;
    	z = event.Z;
    	
    	if (event.world.getBlockId(x, y, z) == Main.SaplingBlock.blockID)
    	{
    		event.world.addBlockEvent(x, y, z, Main.SaplingBlock.blockID, 1, 0);
    		event.setResult(Result.ALLOW);
    		
    	}
    }
}
