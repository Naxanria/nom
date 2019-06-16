package com.naxanria.nom.block.trees;

import com.mojang.datafixers.Dynamic;
import com.naxanria.nom.Nom;
import com.naxanria.nom.NomBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class CinnamonTreeFeature extends AbstractTreeFeature<NoFeatureConfig>
{
  private static final BlockState LEAVES = NomBlocks.CINNAMON_LEAVES.getDefaultState();
  private static final BlockState LOG = NomBlocks.CINNAMON_LOG.getDefaultState();
  
  private final int minHeight = 4;
  private final int maxHeight = 8;
  
  public CinnamonTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49920_1_, boolean p_i49920_2_)
  {
    super(p_i49920_1_, p_i49920_2_);
  }
  
  @Override
  protected boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox boundingBox)
  {
    int height = MathHelper.nextInt(rand, minHeight, maxHeight);
    
//    Nom.LOGGER.info("Attempting generating tree at [{} {} {}]", position.getX(), position.getY(), position.getZ());
    // check for under world height
    if (position.getY() > 0 && position.getY() + height + 1 <= worldIn.getMaxHeight())
    {
      BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
      
      for (int y = 0; y < height; y++)
      {
        // is it a valid block to replace? (Air, leaves, logs)
        if (!func_214587_a(worldIn, pos.setPos(position.getX(), position.getY() + y, position.getZ())))
        {
          return false;
        }
      }
  
      Nom.LOGGER.info("Generating tree at [{} {} {}]", position.getX(), position.getY(), position.getZ());
      
      if (isSoil(worldIn, position.down(), getSapling()))
      {
        setDirtAt(worldIn, position.down(), position);
  
        for (int y = 0; y < height ; y++)
        {
          setLogState(changedBlocks, worldIn, pos.setPos(position.getX(), position.getY() + y, position.getZ()), LOG, boundingBox);
          
          if (height - y == 1)
          {
            for (int x = -1; x <= 1; x++)
            {
              for (int z = -1; z <= 1; z++)
              {
                if (func_214587_a(worldIn, pos.setPos(position.getX() + x, position.getY() + y, position.getZ() + z)))
                {
                  setBlockState(worldIn, pos, LEAVES);
                }
              }
            }
  
            setLogState(changedBlocks, worldIn, pos.setPos(position.getX(), position.getY() + y, position.getZ()), LOG, boundingBox);
          }
          if (func_214587_a(worldIn, pos.setPos(position.getX(), position.getY() + height, position.getZ())))
          {
            setBlockState(worldIn, pos, LEAVES);
          }
          
        }
        
        return true;
      }
    }
    
    return false;
  }
}
