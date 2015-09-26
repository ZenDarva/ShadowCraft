package darva.shadowcraft;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.EntityRegistry;
import darva.shadowcraft.entities.EntityBlast;
import darva.shadowcraft.entities.ShadowWellEntity;
import darva.shadowcraft.handlers.ClientTickHandler;
import darva.shadowcraft.models.ShadowCloak;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

import java.util.Iterator;

public class ClientProxy extends CommonProxy {
	private static final ShadowCloak cloak = new ShadowCloak();
	@Override
	public void registerRenderers() {
		
		 ClientRegistry.bindTileEntitySpecialRenderer(ShadowWellEntity.class, new WellRender());
		 EntityRegistry.registerGlobalEntityID(EntityBlast.class, "ShadowBlast", EntityRegistry.findGlobalUniqueEntityId());
         RenderingRegistry.registerEntityRenderingHandler(EntityBlast.class, new BlastRender());
        RenderingRegistry.registerBlockHandler(new WellISBRH());
	}
	@Override
	public void registerTickHandlers()
	{
		//Main.instance.cfh = new ClientTickHandler(EnumSet.of(TickType.PLAYER));
		//TickRegistry.registerTickHandler(Main.instance.cfh, Side.CLIENT);
		//MinecraftForge.EVENT_BUS.register(new ClientTickHandler() );
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
        System.out.println("Looking for NEI");
        //if (Minecraft.getMinecraft().thePlayer != null ) {
            Iterator modsIT = Loader.instance().getModList().iterator();
            ModContainer modc;
            while (modsIT.hasNext()) {
                modc = (ModContainer) modsIT.next();
                System.out.println(modc.getName());
                if ("Not Enough Items".equals(modc.getName().trim())) {
                    codechicken.nei.api.API.hideItem(new ItemStack(Main.shadowBlock));
                    System.out.println("Hiding the item.");
                    return;

                }
            }
        //}
	}
	
	public ModelBiped getArmorModel(int id){
		return cloak;
		}
		
}
