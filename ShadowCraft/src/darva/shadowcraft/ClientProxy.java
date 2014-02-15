package darva.shadowcraft;

import java.util.EnumSet;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import darva.shadowcraft.entities.EntityBlast;
import darva.shadowcraft.entities.ShadowWellEntity;
import darva.shadowcraft.handlers.ClientTickHandler;
import darva.shadowcraft.models.ShadowCloak;

public class ClientProxy extends CommonProxy {
	private static final ShadowCloak cloak = new ShadowCloak();
	@Override
	public void registerRenderers() {
		//Stub, nothing to do here, servers don't render.
		 ClientRegistry.bindTileEntitySpecialRenderer(ShadowWellEntity.class, new WellRender());
		 EntityRegistry.registerGlobalEntityID(EntityBlast.class, "ShadowBlast", 560);
         RenderingRegistry.registerEntityRenderingHandler(EntityBlast.class, new BlastRender());
         MinecraftForgeClient.registerItemRenderer(Main.shadowWell.blockID, new ItemRender());
	}
	@Override
	public void registerTickHandlers()
	{
		Main.instance.cfh = new ClientTickHandler(EnumSet.of(TickType.PLAYER));
		TickRegistry.registerTickHandler(Main.instance.cfh, Side.CLIENT);

	}
	
	public ModelBiped getArmorModel(int id){
		return cloak;
		}
		
}
