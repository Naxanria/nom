package com.naxanria.nom.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldUtil
{
  public static void spawnAsEntity(World world, BlockPos pos, ItemStack stack)
  {
    Block.spawnAsEntity(world, pos, stack);
  }
}
