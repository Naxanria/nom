package com.naxanria.nom.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class BlockStateMatch extends Match<BlockState>
{
  public static final BlockStateMatch AIR = new BlockStateMatch(true, Blocks.AIR, Blocks.CAVE_AIR, Blocks.VOID_AIR);
  
  private final boolean checkBlock;
  
  public BlockStateMatch(Block... toCompare)
  {
    this(false, toCompare);
  }
  
  public BlockStateMatch(BlockState... toCompare)
  {
    this(false, toCompare);
  }
  
  public BlockStateMatch(boolean checkBlock, Block... toCompare)
  {
    this.checkBlock = checkBlock;
  
    for (Block compare :
      toCompare)
    {
      compareList.add(compare.getDefaultState());
    }
  }
  
  public BlockStateMatch(boolean checkBlock, BlockState... toCompare)
  {
    super(toCompare);
    this.checkBlock = checkBlock;
  }
  
  @Override
  public boolean matches(BlockState toCheck)
  {
    for (BlockState state :
      compareList)
    {
      if (state == toCheck || state.equals(toCheck) || checkBlock && state.getBlock() == toCheck.getBlock())
      {
        return true;
      }
    }
    return false;
  }
}
