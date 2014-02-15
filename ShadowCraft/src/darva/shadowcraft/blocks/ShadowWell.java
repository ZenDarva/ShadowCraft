package darva.shadowcraft.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import darva.shadowcraft.Main;
import darva.shadowcraft.entities.ShadowWellEntity;

public class ShadowWell extends BlockContainer {

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
		//Rendered by WellRender.java
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
	}

	public ShadowWell(int par1, Material par2Material) {
		super(par1, par2Material);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName("Well of Shadows");
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
	public TileEntity createNewTileEntity(World world) {
		return new ShadowWellEntity();
	}
	

}
