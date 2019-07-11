package com.naxanria.nom.util;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

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
}
