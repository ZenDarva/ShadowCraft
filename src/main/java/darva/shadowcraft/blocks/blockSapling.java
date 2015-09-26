package darva.shadowcraft.blocks;

import darva.shadowcraft.Main;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.World;

import java.util.Random;

public class blockSapling extends BlockBush implements IGrowable {
	private int growth = 0;
	public blockSapling(Material par2Material) {
		super(par2Material);
		float f = 0.4F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.needsRandomTick = true;
		this.setBlockTextureName("shadowcraft:sapling_oak");
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
			super.updateTick(world, x, y, z, random);
			int meta;
			if (world.isRemote)
				return;
			if (random.nextInt(7) <3)
			{
				meta = world.getBlockMetadata(x, y, z);
				meta++;
				world.setBlockMetadataWithNotify(x, y, z, meta,4 );
			}
			
			if (world.getBlockMetadata(x, y, z) >= 6)
			{
				Main.treeGen.growTree(world, random, x, y, z);
			}
			
		}

		@Override
		public boolean onBlockEventReceived(World par1World, int par2,
				int par3, int par4, int par5, int par6) {
			// TODO Auto-generated method stub
			if (par1World.isRemote)
				return true;
			Random rnd = new Random();
			int meta = par1World.getBlockMetadata(par2, par3, par4);
			rnd.setSeed( System.currentTimeMillis());
			meta = meta + rnd.nextInt(2);
			if (meta >6) 
			{
				Main.treeGen.growTree(par1World, rnd, par2, par3, par4);
			}
			else
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, meta,4 );
			}
			return super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
			
		}

	@Override
	public String getUnlocalizedName() {
		return "shadowcraft:shadowsapling";
	}

	@Override
	public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_) {
		return true;
	}

	@Override
	public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_) {
		return (double)p_149852_1_.rand.nextFloat() < 0.45D;
	}

	@Override
	public void func_149853_b(World world, Random random, int x, int y, int z) {
		this.updateTick(world,x,y,z,random);
	}
}
