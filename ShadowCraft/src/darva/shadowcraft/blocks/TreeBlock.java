package darva.shadowcraft.blocks;



import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darva.shadowcraft.Main;

public class TreeBlock extends Block {

	private class Vector3
	{
		public int x;
		public int y;
		public int z;
		
		public Vector3 (int X, int Y, int Z)
		{
			x = X;
			y = Y;
			z = Z;
		}
	}
	
	private Icon sideIcon;
	private Icon endIcon;
	public TreeBlock(int par1, Material par2Material) {
		super(par1, par2Material);
		// TODO Auto-generated constructor stub
		this.setLightOpacity(1);
	}
	 @SideOnly(Side.CLIENT)
     /**
      * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
      */
     public int getRenderBlockPass()
     {
             return 1;
     }
	 public boolean isOpaqueCube()
	    {
	        return false;
	    }
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
 {
     sideIcon = par1IconRegister.registerIcon("shadowtrees" + ":" + "log_oak");
     endIcon = par1IconRegister.registerIcon("shadowtrees" + ":" + "log_oak_top");
 }
	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#onBlockClicked(net.minecraft.world.World, int, int, int, net.minecraft.entity.player.EntityPlayer)
	 */
	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4,
			EntityPlayer player) {
		// TODO Auto-generated method stub

	}
	@SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int meta)
    {
    	if (side == 3)
    		return sideIcon;
    	if (side == 2)
    		return sideIcon;
    	if (side == 4)
    		return sideIcon;
    	if (side == 5)
    		return sideIcon;
    	if (side == 1)
    		return endIcon;
    	if (side == 0)
    		return endIcon;
    	return blockIcon;
    }
	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#onBlockActivated(net.minecraft.world.World, int, int, int, net.minecraft.entity.player.EntityPlayer, int, float, float, float)
	 */
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
		// TODO Auto-generated method stub
		if (par1World.isRemote)
		{
			return false;
		}
		if (player == null )
		{
			return false;
		}
		if (player.inventory == null)
		{
			return false;
		}
		if (player.inventory.armorInventory == null)
			return false;
		for (ItemStack i : player.inventory.armorInventory)
		{
			if (i != null)
				if (i.itemID == Main.shadowArmor.itemID)
				{
					int facing = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
					Vector3 vec = findTree(par1World, facing, par2, par3, par4);
					//Try different algorithms for findTree
					//Possibly depth, then width, however, this seems to work for now.
					if (vec == null)
					{
						return false;
					}
					
					switch (facing)
					{
					case 0:
						vec.z++;//offset, so we're not standing in the tree after teleporting.
						teleportPlayer(par1World, player, vec);
						break;
					case 2:
						vec.z--;
						teleportPlayer(par1World, player, vec);
						break;
					case 1:
						vec.x--;
						teleportPlayer(par1World,player,vec);
						break;
					case 3:
						vec.x++;
						teleportPlayer(par1World,player,vec);
						break;
	
					}
					return true;
				}
		}
		
		return false;
	}
	private Vector3 findTree(World world, int Facing, int x, int y, int z)
	{
		// North/south is Z
		// East West is X
		// South = 0
		// West = 1
		// North =2
		// East =3 
		
		int width=1, depth=1;
		int distance =1 ;
		
		for (int length = 1; length < 100; length ++)
		{
			for (int curWidth = width *-1; curWidth <= width; curWidth++)
			{
				for (int curDepth = depth * -1; curDepth <= depth; curDepth++)
				{
					switch (Facing)
					{
					case 0: //south
						if (world.getBlockId(x+curWidth, y+curDepth, z+length) == Main.TreeBlock.blockID)
						{
								return new Vector3(x+curWidth, y+curDepth, z+length);
						}
						continue;
					case 1: //West
						if (world.getBlockId(x-length, y+curDepth, z+curWidth) == Main.TreeBlock.blockID)
						{
								return new Vector3(x-length, y+curDepth, z+curWidth);
						}
						continue;
					case 3: //east
						if (world.getBlockId(x+length, y+curDepth, z+curWidth) == Main.TreeBlock.blockID)
						{
								return new Vector3(x+length, y+curDepth, z+curWidth);
						}
						continue;

					case 2: // North
						if (world.getBlockId(x+curWidth, y+curDepth, z-length) == Main.TreeBlock.blockID)
						{
							return new Vector3(x+curWidth, y+curDepth, z-length);
						}
						continue;
				
					}
				}
			}
			width++;
			depth++;
		}
		
		
		return null;
	}

	private void teleportPlayer(World world, EntityPlayer player, Vector3 to)
	{
		if (!world.isRemote)
        {
			int x1;
			int z1;
			int x,z;
			int dist;
            if (player != null)
            {
                EntityPlayerMP entityplayermp = (EntityPlayerMP)player;

                if (!entityplayermp.playerNetServerHandler.connectionClosed && entityplayermp.worldObj == world)
                {
                        if (player.isRiding())
                        {
                        	//Dismount the player if they're mounted.
                            player.mountEntity((Entity)null);
                        }

                        x1= (int)player.posX;
                        z1 = (int)player.posZ;
                        x = Math.abs(x1 - to.x);
                        z = Math.abs(z1 - to.z);
                        dist = (int)Math.sqrt((x*x) +(z*z));
                        dist/=10;
                        if (dist > 19)
                        	dist = 19;
                        player.setPositionAndUpdate(to.x+.5,to.y, to.z+.5);

                        player.fallDistance = 0.0F; // must clear, or damage is unpredictable.
                        player.attackEntityFrom(DamageSource.fall, dist);
                        
                }
            }
	   }
	}
}
