package com.naxanria.nom.block;

import com.naxanria.nom.util.IntRange;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class HarvestableBlock extends Block
{
  protected ToolType toolType;
  protected int toolLevel;
  protected IntRange xp;
  
  public HarvestableBlock(Properties properties, ToolType toolType, int toolLevel)
  {
    this(properties, toolType, toolLevel, new IntRange(0, 0));
  }
  
  public HarvestableBlock(Properties properties, ToolType toolType, int toolLevel, int maxXp)
  {
    this(properties, toolType, toolLevel, new IntRange(0, maxXp));
  }
  
  public HarvestableBlock(Properties properties, ToolType toolType, int toolLevel, int minXp, int maxXP)
  {
    this(properties, toolType, toolLevel, new IntRange(minXp, maxXP));
  }
  
  public HarvestableBlock(Properties properties, ToolType toolType, int toolLevel, IntRange xp)
  {
    super(properties);
    
    this.toolType = toolType;
    this.toolLevel = toolLevel;
    this.xp = xp;
  }
  
  @Override
  public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch)
  {
    return silktouch == 0 ? xp.get(RANDOM) : 0;
  }
  
  @Nullable
  @Override
  public ToolType getHarvestTool(BlockState state)
  {
    return toolType;
  }
  
  @Override
  public int getHarvestLevel(BlockState state)
  {
    return toolLevel;
  }
  
  @Override
  public boolean isToolEffective(BlockState state, ToolType tool)
  {
    return tool == toolType;
  }
}
