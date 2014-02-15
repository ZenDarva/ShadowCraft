package darva.shadowcraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockPlanks extends Block {

	public BlockPlanks(int par1, Material par2Material) {
		super(par1, par2Material);
		this.setHardness(.5f);
		this.setStepSound(Block.soundWoodFootstep);
		this.setCreativeTab(CreativeTabs.tabBlock);
		
	}

	@Override
	public boolean isBlockSolid(IBlockAccess par1iBlockAccess, int par2,
			int par3, int par4, int par5) {
		return super.isBlockSolid(par1iBlockAccess, par2, par3, par4, par5);
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z,
			ForgeDirection side) {
		return super.isBlockSolidOnSide(world, x, y, z, side);
	}
	@SideOnly(Side.CLIENT)
    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    public int getRenderBlockPass()
    {
            return 1;
            //Render during Alpha pass.
    }
	 public boolean isOpaqueCube()
	    {
	        return false;
	    }
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon("shadowtrees" + ":" + "wood_plank");
  	}

}
