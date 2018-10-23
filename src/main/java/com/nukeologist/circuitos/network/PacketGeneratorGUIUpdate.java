package com.nukeologist.circuitos.network;

import com.nukeologist.circuitos.block.tileblock.BasicGenerator.TileEntityBasicGenerator;
import com.nukeologist.circuitos.utility.LogHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGeneratorGUIUpdate implements IMessage {

    private int x,y,z;
    private boolean messageValid;
    private String mess;

    public PacketGeneratorGUIUpdate(){
        this.messageValid = false;
    }

    public PacketGeneratorGUIUpdate(int x, int y, int z, String mess ){
        this.x = x;
        this.y = y;
        this.z = z;
        this.mess = mess;
        this.messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        try {
            this.x = buf.readInt();
            this.y = buf.readInt();
            this.z = buf.readInt();

            this.mess = ByteBufUtils.readUTF8String(buf);

        }catch (Exception e) {
            LogHelper.logWarn(e.toString());
            return;
        }

        this.messageValid = true;

    }

    @Override
    public void toBytes(ByteBuf buf) {
        if (!this.messageValid) return;
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);


        ByteBufUtils.writeUTF8String(buf, this.mess);

    }

    //TODO: make this more hacked-client proof
    public static class Handler implements IMessageHandler<PacketGeneratorGUIUpdate, IMessage> {

        @Override
        public IMessage onMessage(PacketGeneratorGUIUpdate message, MessageContext ctx) {
            if(!message.messageValid && ctx.side == Side.SERVER) return null;
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
            return null;
        }

        void processMessage(PacketGeneratorGUIUpdate message, MessageContext ctx) {

            World world = ctx.getServerHandler().player.world;
            LogHelper.logInfo(message.mess);
            if(message.mess.equals("analyzing")) {
                TileEntity te = world.getTileEntity(new BlockPos(message.x, message.y, message.z));
                if(te instanceof TileEntityBasicGenerator) {
                    ((TileEntityBasicGenerator) te).analyzing = true;
                    world.notifyBlockUpdate(te.getPos(), world.getBlockState(te.getPos()), world.getBlockState(te.getPos()), 1 );
                    world.scheduleBlockUpdate(te.getPos(), te.getBlockType(), 0, 0);
                    te.markDirty();
                }
            }else if(message.mess.equals("!analyzing")){
                TileEntity te = world.getTileEntity(new BlockPos(message.x, message.y, message.z));
                if(te instanceof TileEntityBasicGenerator) {
                    ((TileEntityBasicGenerator) te).analyzing = false;
                    world.notifyBlockUpdate(te.getPos(), world.getBlockState(te.getPos()), world.getBlockState(te.getPos()), 1 );
                    world.scheduleBlockUpdate(te.getPos(), te.getBlockType(), 0, 0);
                    te.markDirty();
                }
            }
        }
    }
}
