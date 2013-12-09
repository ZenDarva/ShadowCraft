package darva.shadowcraft.handlers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import darva.shadowcraft.Main;
import darva.shadowcraft.item.ShadowArmor;

public class ClientTickHandler implements ITickHandler {

	private EnumSet<TickType> ticksToGet;
	private boolean flying = false;
	private FlightType FlightData;
	private ItemStack armor;
	private ShadowArmor sh;
	
	public ClientTickHandler(EnumSet<TickType> type)
	{
		ticksToGet = type;
		
		
		
	}
	
	private boolean isPressed(int code)
	{
		if (Keyboard.isKeyDown(code))
			return true;
		return false;
	}
		@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
				
	}

	@Override
	public EnumSet<TickType> ticks() {

		return ticksToGet;
	}

	@Override
	public String getLabel() {

		return null;
	}
	
	private boolean armorHasTag(EntityPlayer player, String Tag)
	{
		armor = Main.getArmor(player);
		if (armor != null)
		{
			sh = (ShadowArmor)armor.getItem();
			if (armor.stackTagCompound != null)
			{
				if (armor.stackTagCompound.hasKey(Tag))
					return true;
			}
		}
		return false;
	}
	private FlightType getFlightData(EntityPlayer player)
	{
					FlightType ft;
					if (armor.hasTagCompound() && armor.stackTagCompound.hasKey("Flight"))
					{
						int value;
						value =armor.stackTagCompound.getInteger("Flight") ;
						switch (value)
						{
						case 1:
							ft = new FlightType(100, 200, .03, 1.0399999618530273D, true, false);
							return ft;
						case 2:
							ft = new FlightType(600, 50, .06D, 1.05D, false, false);
							return ft;
						/*default:
							return null;*/
						}
					}
					return null;
				
	}
	
	private class FlightType
	{
		int Duration;
		int Recharge;
		int RechargeMax;
		int DurationMax;
		ItemStack armor;
		double Climb;
		double Horizontal;
		boolean damageOnFall;
		boolean Creative;
		
		public FlightType(int D, int R, double C, double H, boolean d, boolean c)
		{
			Duration =D;
			DurationMax = D;
			Recharge =R;
			RechargeMax = R;
			Climb =C;
			Horizontal = H;
			damageOnFall = d;
			Creative = c;
		}
	}
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		
	if (tickData.length <1)
	return;
	EntityPlayer plr = (EntityPlayer) tickData[0];
	EntityClientPlayerMP cPlr = (EntityClientPlayerMP) plr;
	
	handleFlightNew(cPlr);
	handleInvisibility(cPlr);
	handleBlast(cPlr);
	}
	
	private void handleBlast(EntityClientPlayerMP cPlr)
	{
		if (Minecraft.getMinecraft().inGameHasFocus == false)
			return;

		if (armorHasTag(cPlr, "Blast"))
		{
			if (armor.stackTagCompound.getInteger("Charge") == 0)
				return;

			if (Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed && cPlr.getCurrentEquippedItem() == null)
			{
				Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
				cPlr.sendQueue.addToSendQueue(Main.buildPacket(PacketHandler.Packet_Blast, 1));
				cPlr.sendQueue.addToSendQueue(Main.buildPacket(PacketHandler.Packet_DamageArmor, -1));
			}
		}
	}
	
	private void handleInvisibility(EntityClientPlayerMP cPlr)
	{
		 int lightTarget = 0;
		 int curLight = 15;
		if (armorHasTag(cPlr, "Fog"))
		{
			if (armor.stackTagCompound.getInteger("Charge") == 0)
				return;
			switch (getInvisLightLevel(cPlr))
			{
			case 0:
				return;
			case 1:
				lightTarget = 5;
				break;
			case 2:
				lightTarget = 8;
				break;
			case 3:
				lightTarget = 12;
				break;
			default:
				return;
			}
			curLight = getRealLight((int)cPlr.posX, (int)cPlr.posY, (int)cPlr.posZ, cPlr.worldObj);
			if (curLight <lightTarget)
			{
				cPlr.sendQueue.addToSendQueue(Main.buildPacket(PacketHandler.Packet_HandleInvisibility, 1));
			}
			if (curLight >=lightTarget)
			{
				cPlr.sendQueue.addToSendQueue(Main.buildPacket(PacketHandler.Packet_HandleInvisibility, 2));
			}
		}
	}
	
	private int getRealLight(int x, int y, int z, World world)
	{
        int i1 = world.getSavedLightValue(EnumSkyBlock.Sky, x, y, z) - world.skylightSubtracted;
        float f = world.getCelestialAngleRadians(1.0F);

        if (f < (float)Math.PI)
        {
            f += (0.0F - f) * 0.2F;
        }
        else
        {
            f += (((float)Math.PI * 2F) - f) * 0.2F;
        }

        i1 = Math.round((float)i1 * MathHelper.cos(f));

        if (i1 < 0)
        {
            i1 = 0;
        }

        if (i1 > 15)
        {
            i1 = 15;
        }
        if (world.getSavedLightValue(EnumSkyBlock.Block,x, y, z) > i1)
        	return world.getSavedLightValue(EnumSkyBlock.Block,x, y, z) +1; 
        return i1;
	}
	
	
	
	private void handleFlight(EntityClientPlayerMP cPlr)
	{
		int jump = Minecraft.getMinecraft().gameSettings.keyBindJump.keyCode;

		if (armorHasTag(cPlr, "Flight"))
		{
			if (armor.stackTagCompound.getInteger("Charge") == 0)
				return;

			if (FlightData == null || !FlightData.armor.equals(armor))
			{
				FlightData = getFlightData(cPlr);
				FlightData.armor = armor;
			}
			if (FlightData.Duration <= 0)
			{
				FlightData.Recharge--;
				flying = false;
				
			}
			if (FlightData.Recharge == 0)
			{
				FlightData.Duration = FlightData.DurationMax;
				FlightData.Recharge= FlightData.RechargeMax;
				cPlr.sendQueue.addToSendQueue(Main.buildPacket(PacketHandler.Packet_DamageArmor,-1));
			}
			
			if (flying && FlightData.Duration > 0)
			{
				
				if (cPlr.motionY < FlightData.Climb)
				{
					cPlr.motionY = FlightData.Climb;
				}
				
				 if (!cPlr.onGround)

		            {
		                    cPlr.motionX *= FlightData.Horizontal;
		                    cPlr.motionZ *= FlightData.Horizontal;
		            }
					//Negate fall damage packet.
					cPlr.sendQueue.addToSendQueue(Main.buildPacket(PacketHandler.Packet_PreventFallDamage,1));
					FlightData.Duration--;
			}
			if (!flying && isPressed(jump) && FlightData.Duration > 0)
			{
				flying = true;
			}
			if (flying && !isPressed(jump))
			{
				flying = false;
			}
			if (FlightData.damageOnFall == false)
				cPlr.sendQueue.addToSendQueue(Main.buildPacket(PacketHandler.Packet_PreventFallDamage,1));;
			
		}
	}
	
	private void handleFlightNew(EntityClientPlayerMP cPlr)
	{
		int jump = Minecraft.getMinecraft().gameSettings.keyBindJump.keyCode;
		int boost = Keyboard.KEY_LCONTROL;
		NBTTagCompound tag;
		if (armorHasTag(cPlr, "Flight"))
		{
			tag = armor.stackTagCompound.getCompoundTag("FlightData");
			if (tag == null)
					return;
			if (armor.stackTagCompound.getInteger("Charge") == 0)
				return;
			if (tag.getInteger("Duration") == 0 )
			{
				if (tag.getInteger("Recharge") == tag.getInteger("MaxRecharge"))
				{
					cPlr.sendQueue.addToSendQueue(Main.buildPacket(PacketHandler.Packet_DamageArmor,-1));
				}
				cPlr.sendQueue.addToSendQueue(detailedPacket(PacketHandler.Packet_Flight_Detail, tag.getInteger("Recharge") -1, "Recharge" ));
				flying = false;
			}
			if (tag.getInteger("Recharge") == 0)
			{
				cPlr.sendQueue.addToSendQueue(detailedPacket(PacketHandler.Packet_Flight_Detail, tag.getInteger("MaxRecharge"), "Recharge" ));
				cPlr.sendQueue.addToSendQueue(detailedPacket(PacketHandler.Packet_Flight_Detail, tag.getInteger("MaxDuration"), "Duration" ));
				System.out.println("Recharged");
				
			}
			
			if (flying && (tag.getInteger("Duration") > 0 || tag.getInteger("MaxDuration") == -1))
			{
				
				if (cPlr.motionY < tag.getDouble("Lift"))
				{
					cPlr.motionY = tag.getDouble("Lift");
				}
				if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.keyCode))
				{
					cPlr.motionY = 0;
				}
				
				 if (!cPlr.onGround  && !Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.keyCode))
		            {
					 		cPlr.motionX+= -(Math.sin(Math.toRadians(cPlr.getRotationYawHead())) * tag.getDouble("Horizontal"));
					 		cPlr.motionZ+= (Math.cos(Math.toRadians(cPlr.getRotationYawHead())) * tag.getDouble("Horizontal"));
					 
		                    if (tag.getBoolean("Boost") && isPressed(boost)&& Minecraft.getMinecraft().inGameHasFocus == true)
		                    {
		                    	System.out.println("Boost");
		   					 cPlr.motionX += -(Math.sin(Math.toRadians(cPlr.getRotationYawHead())) * .08d);
							 cPlr.motionZ += (Math.cos(Math.toRadians(cPlr.getRotationYawHead())) * .08d);
		                    }
		                    cPlr.motionX = MathHelper.clamp_float((float)cPlr.motionX, -1.5f, 1.5f);
		                    cPlr.motionZ = MathHelper.clamp_float((float)cPlr.motionZ, -1.5f, 1.5f);
		            }
					//Negate fall damage packet.
					cPlr.sendQueue.addToSendQueue(Main.buildPacket(PacketHandler.Packet_PreventFallDamage,1));
					if (tag.getInteger("Duration")  == -100)
					{
						cPlr.sendQueue.addToSendQueue(Main.buildPacket(PacketHandler.Packet_DamageArmor,-1));
						cPlr.sendQueue.addToSendQueue(detailedPacket(PacketHandler.Packet_Flight_Detail, -1,"Duration"));
						tag.setInteger("Duration", -1);
					}
					else
						cPlr.sendQueue.addToSendQueue(detailedPacket(PacketHandler.Packet_Flight_Detail, tag.getInteger("Duration") -1, "Duration" ));
					
					
			}
			if (!flying && isPressed(jump) && (tag.getInteger("Duration") > 0 || tag.getInteger("MaxDuration") == -1) && Minecraft.getMinecraft().inGameHasFocus == true)
			{
				flying = true;
				System.out.println("flying");
			}
			if (flying && !isPressed(jump))
			{
				flying = false;
			}
			if (tag.getBoolean("Damage") == false)
				cPlr.sendQueue.addToSendQueue(Main.buildPacket(PacketHandler.Packet_PreventFallDamage,1));;
			
		}
	}
	private int getInvisLightLevel(EntityPlayer cPlr)
	{
		if (armorHasTag(cPlr, "Fog"))
		{
			return armor.stackTagCompound.getInteger("Fog");
		}
		return 0;
	}
	
	public static Packet250CustomPayload detailedPacket(int type, int data, String tag)
	{
	ByteArrayOutputStream bos = new ByteArrayOutputStream(12 + tag.length());
	DataOutputStream outputStream = new DataOutputStream(bos);
	try {
			outputStream.writeInt(type);
	        outputStream.writeInt(data);
	        outputStream.writeInt(tag.length());
	        outputStream.writeChars(tag);
	        
	        
	} catch (Exception ex) {
	        ex.printStackTrace();
	}

	Packet250CustomPayload packet = new Packet250CustomPayload();
	packet.channel = "ShadowFlight";
	packet.data = bos.toByteArray();
	packet.length = bos.size();
	return packet;
	}	
	
}
