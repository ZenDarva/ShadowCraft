package darva.shadowcraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import darva.shadowcraft.entities.ShadowWellEntity;

public class WellGui extends GuiContainer {
	
	
	public WellGui(Container par1Container) {
		super(par1Container);
		xSize = 187;
		ySize = 128;
	}

	public static final ResourceLocation texture = new ResourceLocation("shadowcraft", "textures/gui/shadowgui.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	public WellGui(InventoryPlayer invPlayer, ShadowWellEntity entity) {
		super(new WellContainer(invPlayer, entity));
		xSize = 187;
		ySize = 128;
		this.width = 187;
		this.height = 128;
	}


}
