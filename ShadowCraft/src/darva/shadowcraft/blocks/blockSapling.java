package darva.shadowcraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;
import darva.shadowcraft.Main;

public class blockSapling extends BlockFlower {
	private int growth = 0;
	public blockSapling(int par1, Material par2Material) {
		super(par1, par2Material);
		// TODO Auto-generated constructor stub
		float f = 0.4F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.needsRandomTick = true;
	}
	
	 public void registerIcons(IconRegister par1IconRegister)
	 {
	     this.blockIcon = par1IconRegister.registerIcon("shadowtrees" + ":" + "sapling_oak");
	  
	 }
	 public int getRenderBlockPass()
	    {
	            return 1;
	    }
		 public boolean isOpaqueCube()
		    {
		        return false;
		    }

		/* (non-Javadoc)
		 * @see net.minecraft.block.BlockFlower#updateTick(net.minecraft.world.World, int, int, int, java.util.Random)
		 */
		@Override
		public void updateTick(World world, int x, int y, int z,
				Random random) {
			// TODO Auto-generated method stub
			super.updateTick(world, x, y, z, random);
			
			if (random.nextInt(10) <3)
			{
				
				world.setBlockMetadataWithNotify(x, y, z, this.blockID, world.getBlockMetadata(x, y, z) +1 );
				System.out.println("Meta"+ world.getBlockMetadata(x, y, z));
			}
			
			if (world.getBlockMetadata(x, y, z) >= 6)
			{
				Main.treeGen.growTree(world, random, x, y, z);
			}
			
		}

		/* (non-Javadoc)
		 * @see net.minecraft.block.Block#onBlockEventReceived(net.minecraft.world.World, int, int, int, int, int)
		 */
		@Override
		public boolean onBlockEventReceived(World par1World, int par2,
				int par3, int par4, int par5, int par6) {
			// TODO Auto-generated method stub
			System.out.print("Bonemeal");
			Random rnd = new Random();
			rnd.setSeed( System.currentTimeMillis());
			growth = growth + rnd.nextInt(2);
			if (growth >6) 
			{
				Main.treeGen.growTree(par1World, rnd, par2, par3, par4);
			}
			return super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
			
		}
}
