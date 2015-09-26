package darva.shadowcraft.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darva.shadowcraft.entities.EntityBlast;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by James on 9/13/2015.
 */
public class PacketBlast implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class BlastHandler implements IMessageHandler<PacketBlast, IMessage>
    {

        @Override
        public IMessage onMessage(PacketBlast message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            EntityBlast Blast = new EntityBlast(player.worldObj, player);
            player.worldObj.spawnEntityInWorld(Blast);
            System.out.println("Blast!");
            return null;
        }
    }
}
