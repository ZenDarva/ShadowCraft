package darva.shadowcraft.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by James on 9/13/2015.
 */
public class NetworkHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("shadowcraft");


    public static void registerPackets()
    {
        INSTANCE.registerMessage(PacketDamageArmor.DamageMessageHandler.class, PacketDamageArmor.class,0, Side.SERVER);
        INSTANCE.registerMessage(PacketBlast.BlastHandler.class, PacketBlast.class,1, Side.SERVER);
        INSTANCE.registerMessage(PacketFlightDetail.FlightDetailHandler.class, PacketFlightDetail.class,2 ,Side.SERVER);
        INSTANCE.registerMessage(PacketHandleInvisibility.HandleInvisiblityHandler.class, PacketHandleInvisibility.class,3, Side.SERVER);
        INSTANCE.registerMessage(PacketPreventFallDamage.PreventFallDamageHandler.class, PacketPreventFallDamage.class,4, Side.SERVER);


    }
}
