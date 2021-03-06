package darva.shadowcraft.entities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darva.shadowcraft.ShadowParticle;
import darva.shadowcraft.item.ShadowArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import java.util.Random;

public class ShadowWellEntity extends TileEntity implements IInventory {

	private ItemStack[] inventory;
	private int count = 0;
	public int amountToEat;

	
	public ShadowWellEntity()
	{
		inventory = new ItemStack[this.getSizeInventory()];
		this.canUpdate();
	}
	@Override
	public void updateEntity() {
		count +=1;
		if (count < 20 && this.worldObj.isRemote)
			return;
		count = 0;
		
		if (this.worldObj.isRemote)
		{
			
			particleSpam();
		}
		super.updateEntity();
	}
	
	@SideOnly(Side.CLIENT)
	private void particleSpam()
	{
		//Particle spam.
		int total = new Random().nextInt(4);
		for (int x = 0;x <= total; x++) 
			Minecraft.getMinecraft().effectRenderer.addEffect(new ShadowParticle(this.worldObj, this.xCoord +MathHelper.clamp_float((float)( Math.random()),.2f,.8f),this.yCoord +.1,this.zCoord + MathHelper.clamp_float((float)(Math.random()),.2f,.8f), 0,0,0 ));
		
	}
	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO Auto-generated method stub
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		// TODO Auto-generated method stub
		ItemStack stack;
		stack = inventory[i];
		if (stack == null)
			return null;
		if (j >= inventory[i].stackSize)
		{
			inventory[i]=null;
			if (i != 3)
				updateNewCloak();
			return stack;
		}
		else
		{
			stack = stack.splitStack(j);
			if (i != 3)
				updateNewCloak();
			return stack;
		}
		
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (inventory[i] != null)
        {
            ItemStack itemstack = this.inventory[i];
            this.inventory[i] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i]= itemstack;
		if (i != 4)
			updateNewCloak();
	}

	@Override
	public String getInventoryName() {
		return "Well of Shadows";

	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}



	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;

	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}


	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
				return true;
	}

	
	private void setupFlightData(ItemStack stack, int MaxDuration, int MaxRecharge, double lift, double horizontal, boolean damage, boolean boost )
	{
		NBTTagCompound tag;
		tag = new NBTTagCompound();
		tag.setInteger("MaxDuration", MaxDuration);
		tag.setInteger("Duration", MaxDuration);
		tag.setInteger("MaxRecharge", MaxRecharge);
		tag.setInteger("Recharge", MaxRecharge);
		tag.setDouble("Lift", lift);
		tag.setDouble("Horizontal", horizontal);
		tag.setBoolean("Damage", damage);
		tag.setBoolean("Boost", boost);
		stack.stackTagCompound.setTag("FlightData", tag);

	}
	
	private void updateNewCloak()
	{
		
		ItemStack newCloak;
		ItemStack oldCloak = this.inventory[0];
		boolean Changed = false;
		boolean Valid = false;
		ShadowArmor armor;
		
		if (this.inventory[0] != null)
		{
			newCloak = this.inventory[0].copy();
			armor = (ShadowArmor) oldCloak.getItem();
			if (oldCloak.stackTagCompound == null)
			{
				armor = (ShadowArmor) oldCloak.getItem();
				armor.setupNBT(oldCloak);
						
			}
			newCloak.stackTagCompound = (NBTTagCompound) oldCloak.stackTagCompound.copy();
			if (this.inventory[1] != null)
			{
				
				amountToEat = newCloak.stackTagCompound.getInteger("MaxCharge") - newCloak.stackTagCompound.getInteger("Charge");
				if (amountToEat > inventory[1].stackSize)
					amountToEat = inventory[1].stackSize;
				armor.changeCharge(newCloak, amountToEat);
				newCloak.stackTagCompound.setBoolean("Upgraded", true);
				Valid = true;
				Changed = true;
			}
			
			
				if (this.inventory[2] != null)
				{
					switch (this.inventory[2].getItemDamage())
					{
					default:
						//This should be the blank iron rune.  Not Valid.
						break;
					case 1:
						newCloak = parseRune(newCloak, "Flight", 1);
						if (newCloak != null)
								{
							Valid = true;
							Changed = true;
							setupFlightData(newCloak, 100,200, .10d, .01D, true, false);
							
								}
						break;

					case 2:
						newCloak = parseRune(newCloak, "Flight", 2);
						if (newCloak != null)
								{
							Valid = true;
							Changed = true;
							setupFlightData(newCloak, 600,50,.15d, .03d, false, false);

								}
						break;
						case 3:
							newCloak = parseRune(newCloak, "Flight", 3);
							if (newCloak != null)
									{
								Valid = true;
								Changed = true;
								setupFlightData(newCloak, -1,-1, .3, .05d, false, true);
									}
							break;
						case 4:
							newCloak = parseRune(newCloak, "Dissipation", 1);
							if (newCloak != null)
									{
								Valid = true;
								Changed = true;
									}
							break;
						case 5:
							newCloak = parseRune(newCloak, "Dissipation", 2);
							if (newCloak != null)
									{
								Valid = true;
								Changed = true;
									}
							break;
						case 6:
							newCloak = parseRune(newCloak, "Dissipation", 3);
							if (newCloak != null)
									{
								Valid = true;
								Changed = true;
									}
							break;
						case 7:
							newCloak = parseRune(newCloak, "Fog", 1);
							if (newCloak != null)
									{
								Valid = true;
								Changed = true;
									}
							break;
						case 8:
							newCloak = parseRune(newCloak, "Fog", 2);
							if (newCloak != null)
									{
								Valid = true;
								Changed = true;
									}
							break;
						case 9:
							newCloak = parseRune(newCloak, "Fog", 1);
							if (newCloak != null)
									{
								Valid = true;
								Changed = true;
									}
							break;
						case 10:
							newCloak = parseRune(newCloak, "Concentration", 1);
							if (newCloak != null)
									{
								Valid = true;
								Changed = true;
								newCloak.stackTagCompound.setInteger("MaxCharge", 50);
								((ShadowArmor)newCloak.getItem()).changeCharge(newCloak, 0);

									}
							break;
						case 11:
							newCloak = parseRune(newCloak, "Concentration", 2);
							if (newCloak != null)
									{
								Valid = true;
								Changed = true;
								newCloak.stackTagCompound.setInteger("MaxCharge", 100);
								((ShadowArmor)newCloak.getItem()).changeCharge(newCloak, 0);

									}
							break;
						case 12:
							newCloak = parseRune(newCloak, "Concentration", 3);
							if (newCloak != null)
									{
								Valid = true;
								Changed = true;
								newCloak.stackTagCompound.setInteger("MaxCharge", 200);
								((ShadowArmor)newCloak.getItem()).changeCharge(newCloak, 0);

									}
							break;
						case 13:
							newCloak = parseRune(newCloak, "Blast", 1);
							if (newCloak != null)
									{
								Valid = true;
								Changed = true;
									}
							break;
						case 14:
							newCloak = parseRune(newCloak, "Blast", 2);
							if (newCloak != null)
									{
								Valid = true;
								Changed = true;
									}
							break;
						case 15:
							newCloak = parseRune(newCloak, "Blast", 3);
							if (newCloak != null)
									{
								Valid = true;
								Changed = true;
									}
							break;
						case 16:
							//Diamond Blank Rune.
							Valid = false;
							Changed = false;
					}
			
				}
				if (Valid && Changed)
				{
					inventory[3] = newCloak;
					this.updateContainingBlockInfo();
				}
				else if (inventory[3] != null)
				{
					this.setInventorySlotContents(3, null);
				}
		}
	}
	
	private ItemStack parseRune(ItemStack newCloak, String Tag, int Level)
	{
		if (newCloak.stackTagCompound.getInteger(Tag) >= Level ||
				newCloak.stackTagCompound.getInteger("NumUpgrades") > 3)
			return null;
		else {
			newCloak.stackTagCompound.setBoolean("Upgraded", true);
			
			newCloak.stackTagCompound.setInteger(Tag, Level);
			
			return newCloak;
			}

	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#onInventoryChanged()
	 */
//	@Override
//	public void onInventoryChanged() {
//
//		if (!this.worldObj.isRemote)
//		{
//		this.updateNewCloak();
//		}
//		super.onInventoryChanged();
//	}

}
