package darva.shadowcraft.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemRune extends Item {

	public final static String[] unLocalNames ={
		
		"shadowcraft:BlankIronRune",
		"shadowcraft:IronFlightRune",
		"shadowcraft:DiamondFlightRune",
		"shadowcraft:EmeraldFlightRune",
		"shadowcraft:IronDissipationRune",
		"shadowcraft:DiamondDissipationRune",
		"shadowcraft:EmeraldDissipationRune",
		"shadowcraft:IronFogRune",
		"shadowcraft:DiamondFogRune",
		"shadowcraft:EmeraldFogRune",
		"shadowcraft:IronConcentrationRune",
		"shadowcraft:DiamondConcentrationRune",
		"shadowcraft:EmeraldConcentrationRune",
		"shadowcraft:IronBlastRune",
		"shadowcraft:DiamondBlastRune",
		"shadowcraft:EmeraldBlastRune",
		"shadowcraft:DiamondBlankRune",
		"shadowcraft:EmeraldBlankRune",
	};

	public final static String[] LocalNames ={
		
		"Blank Iron Rune",
		"Iron Flight Rune",
		"Diamond Flight Rune",
		"Emerald Flight Rune",
		"Iron Dissipation Rune",
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
	private IIcon[] Icons;
	public ItemRune() {

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


	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return unLocalNames[par1ItemStack.getItemDamage()];
	}
 
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return Icons[0];
	}

	@Override
	public IIcon getIconFromDamage(int par1) {
		return Icons[par1];
	}


	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister IconRegister) {
		super.registerIcons(IconRegister);
		Icons = new IIcon[iconNames.length];
		for (int x = 0; x<iconNames.length; x++)
		{
			Icons[x] = IconRegister.registerIcon("shadowcraft:"+iconNames[x]);
		}
	}

	@Override
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List list) {
		for(int i = 0; i < Icons.length; i++) {
			ItemStack itemstack = new ItemStack(this, 1, i);
			list.add(itemstack);
		}}

}
