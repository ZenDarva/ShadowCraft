package darva.shadowcraft.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import darva.shadowcraft.Main;
import darva.shadowcraft.entities.EntityBlast;
import darva.shadowcraft.item.ShadowArmor;

//ToDo: Rewrite... This works, but isn't pretty.
public class PacketHandler implements IPacketHandler {
	public final static int Packet_DamageArmor = 1;
	public final static int Packet_PreventFallDamage = 2;
	public final static int Packet_HandleInvisibility = 3;
	public final static int Packet_Blast = 4;
	public final static int Packet_Flight_Detail = 5;
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		
		if (side == Side.SERVER) {
			
		serverRec(packet,player);
		
		} else if (side == Side.CLIENT) {
			clientRec(packet,player);
		} else {
		        // We have an errornous state! 
		}
		
	}
	
	private void serverRec(Packet250CustomPayload packet, Player playerEnt)
	{
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		ItemStack armor;
        int type;
        int Data;
        
		try {
			type = inputStream.readInt();
			Data = inputStream.readInt();
			
				EntityPlayerMP player = (EntityPlayerMP) playerEnt;
				
				switch (type)
				{
				case Packet_Flight_Detail:
					int length = inputStream.readInt();
					StringBuilder sb = new StringBuilder();
					String sTag;
					NBTTagCompound tC;
					armor = getArmor(player);
					if (armor == null)
					{
						return;
					}
					tC = armor.stackTagCompound.getCompoundTag("FlightData");
					if (tC == null)
						return;
					for (int x = 0; x<length; x++)
					{
						sb.append(inputStream.readChar());
					}
					sTag = sb.toString();
					tC.setInteger(sTag, Data);
					
				case  Packet_PreventFallDamage:
					player.fallDistance = 0.0f;
					break;
				case Packet_DamageArmor:
					armor = getArmor(player);
					if (armor == null)
						return;
					((ShadowArmor)armor.getItem()).changeCharge(armor, Data);
					break;
				
				case Packet_HandleInvisibility:
					if (Data == 1)
					{
						player.addPotionEffect(new PotionEffect(Potion.invisibility.getId(), 200, 0));
					}
					if (Data == 2)
					{
						player.removePotionEffect(Potion.invisibility.getId());
					}
					break;
				case Packet_Blast:
				{
					if (Data == 1) //Damage blast.
					{
						EntityBlast Blast = new EntityBlast(player.worldObj, player);
						
						player.worldObj.spawnEntityInWorld(Blast);
					}
				}
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void clientRec(Packet250CustomPayload packet, Player player)
	{
		
	}
	
	private ItemStack getArmor(EntityPlayer player)
	{
		if (player == null )
		{
			return null;
		}
		if (player.inventory == null)
		{
			return null;
		}
		if (player.inventory.armorInventory == null)
			return null;
		for (ItemStack i : player.inventory.armorInventory)
		{
			if (i != null)
				if (i.itemID == Main.shadowArmor.itemID)
				{
					if (i.hasTagCompound() && i.stackTagCompound.hasKey("Flight"))
					{
						
						return i;
						
					}
					return null;		
				}
		}
		return null;
	}
}
