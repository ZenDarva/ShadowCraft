package darva.shadowcraft.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.Mod.EventHandler;
import darva.shadowcraft.Main;
import darva.shadowcraft.item.ShadowArmor;


public class DamageHandler {

	private ItemStack armor;
	@EventHandler
	@ForgeSubscribe
	public void onEntityDamaged(LivingHurtEvent event)
	{
		
		if (!(event.entity instanceof EntityPlayer))
		{
			return; //Not a player... we don't care.
		}
		EntityPlayer player = (EntityPlayer) event.entity;
		
		armor = Main.getArmor(player);

		if (armor == null)
				return;
		if (armor.stackTagCompound == null)
			return;
		if (armor.stackTagCompound.getInteger("Charge") == 0)
			return;

		if (armor.stackTagCompound.hasKey("Dissipation"))
		{
			switch (armor.stackTagCompound.getInteger("Dissipation"))
			{
			case 1:
				event.ammount -=1;
				break;
			case 2:
				event.ammount -=2;
				break;
			case 3:
				event.ammount -=4;
				break;			
			}
		}
		if (event.ammount > 0)
			event.setResult(Result.ALLOW);
		else
			event.setCanceled(true);
		
	}
	
	private void updateDamage(EntityPlayer player, ItemStack armor)
	{
		ShadowArmor sh;
		if (armor.stackTagCompound.hasKey("DisDam"))
		{
			int dam = armor.stackTagCompound.getInteger("DisDam");
			dam+=1;
			if (dam > 5);
			{
				dam = 0;
				sh = (ShadowArmor) armor.getItem();
				sh.changeCharge(armor, 1);
				return;
			}
		}
		else
		{
			armor.stackTagCompound.setInteger("DisDam", 1);
		}
	}
}
