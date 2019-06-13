package com.naxanria.nom.block;

import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.common.ToolType;

public class BeeHiveBlock extends HarvestableBlock
{
  public BeeHiveBlock(Properties properties)
  {
    super(properties.hardnessAndResistance(1.0f).tickRandomly(), ToolType.AXE, 0, 2);
  }
  
  @Override
  public BlockRenderLayer getRenderLayer()
  {
    return BlockRenderLayer.CUTOUT;
  }
  
  // todo: add bee particles
}
