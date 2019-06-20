package com.naxanria.nom.block.core;

import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;

public class CustomLogBlock extends LogBlock
{
  public CustomLogBlock(MaterialColor p_i48367_1_, Properties p_i48367_2_)
  {
    super(p_i48367_1_, p_i48367_2_);
  }
  
  @Override
  public boolean isIn(Tag<Block> tagIn)
  {
    return (tagIn.getId().equals(BlockTags.LOGS.getId())) || super.isIn(tagIn);
  }
}
