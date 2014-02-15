package darva.shadowcraft;

import java.util.EnumSet;

import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.model.ModelBiped;

public class CommonProxy {

	public void registerRenderers() {
		//Stub, nothing to do here, servers don't render.
		
	}
	public ModelBiped getArmorModel(int id){
		return null;
		}
	public void registerTickHandlers()
	{
		//Stub.  All tick's handled client side.
	}

}
