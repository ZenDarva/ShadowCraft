package darva.shadowcraft.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by James on 9/13/2015.
 */
public class PacketPreventFallDamage implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class PreventFallDamageHandler implements IMessageHandler<PacketPreventFallDamage, IMessage>
    {

        @Override
        public IMessage onMessage(PacketPreventFallDamage message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            player.fallDistance = 0.0f;
            return null;
        }
    }
}
