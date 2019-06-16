package com.naxanria.nom.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldPos
{
  public final World world;
  public final BlockPos pos;
  
  public WorldPos(World world, BlockPos pos)
  {
    this.world = world;
    this.pos = pos;
  }
}
