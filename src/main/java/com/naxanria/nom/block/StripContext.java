package com.naxanria.nom.block;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StripContext
{
  public final PlayerEntity player;
  public final World world;
  public final BlockPos pos;
  public final ItemStack using;
  
  public StripContext(PlayerEntity player, World world, BlockPos pos, ItemStack using)
  {
    this.player = player;
    this.world = world;
    this.pos = pos;
    this.using = using;
  }
}
