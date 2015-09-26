package darva.shadowcraft.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import darva.shadowcraft.Main;
import darva.shadowcraft.item.ShadowArmor;
import darva.shadowcraft.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;


public class ClientTickHandler {

    private boolean flying = false;
    private ItemStack armor;
    private ShadowArmor sh;


    private boolean isPressed(int code) {
        if (Keyboard.isKeyDown(code))
            return true;
        return false;
    }

    private boolean armorHasTag(EntityPlayer player, String Tag) {
        armor = Main.getArmor(player);
        if (armor != null) {
            sh = (ShadowArmor) armor.getItem();
            if (armor.stackTagCompound != null) {
                if (armor.stackTagCompound.hasKey(Tag))
                    return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public void tickStart(TickEvent.PlayerTickEvent event) {

        EntityPlayer plr = event.player;

        handleFlight(plr);
        handleInvisibility(plr);
        handleBlast(plr);
    }

    private void handleBlast(EntityPlayer cPlr) {
        if (Minecraft.getMinecraft().inGameHasFocus == false)
            return;

        if (armorHasTag(cPlr, "Blast")) {
            if (armor.stackTagCompound.getInteger("Charge") == 0)
                return;

            if (Minecraft.getMinecraft().gameSettings.keyBindUseItem.getIsKeyPressed() && cPlr.getCurrentEquippedItem() == null) {
                //Minecraft.getMinecraft().gameSettings.keyBindUseItem. = false;
                NetworkHandler.INSTANCE.sendToServer(new PacketBlast());
                NetworkHandler.INSTANCE.sendToServer(new PacketDamageArmor(-1));
            }
        }
    }

    private void handleInvisibility(EntityPlayer cPlr) {
        int lightTarget = 0;
        int curLight = 15;
        if (armorHasTag(cPlr, "Fog")) {
            if (armor.stackTagCompound.getInteger("Charge") == 0)
                return;
            switch (getInvisLightLevel(cPlr)) {
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
            curLight = getRealLight((int) cPlr.posX, (int) cPlr.posY, (int) cPlr.posZ, cPlr.worldObj);
            if (curLight < lightTarget) {
                //cPlr.sendQueue.addToSendQueue(Main.buildPacket(PacketHandler.Packet_HandleInvisibility, 1));
                NetworkHandler.INSTANCE.sendToServer(new PacketHandleInvisibility(true));
            }
            if (curLight >= lightTarget) {
                NetworkHandler.INSTANCE.sendToServer(new PacketHandleInvisibility(false));
            }
        }
    }

    public static int getRealLight(int x, int y, int z, World world) {
        int i1 = world.getSavedLightValue(EnumSkyBlock.Sky, x, y, z) - world.skylightSubtracted;
        float f = world.getCelestialAngleRadians(1.0F);

        if (f < (float) Math.PI) {
            f += (0.0F - f) * 0.2F;
        } else {
            f += (((float) Math.PI * 2F) - f) * 0.2F;
        }

        i1 = Math.round((float) i1 * MathHelper.cos(f));

        if (i1 < 0) {
            i1 = 0;
        }

        if (i1 > 15) {
            i1 = 15;
        }
        if (world.getSavedLightValue(EnumSkyBlock.Block, x, y, z) > i1)
            return world.getSavedLightValue(EnumSkyBlock.Block, x, y, z) + 1;
        return i1;
    }


    private void handleFlight(EntityPlayer cPlr) {
        int jump = Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode();
        int boost = Keyboard.KEY_LCONTROL;
        NBTTagCompound tag;
        if (armorHasTag(cPlr, "Flight")) {
            tag = armor.stackTagCompound.getCompoundTag("FlightData");
            if (tag == null)
                return;
            if (armor.stackTagCompound.getInteger("Charge") == 0)
                return;
            if (tag.getInteger("Duration") == 0) {
                if (tag.getInteger("Recharge") == tag.getInteger("MaxRecharge")) {
                    NetworkHandler.INSTANCE.sendToServer(new PacketDamageArmor(-1));
                }
                NetworkHandler.INSTANCE.sendToServer(new PacketFlightDetail(-1, "Recharge"));
                flying = false;
            }
            if (tag.getInteger("Recharge") == 0) {
                NetworkHandler.INSTANCE.sendToServer(new PacketFlightDetail(tag.getInteger("MaxRecharge"), "Recharge"));
                NetworkHandler.INSTANCE.sendToServer(new PacketFlightDetail(tag.getInteger("MaxDuration"), "Duration"));

            }

            if (flying && (tag.getInteger("Duration") > 0 || tag.getInteger("MaxDuration") == -1)) {

                if (cPlr.motionY < tag.getDouble("Lift")) {
                    cPlr.motionY = tag.getDouble("Lift");
                }
                if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode())) {
                    cPlr.motionY = 0;
                }

                if (!cPlr.onGround && !Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode())) {
                    cPlr.motionX += -(Math.sin(Math.toRadians(cPlr.getRotationYawHead())) * tag.getDouble("Horizontal"));
                    cPlr.motionZ += (Math.cos(Math.toRadians(cPlr.getRotationYawHead())) * tag.getDouble("Horizontal"));

                    if (tag.getBoolean("Boost") && isPressed(boost) && Minecraft.getMinecraft().inGameHasFocus == true) {
                        cPlr.motionX += -(Math.sin(Math.toRadians(cPlr.getRotationYawHead())) * .08d);
                        cPlr.motionZ += (Math.cos(Math.toRadians(cPlr.getRotationYawHead())) * .08d);
                    }
                    cPlr.motionX = MathHelper.clamp_float((float) cPlr.motionX, -1.5f, 1.5f);
                    cPlr.motionZ = MathHelper.clamp_float((float) cPlr.motionZ, -1.5f, 1.5f);
                }
                //Negate fall damage packet.
                NetworkHandler.INSTANCE.sendToServer(new PacketPreventFallDamage());
                if (tag.getInteger("Duration") == -100) {
                    NetworkHandler.INSTANCE.sendToServer(new PacketDamageArmor(-1));
                    NetworkHandler.INSTANCE.sendToServer(new PacketFlightDetail(-1, "Duration"));
                    tag.setInteger("Duration", -1);
                } else
                    NetworkHandler.INSTANCE.sendToServer(new PacketFlightDetail(-1, "Duration"));


            }
            if (!flying && isPressed(jump) && (tag.getInteger("Duration") > 0 || tag.getInteger("MaxDuration") == -1) && Minecraft.getMinecraft().inGameHasFocus == true) {
                flying = true;
            }
            if (flying && !isPressed(jump)) {
                flying = false;
            }
            if (tag.getBoolean("Damage") == false)
                NetworkHandler.INSTANCE.sendToServer(new PacketPreventFallDamage());
        }
    }

    private int getInvisLightLevel(EntityPlayer cPlr) {
        if (armorHasTag(cPlr, "Fog")) {
            return armor.stackTagCompound.getInteger("Fog");
        }
        return 0;
    }


}
