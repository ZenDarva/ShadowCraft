package darva.shadowcraft;

import darva.shadowcraft.network.NetworkHandler;
import net.minecraft.client.model.ModelBiped;

public class CommonProxy {

    public void RegisterPackets()
    {
        NetworkHandler.registerPackets();
    }

    public void registerRenderers() {
        //Stub, nothing to do here, servers don't render.

    }

    public ModelBiped getArmorModel(int id) {
        return null;
    }

    public void registerTickHandlers() {
        //Stub.  All tick's handled client side.
    }

}
