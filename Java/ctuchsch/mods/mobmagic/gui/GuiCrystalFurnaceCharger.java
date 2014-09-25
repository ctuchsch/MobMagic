package ctuchsch.mods.mobmagic.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Segment;

import org.lwjgl.opengl.GL11;

import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.containers.ContainerCrystalFurnaceCharger;
import ctuchsch.mods.mobmagic.containers.ContainerToolCharger;
import ctuchsch.mods.mobmagic.messages.MessageInfuserGuiButton;
import ctuchsch.mods.mobmagic.tileentities.TileCrystalFurnaceCharger;
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

public class GuiCrystalFurnaceCharger extends GuiContainer {
	public static final ResourceLocation texture = new ResourceLocation(MobMagic.MODID,
			"textures/gui/CrystalFurnaceChargerGUI.png");
	private static final int batteryXOffset = 82;
	private static final int batteryYOffsetMax = 51; // bottom
	private static final int batteryYOffsetMin = 33; // bottom
	private static final int batteryTextureOffsetX = 176;
	private static final int batteryTextureOffsetY = 67;
	private static final int batWidth = 11;
	private static final int batHeight = 19;
	
	TileCrystalFurnaceCharger charger;

	private float mouseX;
	private float mouseY;	

	public GuiCrystalFurnaceCharger(InventoryPlayer playerInventory, TileCrystalFurnaceCharger charger) {
		super(new ContainerCrystalFurnaceCharger(playerInventory, charger));
		this.charger = charger;

		this.xSize = 176;
		this.ySize = 166;
	}	

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);		
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(charger.isBurning()) {
			int scaledGui = charger.getGuiPosScaled(batHeight);
			drawTexturedModalRect(guiLeft + batteryXOffset, guiTop + batteryYOffsetMax - scaledGui ,batteryTextureOffsetX,batteryTextureOffsetY + (batHeight - scaledGui),batWidth,batHeight - (batHeight - scaledGui));
		}
	}
	public void drawScreen(int par1, int par2, float par3) {
		this.mouseX = (float) par1;
		this.mouseY = (float) par2;
		super.drawScreen(par1, par2, par3);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int p1, int p2) {	
	}
}
