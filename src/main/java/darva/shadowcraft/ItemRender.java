package darva.shadowcraft;

import darva.shadowcraft.entities.ShadowWellEntity;
import darva.shadowcraft.models.WellModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRender implements IItemRenderer {

	private final WellModel wellModel;
	private TileEntitySpecialRenderer render;
	
	public ItemRender(TileEntitySpecialRenderer renderer)
	{
		render=renderer;
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
			render.renderTileEntityAt(new ShadowWellEntity(), 0, 0, 0, 0);
	}

}
