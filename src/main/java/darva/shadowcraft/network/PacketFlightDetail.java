package darva.shadowcraft.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darva.shadowcraft.Main;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by James on 9/13/2015.
 */
public class PacketFlightDetail implements IMessage {
    int value;
    String name;
    @Override
    public void fromBytes(ByteBuf buf) {
        value = buf.readInt();
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        name = new String(bytes);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(value);
        buf.writeBytes(name.getBytes());
    }

    public PacketFlightDetail(int Value, String Name)
    {
        value = Value;
        name = Name;
    }
    public PacketFlightDetail(){};

    public static class FlightDetailHandler implements IMessageHandler<PacketFlightDetail, IMessage>
    {

        @Override
        public IMessage onMessage(PacketFlightDetail message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            ItemStack armor;
            NBTTagCompound tC;
            armor = Main.getArmor(player);
            if (armor == null)
            {
                return null;
            }
            tC = armor.stackTagCompound.getCompoundTag("FlightData");
            if (tC == null)
                return null;
            tC.setInteger(message.name, message.value);
            return null;
        }
    }
}
