package darva.shadowcraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;

public class BlockPlanks extends Block {

	public BlockPlanks(Material par2Material) {
		super(par2Material);
		this.setHardness(.5f);
		this.setStepSound(Block.soundTypeWood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setBlockTextureName("shadowcraft:wood_plank");
	}

	@Override
	public boolean isBlockSolid(IBlockAccess par1iBlockAccess, int par2,
			int par3, int par4, int par5) {
		return super.isBlockSolid(par1iBlockAccess, par2, par3, par4, par5);
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

    @Override
    public String getUnlocalizedName() {
        return "shadowcraft:planks";
    }

    @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon("shadowcraft" + ":" + "wood_plank");
  	}

}
