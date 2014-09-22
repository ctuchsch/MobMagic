package ctuchsch.mods.mobmagic.messages;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ctuchsch.mods.mobmagic.tileentities.TileToolCharger;

public class MessageInfuserGuiButton implements IMessage {

	private int tileX;
	private int tileY;
	private int tileZ;
	private int id;
	
	public MessageInfuserGuiButton() {}
	
	public MessageInfuserGuiButton(int id, int x, int y, int z) {
		this.tileX = x;
		this.tileY = y;
		this.tileZ = z;
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.tileX = buf.getInt(0);
		this.tileY = buf.getInt(4);
		this.tileZ = buf.getInt(8);
		this.id = buf.getInt(12);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(tileX);
		buf.writeInt(tileY);
		buf.writeInt(tileZ);
		buf.writeInt(id);
	}
	
	public static class Handler implements IMessageHandler<MessageInfuserGuiButton, IMessage> {

		@Override
		public IMessage onMessage(MessageInfuserGuiButton message, MessageContext ctx) {
			TileEntity tile = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.tileX, message.tileY, message.tileZ);
			if(tile != null && tile instanceof TileToolCharger) {
				TileToolCharger charger = (TileToolCharger)tile;
				
				if(message.id < 100){					
					charger.processSlot(message.id);
				}
				else
					charger.startProcessing();
			}
			return null;
		}
	}
}
