package darva.shadowcraft.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import darva.shadowcraft.Main;
import darva.shadowcraft.entities.ShadowWellEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ShadowWell extends BlockContainer {

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return Main.renderId;
		//Rendered by WellRender.java
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}


	@Override
	public String getUnlocalizedName() {
		return "shadowcraft:wellofshadows";
	}

	public ShadowWell(Material par2Material) {
		super(par2Material);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setBlockBounds(0F, 0F, 0F, 1F, 1.39F, 1F);
	}

	
	@Override
	public boolean onBlockActivated(World par1World, int x, int y,
			int z, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
		if (!par1World.isRemote)
		{
			FMLNetworkHandler.openGui(player, Main.instance, 0, par1World, x, y, z);
		}
		return true;
	}



	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new ShadowWellEntity();
	}
}
