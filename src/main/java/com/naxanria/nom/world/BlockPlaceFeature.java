package com.naxanria.nom.world;

import com.naxanria.nom.Nom;
import com.naxanria.nom.util.BlockStateMatch;
import com.naxanria.nom.util.WeightedRandomList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class BlockPlaceFeature extends Feature<NoFeatureConfig>
{
  private WeightedRandomList<BlockState> stateWeightedList;
  private float chance;
  private BlockStateMatch aboveCheck;
  private BlockStateMatch belowCheck;
  
  private BlockPlaceFeature(WeightedRandomList<BlockState> stateWeightedList, float chance, BlockStateMatch aboveCheck, BlockStateMatch belowCheck)
  {
    super(dynamic -> NoFeatureConfig.NO_FEATURE_CONFIG);
  
    this.stateWeightedList = stateWeightedList;
    this.chance = chance;
    this.aboveCheck = aboveCheck;
    this.belowCheck = belowCheck;
    
    if (aboveCheck == null && belowCheck == null)
    {
      Nom.LOGGER.warn("Need at least a block check above or below!");
      new Exception().printStackTrace();
    }
  }
  
  @Override
  public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config)
  {
    if (aboveCheck == null && belowCheck == null)
    {
      return false;
    }
    
    if (rand.nextFloat() <= chance)
    {
      BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos(pos);
      check.setY(worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, check.getX(), check.getZ()));
      
      while (check.getY() > 0)
      {
        BlockState state = worldIn.getBlockState(check);
  
        if (state.isAir(worldIn, check))
        {
          if (belowCheck != null)
          {
            BlockState below = worldIn.getBlockState(check.down());
            
            if (belowCheck.matches(below))
            {
              placeBlock(worldIn, check);
            }
          }
          else if (aboveCheck != null)
          {
            BlockState above = worldIn.getBlockState(check.up());
            
            if (aboveCheck.matches(above))
            {
              placeBlock(worldIn, check);
            }
          }
        }
        
        check.setY(check.getY() - 1);
      }
    }
    
    return false;
  }
  
  private void placeBlock(IWorld world, BlockPos pos)
  {
//    Nom.LOGGER.info("Placed block at [{} {} {}]", pos.getX(), pos.getY(), pos.getZ());
    setBlockState(world, pos, stateWeightedList.get());
  }
  
  public static Builder builder()
  {
    return new Builder();
  }
  
  public static class Builder
  {
    private WeightedRandomList<BlockState> stateWeightedList = new WeightedRandomList<>();
    private float chance = 0.25f;
    private BlockStateMatch aboveCheck;
    private BlockStateMatch belowCheck;
  
  
    public Builder addBlock(Block state, float weight)
    {
      return addBlock(state.getDefaultState(), weight);
    }
    
    public Builder addBlock(BlockState state, float weight)
    {
      stateWeightedList.add(state, weight);
      
      return this;
    }
    
    public Builder setStateWeightedList(WeightedRandomList<BlockState> stateWeightedList)
    {
      this.stateWeightedList = stateWeightedList;
      return this;
    }
  
    public Builder setChance(float chance)
    {
      this.chance = chance;
      return this;
    }
  
    public Builder setAboveCheck(BlockStateMatch aboveCheck)
    {
      this.aboveCheck = aboveCheck;
      return this;
    }
    
    public Builder addAboveCheck(Block block, Block... blocks)
    {
      for (Block b :
        blocks)
      {
        addAboveCheck(b.getDefaultState());
      }
      
      return addAboveCheck(block.getDefaultState());
    }
    
    public Builder addAboveCheck(BlockState state)
    {
      if (aboveCheck == null)
      {
        aboveCheck = new BlockStateMatch(true, state);
      }
      else
      {
        aboveCheck.addCompare(state);
      }
      
      return this;
    }
  
    public Builder setBelowCheck(BlockStateMatch belowCheck)
    {
      this.belowCheck = belowCheck;
      return this;
    }
  
    public Builder addBelowCheck(Block state, Block... blocks)
    {
      for (Block b :
        blocks)
      {
        addBelowCheck(b.getDefaultState());
      }
      
      return addBelowCheck(state.getDefaultState());
    }
    
    public Builder addBelowCheck(BlockState state)
    {
      if (belowCheck == null)
      {
        belowCheck = new BlockStateMatch(true, state);
      }
      else
      {
        belowCheck.addCompare(state);
      }
      
      return this;
    }
    
    public BlockPlaceFeature build()
    {
      return new BlockPlaceFeature(stateWeightedList, chance, aboveCheck, belowCheck);
    }
  }
}
