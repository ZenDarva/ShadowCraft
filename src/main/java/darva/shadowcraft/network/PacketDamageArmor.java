package darva.shadowcraft.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darva.shadowcraft.Main;
import darva.shadowcraft.item.ShadowArmor;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

/**
 * Created by James on 9/13/2015.
 */
public class PacketDamageArmor implements IMessage {

    public int amount;

    public PacketDamageArmor(int Amount)
    {
        amount = Amount;
    }
    public PacketDamageArmor(){}

    @Override
    public void fromBytes(ByteBuf buf) {
        amount = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(amount);
    }

    public static class DamageMessageHandler implements IMessageHandler<PacketDamageArmor, IMessage>
    {

        @Override
        public IMessage onMessage(PacketDamageArmor message, MessageContext ctx) {
            ItemStack armor;
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            armor = Main.getArmor(player);
            if (armor == null)
                return null;
            ((ShadowArmor)armor.getItem()).changeCharge(armor, message.amount);
            return null;
        }
    }
}
