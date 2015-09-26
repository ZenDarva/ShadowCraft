package darva.shadowcraft.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * Created by James on 9/13/2015.
 */
public class PacketHandleInvisibility implements IMessage {

    public boolean invisible;
    @Override
    public void fromBytes(ByteBuf buf) {
        invisible = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(invisible);
    }
    public PacketHandleInvisibility(Boolean Invisible)
    {
     invisible=true;
    }
    public PacketHandleInvisibility(){};

    public static class HandleInvisiblityHandler implements IMessageHandler<PacketHandleInvisibility, IMessage>
    {
        //Stupid name ehh?
        @Override
        public IMessage onMessage(PacketHandleInvisibility message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;

            if (message.invisible)
            {
                player.addPotionEffect(new PotionEffect(Potion.invisibility.getId(), 200, 0));
            }
            else
            {
                player.removePotionEffect(Potion.invisibility.getId());
            }
            return null;
        }
    }
}
