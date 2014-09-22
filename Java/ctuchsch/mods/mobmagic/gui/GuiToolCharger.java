package ctuchsch.mods.mobmagic.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.containers.ContainerToolCharger;
import ctuchsch.mods.mobmagic.tileentities.TileEssenceTank;
import ctuchsch.mods.mobmagic.tileentities.TileToolCharger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

public class GuiToolCharger extends GuiContainer {
	public static final ResourceLocation texture = new ResourceLocation(MobMagic.MODID, "textures/gui/toolChargerGUI.png");
	TileToolCharger charger;

	private float mouseX;
	private float mouseY;

	public GuiToolCharger(InventoryPlayer playerInventory, TileToolCharger charger) {
		super(new ContainerToolCharger(playerInventory, charger));
		this.charger = charger;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void actionPerformed(GuiButton b) {
		charger.processSlot(b.id);
	}

	@Override
	public void initGui() {
		super.initGui();

		int linkedTanks = charger.getNumActiveLinks();
		for (int i = 0; i < linkedTanks; i++) {
			if (i < 3)
				buttonList.add(new GuiButton(i, (guiLeft + 9) + (i * 23), guiTop + 63, 15, 10, ""));
			else
				buttonList.add(new GuiButton(i, (guiLeft + 37) + (i * 23), guiTop + 63, 15, 10, ""));
		}

		//buttonList.add(new GuiButton(1, guiLeft + 10, guiTop + 60, 25, 20, ">>"));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int linkedTanks = charger.getNumActiveLinks();
		for (int i = 0; i < linkedTanks; i++) {
			if (i < 3)
				drawTexturedModalRect((guiLeft + 8) + (i * 23), guiTop + 12, 176, 0, 16, 50);
			else
				drawTexturedModalRect((guiLeft + 36) + (i * 23), guiTop + 12, 176, 0, 16, 50);
		}

		drawFluid(linkedTanks);

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		for (int i = 0; i < linkedTanks; i++) {
			if (i < 3)
				drawTexturedModalRect((guiLeft + 9) + (i * 23), guiTop + 12, 176, 52, 11, 50);
			else
				drawTexturedModalRect((guiLeft + 37) + (i * 23), guiTop + 12, 176, 52, 11, 50);
		}
	}

	private void drawFluid(int TankCount) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		int offsetTop = guiTop + 13;
		int offsetBottom = guiTop + 12 + 50;
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		for (int i = 0; i < TankCount; i++) {
			TileEssenceTank tank = charger.getTank(i);
			if (tank != null) {
				double liquidHeight = charger.getScaledFluidAmount(i, 50);
				if (liquidHeight > 0) {
					FluidStack fluid = tank.tank.getFluid();
					if (fluid.getFluid().getIcon() != null) {
						IIcon texture = fluid.getFluid().getIcon();
						double minU = texture.getInterpolatedU(0);
						double maxU = texture.getInterpolatedU(16);
						double minV = texture.getInterpolatedV(0);
						double maxV = texture.getInterpolatedV(16);

						int offsetSide;

						if (i < 3) {
							offsetSide = guiLeft + 9 + (i * 23);
							t.addVertexWithUV(offsetSide, offsetBottom - liquidHeight, zLevel, maxU, maxV);
							t.addVertexWithUV(offsetSide, offsetBottom, zLevel, maxU, minV);
							t.addVertexWithUV(offsetSide + 14, offsetBottom, zLevel, minU, minV);
							t.addVertexWithUV(offsetSide + 14, offsetBottom - liquidHeight, zLevel, minU, maxV);
						} else {
							offsetSide = guiLeft + 37 + (i * 23);
							t.addVertexWithUV(offsetSide, offsetBottom - liquidHeight, zLevel, maxU, maxV);
							t.addVertexWithUV(offsetSide, offsetBottom, zLevel, maxU, minV);
							t.addVertexWithUV(offsetSide + 14, offsetBottom, zLevel, minU, minV);
							t.addVertexWithUV(offsetSide + 14, offsetBottom - liquidHeight, zLevel, minU, maxV);
						}

					}
				}
			}
		}
		t.draw();
	}

	public void drawScreen(int par1, int par2, float par3) {
		this.mouseX = (float) par1;
		this.mouseY = (float) par2;
		super.drawScreen(par1, par2, par3);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int p1, int p2) {
		int boxX = guiLeft;
		int boxY = guiTop;
		int sizeX = 15;
		int sizeY = 50;

		int k = (this.width - this.xSize) / 2; // X asis on GUI
		int l = (this.height - this.ySize) / 2; // Y asis on GUI

		int linkedTanks = charger.getNumActiveLinks();
		for (int i = 0; i < linkedTanks; i++) {
			if (i < 3) {
				// drawTexturedModalRect((guiLeft + 8) + (i * 23), guiTop + 12,
				// 176, 0, 16, 50);
				boxX = (guiLeft + 8) + (i * 23);
				boxY = (guiTop + 12);

			} else {
				// drawTexturedModalRect((guiLeft + 36) + (i * 23), guiTop + 12,
				// 176, 0, 16, 50);

				boxX = (guiLeft + 36) + (i * 23);
				boxY = (guiTop + 12);
			}

			if (mouseX > boxX && mouseX < boxX + sizeX) {
				if (mouseY > boxY && mouseY < boxY + sizeY) {
					FluidStack fluid = charger.getFluid(i);
					if (fluid != null) {
						List list = new ArrayList();
						list.add(fluid.getFluid().getLocalizedName(fluid) + ": " + fluid.amount + "mb");
						this.drawHoveringText(list, (int) mouseX - k, (int) mouseY - l, fontRendererObj);
					}
				}
			}

		}

	}

}
