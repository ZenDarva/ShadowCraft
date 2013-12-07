package darva.shadowcraft.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemRune extends Item {

	public static String[] unLocalNames ={
		
		"BlankIronRune",
		"IronFlightRune",
		"DiamondFlyingRune",
		"EmeraldFlightRune",
		"IronDissipationRune",
		"DiamondDissipationRune",
		"EmeraldDissipationRune",
		"IronFogRune",
		"DiamondFogRune",
		"EmeraldFogRune",
		"IronConcentrationRune",
		"DiamondConcentrationRune",
		"EmeraldConcentrationRune",
		"IronBlastRune",
		"DiamondBlastRune",
		"EmeraldBlastRune",
		"DiamondBlankRune",
		"EmeraldBlankRune",
	};

	public static String[] LocalNames ={
		
		"Blank Iron Rune",
		"Iron Flight Rune",
		"Diamond Flying Rune",
		"Emerald Flight Rune",
		"IronDissipationRune",
		"Diamond Dissipation Rune",
		"Emerald Dissipation Rune",
		"Iron Fog Rune",
		"Diamond Fog Rune",
		"Emerald Fog Rune",
		"Iron Concentration Rune",
		"Diamond Concentration Rune",
		"Emerald Concentration Rune",
		"Iron Blast Rune",
		"Diamond Blast Rune",
		"Emerald Blast Rune",
		"Diamond Blank Rune",
		"Emerald Blank Rune",
	};
	private String[] iconNames;
	@SideOnly(Side.CLIENT)
	private Icon[] Icons;
	public ItemRune(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
		
		
		iconNames = new String[18];
		iconNames[0] ="ironblankrune";
		iconNames[1] ="ironflightrune";
		iconNames[2] ="diamondflightrune";
		iconNames[3] ="emeraldflightrune";
		iconNames[4] ="irondissipationrune";
		iconNames[5] ="diamonddissipationrune";
		iconNames[6] ="emeralddissipationrune";
		iconNames[7] ="ironfogrune";
		iconNames[8] = "diamondfogrune";
		iconNames[9] = "emeraldfogrune";
		iconNames[10] = "ironconcentrationrune";
		iconNames[11] = "diamondconcentrationrune";
		iconNames[12] = "emeraldconcentrationrune";
		iconNames[13] = "ironblastrune";
		iconNames[14] = "diamondblastrune";
		iconNames[15] = "emeraldblastrune";
		iconNames[16] = "diamondblankrune";
		iconNames[17] = "emeraldblankrune";
		setHasSubtypes(true);
		
		this.setUnlocalizedName("rune");
	}


	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getUnlocalizedName(net.minecraft.item.ItemStack)
	 */
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return unLocalNames[par1ItemStack.getItemDamage()];
	}
 
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getIcon(net.minecraft.item.ItemStack, int)
	 */
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		// TODO Auto-generated method stub
		return Icons[0];
	}

	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getIconFromDamage(int)
	 */
	@Override
	public Icon getIconFromDamage(int par1) {
		return Icons[par1];
	}


	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#registerIcons(net.minecraft.client.renderer.texture.IconRegister)
	 */

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister IconRegister) {
		super.registerIcons(IconRegister);
		Icons = new Icon[iconNames.length];
		for (int x = 0; x<iconNames.length; x++)
		{
			Icons[x] = IconRegister.registerIcon("shadowtrees:"+iconNames[x]);
		}
	}
	
	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) {
	for(int i = 0; i < Icons.length; i++) {
		ItemStack itemstack = new ItemStack(id, 1, i);
		list.add(itemstack);
	}
	}

	

}
