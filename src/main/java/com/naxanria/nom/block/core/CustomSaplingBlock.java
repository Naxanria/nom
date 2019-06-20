package com.naxanria.nom.block.core;

import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;

public class CustomSaplingBlock extends SaplingBlock
{
  protected CustomSaplingBlock(Tree p_i48337_1_, Properties properties)
  {
    super(p_i48337_1_, properties);
  }
  
  @Override
  public boolean isIn(Tag<Block> tagIn)
  {
    return tagIn.getId() == BlockTags.SAPLINGS.getId() || super.isIn(tagIn);
  }
}
