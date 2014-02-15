package darva.shadowcraft.item;

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darva.shadowcraft.Main;

public class ShadowArmor extends ItemArmor{

	
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		// TODO Auto-generated method stub
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		
		NBTTagCompound tag;
		tag = par1ItemStack.stackTagCompound;
		
		if (tag == null)
			return;
		if (!tag.getBoolean("Upgraded"))
		{
			return;
		}
		
		//Need a cleaner way to handle rune descriptions.
		switch (tag.getInteger("Flight"))
		{
		default:
			break;
		case 1:
			par3List.add("Iron Flight Rune");
			break;
		case 2:
			par3List.add("Diamond Flight Rune");
			break;
		case 3:
			par3List.add("Emerald Flight Rune");
			break;
		}
		switch (tag.getInteger("Dissipation"))
		{
		default:
			break;
		case 1:
			par3List.add("Iron Dissipation Rune");
			break;
		case 2:
			par3List.add("Diamond Dissipation Rune");
			break;
		case 3:
			par3List.add("Emerald Dissipation Rune");
			break;
		}
		switch (tag.getInteger("Fog"))
		{
		default:
			break;
		case 1:
			par3List.add("Iron Fog Rune");
			break;
		case 2:
			par3List.add("Diamond Fog Rune");
			break;
		case 3:
			par3List.add("Emerald Fog Rune");
			break;
		}
		switch (tag.getInteger("Concentration"))
		{
		default:
			break;
		case 1:
			par3List.add("Iron Concentration Rune");
			break;
		case 2:
			par3List.add("Diamond Concentration Rune");
			break;
		case 3:
			par3List.add("Emerald Concentration Rune");
			break;
		}
		switch (tag.getInteger("Blast"))
		{
		default:
			break;
		case 1:
			par3List.add("Iron Blast Rune");
			break;
		case 2:
			par3List.add("Diamond Blast Rune");
			break;
		case 3:
			par3List.add("Emerald Blast Rune");
			break;
		}
	}


	@Override
	public boolean isRepairable() {
		
		return false;
	}
	
	
	@Override
	public void setDamage(ItemStack stack, int damage) {
			;//Prevent vanilla damage mechanics.
			//All internal damage is handled through changeCharge.
		return;
	}
	
	public void changeCharge(ItemStack stack, int amount) {
		int charge;
		int maxCharge;
		charge = stack.stackTagCompound.getInteger("Charge");
		maxCharge = stack.stackTagCompound.getInteger("MaxCharge");
		charge+=amount;
		
		stack.stackTagCompound.setInteger("Charge", charge);
		 double Percent = ((double)charge/(double)maxCharge);
		super.setDamage(stack, (int)(this.getMaxDamage() - (this.getMaxDamage() * Percent)) ) ;
	}
	
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getDamage(net.minecraft.item.ItemStack)
	 */
	@Override
	public int getDamage(ItemStack stack) {
		//Clamping damage to MaxDamage -1 prevents item from being destroyed.
		return MathHelper.clamp_int(super.getDamage(stack), 0, this.getMaxDamage() -1);
	}


	
	public void setupNBT(ItemStack par1ItemStack)
	{
		NBTTagList lTag;
		
		NBTTagCompound tag = new NBTTagCompound();
		par1ItemStack.setTagCompound( new NBTTagCompound( ) );
		par1ItemStack.stackTagCompound.setInteger("Charge", 20);
		par1ItemStack.stackTagCompound.setInteger("MaxCharge", 20);
		par1ItemStack.stackTagCompound.setBoolean("Upgraded", false);
		par1ItemStack.stackTagCompound.setCompoundTag("display", tag);
		
	}
	public ShadowArmor(int par1, EnumArmorMaterial par2EnumArmorMaterial,
			int par3, int par4) {
		super(par1, par2EnumArmorMaterial, par3, par4);
		this.setUnlocalizedName("shadowcloak");
		LanguageRegistry.addName(this, "Shadow Cloak");
		this.maxStackSize=1;
		
		
	}
	@Override
	public boolean requiresMultipleRenderPasses() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving,
	ItemStack itemStack, int armorSlot) {
		ModelBiped armorModel = null;
		ShadowArmor sh;
		if (itemStack != null && itemStack.getItem() instanceof ShadowArmor)
		{
			armorModel = Main.proxy.getArmorModel(0);
			if (entityLiving.isInvisible())
			{
				//If player is invisible, armor should be too.
				return null;
			}
			if(armorModel != null){
				armorModel.bipedHead.showModel = armorSlot == 0;
				armorModel.bipedHeadwear.showModel = armorSlot == 0;
				armorModel.bipedBody.showModel = armorSlot == 1 || armorSlot == 2;
				armorModel.bipedRightArm.showModel = armorSlot == 1;
				armorModel.bipedLeftArm.showModel = armorSlot == 1;
				armorModel.bipedRightLeg.showModel = armorSlot == 2 || armorSlot == 3;
				armorModel.bipedLeftLeg.showModel = armorSlot == 2 || armorSlot == 3;

				armorModel.isSneak = entityLiving.isSneaking();
				armorModel.isRiding = entityLiving.isRiding();
				armorModel.isChild = entityLiving.isChild();
				armorModel.heldItemRight = entityLiving.getCurrentItemOrArmor(0) != null ? 1 :0;
				if(entityLiving instanceof EntityPlayer){
				armorModel.aimedBow =((EntityPlayer)entityLiving).getItemInUseDuration() > 2;
				}
				return armorModel;
				}
			
		}
	return null;
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("shadowtrees:shadowcloak"); //Temporary.
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			String type) {
		if (entity.isInvisible()) //If player is invisible, armor should be too.
			return "shadowtrees:textures/armor/blank.png";
		else
			return "shadowtrees:textures/armor/shadowcloak2.png";
	}

	
}
