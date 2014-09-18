package ctuchsch.mods.mobmagic.gui;

import org.lwjgl.opengl.GL11;

import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.containers.ContainerItemWand;
import ctuchsch.mods.mobmagic.items.ItemInventoryWand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiItemInventoryWand extends GuiContainer {

	public static final ResourceLocation texture = new ResourceLocation(MobMagic.MODID, "textures/gui/wandBasicGUI.png");

	private final ItemInventoryWand inventory;

	public GuiItemInventoryWand(ContainerItemWand container) {
		super(container);
		this.inventory = container.inventory;

		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int p1, int p2) {
		String name = "Mob Wand";
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString("Inventory", 8, this.ySize - 94, 4210752);
	}


	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1F, 1F, 1F, 1F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

	}

}
