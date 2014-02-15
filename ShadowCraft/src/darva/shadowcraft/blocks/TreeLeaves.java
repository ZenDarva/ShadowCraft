package darva.shadowcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import darva.shadowcraft.Main;

public class TreeLeaves extends Block {

	public TreeLeaves(int par1, Material par2Material) {
		super(par1, par2Material);

		this.setLightOpacity(15);
		this.needsRandomTick = true;
	}
	public int getRenderBlockPass()
    {
            return 1;
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
	 public void registerIcons(IconRegister par1IconRegister)
	 {
	     this.blockIcon = par1IconRegister.registerIcon("shadowtrees" + ":" + "leaves_oak");
	  
	 }
	 @Override
	    public boolean isLeaves(World world, int x, int y, int z)
	    {
	        return true;
	    }
	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#updateTick(net.minecraft.world.World, int, int, int, java.util.Random)
	 */
	@Override
	public void updateTick(World par1World, int x, int y, int z,
			Random par5Random) {
		// TODO Auto-generated method stub
		
		if ((compareNeighbors(par1World, x,y,z, Main.LeavesBlock.blockID) ||
				compareNeighbors(par1World, x,y,z, Main.TreeBlock.blockID))
				&& CheckForWood(par1World, x,y,z))
		{
			//We're good, don't dissipate, but check to see if we need to spawn
			// shadows.
			
			if (par5Random.nextInt(20) > 5)
			{
				setBlock(par1World, x-1,y,z);
				setBlock(par1World, x+1,y,z);
				setBlock(par1World, x,y+1,z);
				setBlock(par1World, x,y-1,z);
				setBlock(par1World, x,y,z-1);
				setBlock(par1World, x,y,z+1);
			}
			
			
			return;
		}
		
		par1World.destroyBlock(x, y, z, true);
		//this.dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
		
		super.updateTick(par1World, x, y, z, par5Random);
	}
	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#idDropped(int, java.util.Random, int)
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		if (par2Random.nextInt(18) == 1) 
		{
			return Main.SaplingBlock.blockID;
		}
		if (par2Random.nextInt(20) == 1)
			return Main.itemShadow.itemID;
		return 0;
		//return super.idDropped(par1, par2Random, par3);
	}
	private boolean compareNeighbors(World world, int X, int Y, int Z, int ID)
	{
		if (world.getBlockId(X-1, Y, Z) == ID || world.getBlockId(X+1,Y,Z) == ID
				|| world.getBlockId(X, Y-1, Z) == ID || world.getBlockId(X, Y+1, Z) == ID
				|| world.getBlockId(X, Y, Z-1) == ID || world.getBlockId(X, Y, Z+1) == ID)
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
					if (world.getBlockId(x2, y2, z2) == Main.TreeBlock.blockID)
						return true;
				}
			}
		}
		
		return false;
	}
	
	private void setBlock(World world, int x, int y, int z)
	{
		if (world.isAirBlock(x, y, z) && world.getBlockId(x, y, z) != this.blockID)
		{
			//Possible, and sane to replace this block with shadows.
			world.setBlock(x, y, z, Main.shadowBlock.blockID, 6, 3);
			world.scheduleBlockUpdate(x, y, z, Main.shadowBlock.blockID, 20);
		}
	}

}
