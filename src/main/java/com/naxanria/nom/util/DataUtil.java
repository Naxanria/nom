package com.naxanria.nom.util;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

public class DataUtil
{
  public static PacketBuffer unPooled()
  {
    return new PacketBuffer(Unpooled.buffer());
  }
  
  public static PacketBuffer pos(BlockPos pos)
  {
    return unPooled().writeBlockPos(pos);
  }
//
//  public static WorldPos getWorldPos(PacketBuffer buffer)
//  {
//    int worldId = buffer.readInt();
//    BlockPos pos = buffer.readBlockPos();
//
//
//
//    return new WorldPos()
//  }
}
