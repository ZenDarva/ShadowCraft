package darva.shadowcraft.blocks;

import java.util.Random;

import darva.shadowcraft.handlers.ClientTickHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ShadowBlock extends Block {

	private static int spreadLimit =5;
	public ShadowBlock(int par1, Material par2Material) {
		super(par1, par2Material);
		setBlockBounds(0, 0, 0, 0, 0, 0);
		this.setLightOpacity(255);
		this.needsRandomTick = true;
		
	}

	@Override
	public int getRenderType() {
		return -1; //Does not render.
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {

		this.blockIcon = par1IconRegister.registerIcon("shadowtrees:empty");
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
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateTick(World world, int x, int y, int z,
			Random par5Random) {
		
		int meta = world.getBlockMetadata(x, y, z);
		if (meta > spreadLimit)
		{
			meta--;
			setBlock(world, x-1, y, z, meta);
			setBlock(world, x+1, y, z, meta);
			
			setBlock(world, x, y-1, z, meta);
			setBlock(world, x, y+1, z, meta);
			
			setBlock(world, x, y, z+1, meta);
			setBlock(world, x, y, z-1, meta);
			//Shadows only get to spread once, set metadata to spreadLimit to prevent
			//further spreading.
			world.setBlockMetadataWithNotify(x, y, z, spreadLimit, 1|2);

		}
		if (meta <= spreadLimit)
		{
			//decay if in bright light.
			if (ClientTickHandler.getRealLight(x, y+1, z, world) > 7)  
			{
				//Check the block above for light level, to see if we're being shined on.
				world.setBlockMetadataWithNotify(x, y, z, meta-1, 4);
			}
			if (meta <= 0 )
			{
				//Turn it back to normal air.
				world.setBlock(x, y, z, 0, 0, 1|2);
				world.updateAllLightTypes(x, y, z);
			}

		}
	}

	private void setBlock(World world, int x, int y, int z, int meta)
	{
		if (world.isAirBlock(x, y, z) && world.getBlockId(x, y, z) != this.blockID)
		{
			//Possible, and sane to replace this block with shadows.
			world.setBlock(x, y, z, blockID, meta, 3);
			world.scheduleBlockUpdate(x, y, z, blockID, 20);
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
	public boolean isBlockNormalCube(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean isBlockReplaceable(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean isAirBlock(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean canBeReplacedByLeaves(World world, int x, int y, int z) {
		return true;
	}
	
	

}
