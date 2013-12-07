package darva.shadowcraft.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import darva.shadowcraft.Main;
import darva.shadowcraft.WellContainer;
import darva.shadowcraft.WellGui;
import darva.shadowcraft.entities.ShadowWellEntity;

public class GuiHandler implements IGuiHandler {

	public GuiHandler()
	{
		NetworkRegistry.instance().registerGuiHandler(Main.instance,this);
	}
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		switch(ID)
		{
		case 0:
			if (entity != null && entity.getBlockType().blockID == Main.shadowWell.blockID)
			{
				return new WellContainer(player.inventory, (ShadowWellEntity)entity);
			}
		default:
			return null;
			
		}
		
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		switch(ID)
		{
		case 0:
			if (entity != null && entity.getBlockType().blockID == Main.shadowWell.blockID)
			{
				return new WellGui(player.inventory, (ShadowWellEntity)entity);
			}
		default:
			return null;
			
		}
	}

}
