package darva.shadowcraft.blocks;

import darva.shadowcraft.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class TreeLeaves extends Block {

	public TreeLeaves(Material par2Material) {
		super(par2Material);

		this.setLightOpacity(15);
		this.needsRandomTick = true;
        this.setBlockTextureName("shadowcraft:leaves_oak");
	}
	public int getRenderBlockPass()
    {
            return 1;
    }

	@Override
	public String getUnlocalizedName() {
		return "shadowcraft:shadowleaves";
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, 1 - par5);
    }
	 public boolean isOpaqueCube()
	    {
	        return false;
	    }



    public void registerIcons(IIconRegister par1IconRegister)
	 {
	     this.blockIcon = par1IconRegister.registerIcon("shadowcraft" + ":" + "leaves_oak");
	  
	 }

	@Override
	public boolean isLeaves(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public void updateTick(World par1World, int x, int y, int z,
			Random par5Random) {
		
		if ((compareNeighbors(par1World, x,y,z, Main.LeavesBlock) ||
				compareNeighbors(par1World, x,y,z, Main.TreeBlock))
				&& CheckForWood(par1World, x,y,z))
		{
			//We're good, don't dissipate, but check to see if we need to spawn
			// shadows.
			
//			if (par5Random.nextInt(20) > 1)
//			{
//				//Spawn shadows around this block.
//				setBlock(par1World, x-1,y,z);
//				setBlock(par1World, x+1,y,z);
//				setBlock(par1World, x,y+1,z);
//				setBlock(par1World, x,y-1,z);
//				setBlock(par1World, x,y,z-1);
//				setBlock(par1World, x,y,z+1);
//			}
			
			
			return;
		}

		dropBlockAsItem(par1World,x,y,z,3,3);
		par1World.setBlockToAir(x,y,z);

		super.updateTick(par1World, x, y, z, par5Random);
	}
	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#idDropped(int, java.util.Random, int)
	 */

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		Random par2Random = new Random();
		ArrayList<ItemStack> results = new ArrayList<ItemStack>();
		if (par2Random.nextInt(18) == 1)
		{
			results.add(new ItemStack(Main.SaplingBlock));
		}
		if (par2Random.nextInt(20) == 1)
			results.add(new ItemStack( Main.itemShadow));
		return results;
	}

	private boolean compareNeighbors(World world, int X, int Y, int Z, Block ID)
	{
		if (world.getBlock(X - 1, Y, Z) == ID || world.getBlock(X + 1, Y, Z) == ID
				|| world.getBlock(X, Y - 1, Z) == ID || world.getBlock(X, Y + 1, Z) == ID
				|| world.getBlock(X, Y, Z - 1) == ID || world.getBlock(X, Y, Z + 1) == ID)
		{
			return true;
		}
		return false;
	}
	private boolean CheckForWood(World world, int X, int Y, int Z)
	{
		for (int x2 = X-2; x2 < X + 3; x2++)
		{
			for (int y2 = Y-2; y2 < Y + 3; y2++)
			{
				for (int z2 = Z-2; z2 < Z + 3; z2++)
				{
					if (world.getBlock(x2, y2, z2) == Main.TreeBlock)
						return true;
				}
			}
		}
		
		return false;
	}
	
	private void setBlock(World world, int x, int y, int z)
	{
		if (world.isAirBlock(x, y, z) && world.getBlock(x, y, z) != this)
		{
			//Possible, and sane to replace this block with shadows.
			world.setBlock(x, y, z, Main.shadowBlock, 2, 3);
			world.scheduleBlockUpdate(x, y, z, Main.shadowBlock, 20);
		}
	}

}
