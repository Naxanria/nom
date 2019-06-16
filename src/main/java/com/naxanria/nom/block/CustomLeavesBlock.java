package com.naxanria.nom.block;

import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;

public class CustomLeavesBlock extends LeavesBlock
{
  public CustomLeavesBlock(Properties properties)
  {
    super(properties);
  }
  
  @Override
  public boolean isIn(Tag<Block> tagIn)
  {
    return tagIn.getId() == BlockTags.LEAVES.getId() || super.isIn(tagIn);
  }
}
