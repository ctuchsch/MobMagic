package ctuchsch.mods.mobmagic.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.containers.ContainerToolCharger;
import ctuchsch.mods.mobmagic.messages.MessageInfuserGuiButton;
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
	public static final ResourceLocation texture = new ResourceLocation(MobMagic.MODID,
			"textures/gui/infusionCrafterGUI.png");
	TileToolCharger charger;

	private float mouseX;
	private float mouseY;
	private int over2Offset = 82;
	private int under2Offset = 8;

	public GuiToolCharger(InventoryPlayer playerInventory, TileToolCharger charger) {
		super(new ContainerToolCharger(playerInventory, charger));
		this.charger = charger;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void actionPerformed(GuiButton b) {
		if (b.id < 100)
			charger.processSlot(b.id);
		if (b.id >= 100)
			charger.startProcessing();

		MessageInfuserGuiButton message = new MessageInfuserGuiButton(b.id, charger.xCoord, charger.yCoord, charger.zCoord);
		MobMagic.infuserButtonChannel.sendToServer(message);
	}

	@Override
	public void initGui() {
		super.initGui();
		List<Integer> linkedTanks = charger.getActiveLinks();
		for (int i = 0; i < linkedTanks.size(); i++) {
			if (i < 2)
				buttonList.add(new GuiButton(linkedTanks.get(i), (guiLeft + under2Offset + 1) + (i * 23), guiTop + 63, 15, 10, ""));
			else
				buttonList.add(new GuiButton(linkedTanks.get(i), (guiLeft + over2Offset + 1) + (i * 23), guiTop + 63, 15, 10, ""));
		}
		buttonList.add(new GuiButton(100, guiLeft + 81, guiTop + 70, 15, 10, ""));

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		List<Integer> linkedTanks = charger.getActiveLinks();
		for (int i = 0; i < linkedTanks.size(); i++) {
			if (i < 2)
				drawTexturedModalRect((guiLeft + under2Offset) + (i * 23), guiTop + 12, 176, 0, 16, 50);
			else
				drawTexturedModalRect((guiLeft + over2Offset) + (i * 23), guiTop + 12, 176, 0, 16, 50);
		}

		drawFluid(linkedTanks);

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		for (int i = 0; i < linkedTanks.size(); i++) {
			if (i < 2)
				drawTexturedModalRect((guiLeft + under2Offset + 1) + (i * 23), guiTop + 12, 176, 52, 11, 50);
			else
				drawTexturedModalRect((guiLeft + over2Offset + 1) + (i * 23), guiTop + 12, 176, 52, 11, 50);
		}

		if (charger.processingTicks > 0) {
			int bubbleSegments = 28;
			int arrowSegments = 26;

			int bubbleHeight = (charger.processingTicks % charger.bubbleSpeed) * bubbleSegments / charger.bubbleSpeed;
			int arrowHeight = charger.processingTicks * arrowSegments / charger.ProcessingSpeed;
			// draw bubbles
			drawTexturedModalRect(guiLeft + 65, guiTop + 43, 192, 0, 13, bubbleHeight);

			// draw arrow
			drawTexturedModalRect(guiLeft + 101, guiTop + 43, 205, 0, 8, arrowHeight);
		}

		drawCraftingFluidSquares();
	}

	private void drawCraftingFluidSquares() {
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		int j = 0;
		// System.out.println(charger.getNumCraftingSlotsFilled());
		for (int i = 0; j < charger.getNumCraftingSlotsFilled() && i < charger.fluidSlots.length; i++) {
			TileEssenceTank tank = charger.getCraftingSlotTank(i);
			if (tank != null) {
				FluidStack fluid = tank.tank.getFluid();
				if (fluid != null) {

					if (fluid.getFluid().getIcon() != null) {
						IIcon texture = fluid.getFluid().getIcon();
						double minU = texture.getInterpolatedU(0);
						double maxU = texture.getInterpolatedU(16);
						double minV = texture.getInterpolatedV(0);
						double maxV = texture.getInterpolatedV(16);

						int minX = 0;
						int maxX = 0;
						int maxY = 0;
						int minY = 0;

						switch (j) {
							case 0:
								minX = guiLeft + 59;
								minY = guiTop + 15;
								break;
							case 1:
								minX = guiLeft + 80;
								minY = guiTop + 9;
								break;
							case 2:
								minX = guiLeft + 101;
								minY = guiTop + 15;
								break;
						}

						maxX = minX + 16;
						maxY = minY + 16;

						t.addVertexWithUV(minX, minY, zLevel, maxU, maxV);
						t.addVertexWithUV(minX, maxY, zLevel, maxU, minV);
						t.addVertexWithUV(maxX, maxY, zLevel, minU, minV);
						t.addVertexWithUV(maxX, minY, zLevel, minU, maxV);

						j++;
					}
				}
			}
		}
		t.draw();
	}

	private void drawFluid(List<Integer> Tanks) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		int offsetTop = guiTop + 13;
		int offsetBottom = guiTop + 12 + 50;
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		for (int i = 0; i < Tanks.size(); i++) {
			TileEssenceTank tank = charger.getTank(Tanks.get(i));
			if (tank != null) {
				double liquidHeight = charger.getScaledFluidAmount(Tanks.get(i), 50);
				if (liquidHeight > 0) {
					FluidStack fluid = tank.tank.getFluid();
					if (fluid.getFluid().getIcon() != null) {
						IIcon texture = fluid.getFluid().getIcon();
						double minU = texture.getInterpolatedU(0);
						double maxU = texture.getInterpolatedU(16);
						double minV = texture.getInterpolatedV(0);
						double maxV = texture.getInterpolatedV(16);

						int offsetSide;

						if (i < 2) {
							offsetSide = guiLeft + under2Offset + 1 + (i * 23);
							t.addVertexWithUV(offsetSide, offsetBottom - liquidHeight, zLevel, maxU, maxV);
							t.addVertexWithUV(offsetSide, offsetBottom, zLevel, maxU, minV);
							t.addVertexWithUV(offsetSide + 14, offsetBottom, zLevel, minU, minV);
							t.addVertexWithUV(offsetSide + 14, offsetBottom - liquidHeight, zLevel, minU, maxV);
						} else {
							offsetSide = guiLeft + over2Offset + 1 + (i * 23);
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

		List<Integer> linkedTanks = charger.getActiveLinks();
		for (int i = 0; i < linkedTanks.size(); i++) {
			if (i < 2) {
				// drawTexturedModalRect((guiLeft + 8) + (i * 23), guiTop + 12,
				// 176, 0, 16, 50);
				boxX = (guiLeft + under2Offset) + (i * 23);
				boxY = (guiTop + 12);

			} else {
				// drawTexturedModalRect((guiLeft + 36) + (i * 23), guiTop + 12,
				// 176, 0, 16, 50);

				boxX = (guiLeft + over2Offset) + (i * 23);
				boxY = (guiTop + 12);
			}

			if (mouseX > boxX && mouseX < boxX + sizeX) {
				if (mouseY > boxY && mouseY < boxY + sizeY) {
					FluidStack fluid = charger.getFluid(linkedTanks.get(i));
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
