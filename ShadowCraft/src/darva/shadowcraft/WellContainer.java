package darva.shadowcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import darva.shadowcraft.entities.ShadowWellEntity;

public class WellContainer extends Container {
	private ShadowWellEntity well;
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return well.isUseableByPlayer(entityplayer);
	}
	
	public WellContainer(InventoryPlayer invPlayer, ShadowWellEntity ent)
	{
		well = ent;
		for(int x = 0; x < 9; x++) {
			  this.addSlotToContainer(new Slot(invPlayer, x, 7 + x * 20, 109));
			}
		
		System.out.println (this.well.worldObj.isRemote);
			for (int y = 0; y<3; y++)
				for(int x = 0; x< 9; x++) {	
				{
					this.addSlotToContainer(new Slot(invPlayer, x+(y*9)+9,7+x*20,46+ y*20));
					
				}
			}
			//Slot for cloak.
			this.addSlotToContainer(new CloakSlot(well, 0, 8,20));
			//Slot for Fuel
			this.addSlotToContainer(new FuelSlot(well, 1,48 ,20));
			//Slots for Runes will go here.
			this.addSlotToContainer(new RuneSlot(well, 2, 88,20));
			//Output slot for the new cloak.
			this.addSlotToContainer(new OutputSlot(well, 3, 148,20));
			
		
		
		
	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
	  Slot slot = getSlot(i);
	  
	  if(slot != null && slot.getHasStack()) {
	        ItemStack itemstack = slot.getStack();
	        ItemStack result = itemstack.copy();
	        if(i >= 36) {
	          if(!saneMergeItemStack(itemstack, 0, 36, false)) {
	        	  {
	        		  return null;  
	        	  }
	                
	          }
	        } 
	        else if(saneMergeItemStack(itemstack, 36, 36 + well.getSizeInventory(), false))
	        {
	          
	        }
	        else
	        {
	        	return null;
	        }
	        if(itemstack.stackSize == 0) {
	          slot.putStack(null);
	        } else {
	          slot.onSlotChanged();
	        }
	        slot.onPickupFromSlot(player, itemstack); 
	        return result;
	  }
	  return null;
	}
	class CloakSlot extends Slot
	{

		public CloakSlot(IInventory par1iInventory, int par2, int par3,
				int par4) {
			super(par1iInventory, par2, par3, par4);
		}

		@Override
		public boolean isItemValid(ItemStack itemstack) {
			// TODO Auto-generated method stub
			if (itemstack.itemID != Main.shadowArmor.itemID)
			{
				return false;
			}
			return true;
		}

		/* (non-Javadoc)
		 * @see net.minecraft.inventory.Slot#getSlotStackLimit()
		 */
		@Override
		public int getSlotStackLimit() {
			
			return 1;
		}

		
	}
	class FuelSlot extends Slot
	{

		public FuelSlot(IInventory par1iInventory, int par2, int par3,
				int par4) {
			super(par1iInventory, par2, par3, par4);
			// TODO Auto-generated constructor stub
		}

		/* (non-Javadoc)
		 * @see net.minecraft.inventory.Slot#getSlotStackLimit()
		 */
		@Override
		public int getSlotStackLimit() {
			
			return 64;
		}

		@Override
		public boolean isItemValid(ItemStack itemstack) {
			if (itemstack.itemID != Main.itemShadow.itemID)
			{
				return false;
			}
			return true;
		}
	}
		class RuneSlot extends Slot
		{

			public RuneSlot(IInventory par1iInventory, int par2, int par3,
					int par4) {
				
				super(par1iInventory, par2, par3, par4);
			}

			/* (non-Javadoc)
			 * @see net.minecraft.inventory.Slot#getSlotStackLimit()
			 */
			@Override
			public int getSlotStackLimit() {
				
				return 1;
			}

			@Override
			public boolean isItemValid(ItemStack itemstack) {
				if (itemstack.itemID != Main.runeItem.itemID)
				{
					return false;
				}
				return true;
			}
		}
		class OutputSlot extends Slot
		{

			public OutputSlot(IInventory par1iInventory, int par2, int par3,
					int par4) {
				super(par1iInventory, par2, par3, par4);
				// TODO Auto-generated constructor stub
			}

			/* (non-Javadoc)
			 * @see net.minecraft.inventory.Slot#onPickupFromSlot(net.minecraft.entity.player.EntityPlayer, net.minecraft.item.ItemStack)
			 */
			@Override
			public void onPickupFromSlot(EntityPlayer par1EntityPlayer,
					ItemStack par2ItemStack) {
				
				well.ApplyUpgrades(par2ItemStack);
				this.inventory.decrStackSize(0, 1);
				this.inventory.decrStackSize(1, well.amountToEat);
				this.inventory.decrStackSize(2, 1);
				this.inventory.decrStackSize(3, 1);
				super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
				this.inventory.onInventoryChanged();
			}

			@Override
			public boolean isItemValid(ItemStack itemstack) {
					return false;
			}
		}
		 protected boolean saneMergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4)
		    {
		        boolean flag1 = false;
		        int k = par2;

		        if (par4)
		        {
		            k = par3 - 1;
		        }

		        Slot slot;
		        ItemStack itemstack1;

		        if (par1ItemStack.isStackable())
		        {
		            while (par1ItemStack.stackSize > 0 && (!par4 && k < par3 || par4 && k >= par2))
		            {
		                slot = (Slot)this.inventorySlots.get(k);
		                itemstack1 = slot.getStack();
		                if ( slot.isItemValid(par1ItemStack) && itemstack1 != null && itemstack1.itemID == par1ItemStack.itemID && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(par1ItemStack, itemstack1)  )
		                {
		                	System.out.println("Slot:" +k);
		                    int l = itemstack1.stackSize + par1ItemStack.stackSize;

		                    if (l <= par1ItemStack.getMaxStackSize())
		                    {
		                        par1ItemStack.stackSize = 0;
		                        itemstack1.stackSize = l;
		                        slot.onSlotChanged();
		                        flag1 = true;
		                    }
		                    else if (itemstack1.stackSize < par1ItemStack.getMaxStackSize())
		                    {
		                        par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - itemstack1.stackSize;
		                        itemstack1.stackSize = par1ItemStack.getMaxStackSize();
		                        slot.onSlotChanged();
		                        flag1 = true;
		                    }
		                }
		                if (par4)
		                {
		                    --k;
		                }
		                else
		                {
		                    ++k;
		                }
		            }
		        }

		        if (par1ItemStack.stackSize > 0)
		        {
		            if (par4)
		            {
		                k = par3 - 1;
		            }
		            else
		            {
		                k = par2;
		            }

		            while (!par4 && k < par3 || par4 && k >= par2)
		            {
		                slot = (Slot)this.inventorySlots.get(k);
		                itemstack1 = slot.getStack();
		                if (itemstack1 == null && slot.isItemValid(par1ItemStack))
		                {
		                    slot.putStack(par1ItemStack.copy());
		                    slot.onSlotChanged();
		                    par1ItemStack.stackSize = 0;
		                    flag1 = true;
		                    break;
		                }

		                if (par4)
		                {
		                    --k;
		                }
		                else
		                {
		                    ++k;
		                }
		            }
		        }

		        return flag1;
		    }

		/* (non-Javadoc)
		 * @see net.minecraft.inventory.Container#onContainerClosed(net.minecraft.entity.player.EntityPlayer)
		 */
		@Override
		public void onContainerClosed(EntityPlayer par1EntityPlayer) {
			super.onContainerClosed(par1EntityPlayer);

	        if (!this.well.worldObj.isRemote)
	        {
	        	for (int k = 0; k < 4; k++)
	        	{
	            ItemStack itemstack = this.well.getStackInSlotOnClosing(k);

	            if (itemstack != null)
	            {
	                par1EntityPlayer.dropPlayerItem(itemstack);
	            }
	        	}
	        }
			super.onContainerClosed(par1EntityPlayer);
		}

}
