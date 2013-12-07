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

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#renderAsNormalBlock()
	 */
	@Override
	public boolean renderAsNormalBlock() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#getRenderType()
	 */
	@Override
	public int getRenderType() {
		// TODO Auto-generated method stub
		return -1;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#isOpaqueCube()
	 */
	@Override
	public boolean isOpaqueCube() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#registerIcons(net.minecraft.client.renderer.texture.IconRegister)
	 */
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		// TODO Auto-generated method stub
		super.registerIcons(par1IconRegister);
		//this.blockIcon = par1IconRegister.registerIcon("shadowtrees" + ":" + "shadowwell");
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
		// TODO Auto-generated method stub
		return new ShadowWellEntity();
	}
	

}
