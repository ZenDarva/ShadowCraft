package darva.shadowcraft;

import darva.shadowcraft.blocks.ShadowWell;
import darva.shadowcraft.entities.ShadowWellEntity;
import darva.shadowcraft.models.ShadowCloak;
import darva.shadowcraft.models.WellModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRender implements IItemRenderer {

	private final WellModel wellModel;
	
	
	public ItemRender()
	{
		wellModel = new WellModel();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		if (item.itemID == Main.shadowWell.blockID)
			TileEntityRenderer.instance.renderTileEntityAt(new ShadowWellEntity(), 0, 0, 0, 0);
			
	}

}
