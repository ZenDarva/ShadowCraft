package darva.shadowcraft.item;

import darva.shadowcraft.Main;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemShadow extends Item {

	public ItemShadow(int par1) {
		super(par1);
		this.setUnlocalizedName("Congealed Shadows");
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setMaxStackSize(64);
		
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		// TODO Auto-generated method stub
		this.itemIcon = par1IconRegister.registerIcon("shadowtrees:congealed_shadow");
	}


	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World world, int x, int y,
			int z, int side, float par8, float par9, float par10) {
		// TODO Auto-generated method stub
		int targX, targY, targZ;
		
		if (world.isRemote)
		{
			return false;
		}
		par1ItemStack.stackSize--;
		
		targX = x;
		targY = y;
		targZ = z;
		switch(side)
		{
		case 0: //bottom
			targY--;
			break;
		case 1: //Top
			targY++;
			break;
		case 2: //z Decreasing
			targZ--;
			break;
		case 3: //z increasing
			targZ++;
			break;
		case 4: //x decreasing
			targX--;
			break;
		case 5: //x increasing
			targX++;
			break;
			
		default: //I dunno how you did that...
			return false; //So i'm gonna bail.
		}
		
		if (world.isAirBlock(targX, targY, targZ))
		{
			world.setBlock(targX, targY, targZ, Main.shadowBlock.blockID, 8, 1|2);
			world.scheduleBlockUpdate(targX, targY, targZ, Main.shadowBlock.blockID, 10);
		}
		
		return true;
	}

}
