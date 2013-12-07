package darva.shadowcraft.item;

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
	public boolean isRepairable() {
		
		return false;
	}
	
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getItemDisplayName(net.minecraft.item.ItemStack)
	 */
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#setDamage(net.minecraft.item.ItemStack, int)
	 */
	@Override
	public void setDamage(ItemStack stack, int damage) {
			;
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
		/*
		System.out.println("Charge: " + charge + " MaxCharge: " + maxCharge );
		System.out.println("Percent:" + Percent);
		System.out.println("incoming Damage: " + amount);
		System.out.println("Result " + (this.getMaxDamage() - (this.getMaxDamage() * Percent)));
		*/
	}
	
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getDamage(net.minecraft.item.ItemStack)
	 */
	@Override
	public int getDamage(ItemStack stack) {
		
		return MathHelper.clamp_int(super.getDamage(stack), 0, this.getMaxDamage() -1);
	}


	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
			setupNBT(par1ItemStack);
		super.onCreated(par1ItemStack, par2World, par3EntityPlayer);
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
		lTag = new NBTTagList();
		tag.setTag("Lore", lTag);
		
	}
	public ShadowArmor(int par1, EnumArmorMaterial par2EnumArmorMaterial,
			int par3, int par4) {
		super(par1, par2EnumArmorMaterial, par3, par4);
		this.setUnlocalizedName("shadowcloak");
		LanguageRegistry.addName(this, "Shadow Cloak");
		this.maxStackSize=1;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving,
	ItemStack itemStack, int armorSlot) {
		ModelBiped armorModel = null;
		if (itemStack != null && itemStack.getItem() instanceof ShadowArmor)
		{
			armorModel = Main.proxy.getArmorModel(0);
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
	/* (non-Javadoc)
	 * @see net.minecraft.item.ItemArmor#registerIcons(net.minecraft.client.renderer.texture.IconRegister)
	 */
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		// TODO Auto-generated method stub
		
		//this.itemIcon = par1IconRegister.registerIcon("iron armor"); //Temporary.
		super.registerIcons(par1IconRegister);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getArmorTexture(net.minecraft.item.ItemStack, net.minecraft.entity.Entity, int, java.lang.String)
	 */
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			String type) {
		// TODO Auto-generated method stub
		
		return "shadowtrees:textures/armor/shadowcloak2.png";
	}
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		// TODO Auto-generated method stub
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		
		
	}

	
}
