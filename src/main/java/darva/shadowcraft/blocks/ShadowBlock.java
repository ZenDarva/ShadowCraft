package darva.shadowcraft.blocks;

import darva.shadowcraft.handlers.ClientTickHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class ShadowBlock extends Block {

	private static int spreadLimit =3;
	public ShadowBlock(Material par2Material) {
		super(par2Material);
		setBlockBounds(0, 0, 0, 0, 0, 0);
		this.setLightOpacity(255);
		this.needsRandomTick = true;
		this.setHarvestLevel("axe",0);
		this.setBlockTextureName("shadowcraft:empty");
		
	}

	@Override
	public int getRenderType() {
		return -1; //Does not render.
	}

	@Override
	public String getItemIconName() {
		return "shadowcraft:empty";
	}


	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {		
		int meta = world.getBlockMetadata(x,y,z);
		/*if (meta == 0)  //This is test code for spreading.
		{
			world.setBlockMetadataWithNotify(x, y,z, 10, 2);
		}*/

	}

	@Override
	public String getUnlocalizedName() {
		return "shadowcraft:shadowblock";
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateTick(World world, int x, int y, int z,
			Random r) {

		int meta = world.getBlockMetadata(x, y, z);
		if (meta > spreadLimit)
		{
			meta--;
            if (r.nextInt(20)>4)
			    setBlock(world, x-1, y, z, meta);
            if (r.nextInt(20)>4)
			    setBlock(world, x+1, y, z, meta);

            if (r.nextInt(20)>4)
			    setBlock(world, x, y-1, z, meta);
            if (r.nextInt(20)>4)
			    setBlock(world, x, y+1, z, meta);

            if (r.nextInt(20)>4)
			    setBlock(world, x, y, z+1, meta);
            if (r.nextInt(20)>4)
			    setBlock(world, x, y, z-1, meta);
			//Shadows only get to spread once, set metadata to spreadLimit to prevent
			//further spreading.
			world.setBlockMetadataWithNotify(x, y, z, spreadLimit, 1|2);
            world.scheduleBlockUpdate(x, y, z, this, 10);

		}
		if (meta <= spreadLimit)
		{
			//decay if in bright light.
			if (ClientTickHandler.getRealLight(x, y+1, z, world) > 7)  
			{
				//Check the block above for light level, to see if we're being shined on.
				world.setBlockMetadataWithNotify(x, y, z, meta-1, 3);
                world.scheduleBlockUpdate(x,y,z,this,10);
			}
			if (meta <= 0 )
			{
				//Turn it back to normal air.
				world.setBlock(x, y, z, Blocks.air, 0, 1 | 2);
				world.updateLightByType(EnumSkyBlock.Sky, x, y, z);
				world.updateLightByType(EnumSkyBlock.Block, x, y, z);;
			}

		}
	}

	private void setBlock(World world, int x, int y, int z, int meta)
	{
		if (world.isAirBlock(x, y, z) && world.getBlock(x, y, z) != this)
		{
			//Possible, and sane to replace this block with shadows.
			world.setBlock(x, y, z, this, meta, 3);
			world.scheduleBlockUpdate(x, y, z, this, 10);
            world.updateLightByType(EnumSkyBlock.Sky, x, y, z);
            world.updateLightByType(EnumSkyBlock.Block, x, y, z);
		}
	}
	
	@Override
	public boolean isBlockSolid(IBlockAccess par1iBlockAccess, int par2,
			int par3, int par4, int par5) {
		return false;
	}

	@Override
	public boolean canCollideCheck(int par1, boolean par2) {
		return false;
	}

	@Override
	public boolean isCollidable() {
		return false;
}

	@Override
	public boolean isAir(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return true;
	}


	@Override
	public boolean isBlockNormalCube() {
		return false;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
		return true;
	}
	
	

}
