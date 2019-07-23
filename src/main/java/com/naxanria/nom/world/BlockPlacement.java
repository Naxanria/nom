package com.naxanria.nom.world;

import com.naxanria.nom.util.BlockStateMatch;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BlockPlacement extends SimplePlacement<NoPlacementConfig>
{
  private int frequency;
  private BlockStateMatch floor;
  
  public BlockPlacement(int frequency)
  {
    super(dynamic -> NoPlacementConfig.NO_PLACEMENT_CONFIG);
    
    this.frequency = frequency;
  }
  
  //
//  @Override
//  protected <FC extends IFeatureConfig> boolean place(IWorld p_214998_1_, ChunkGenerator<? extends GenerationSettings> p_214998_2_, Random p_214998_3_, BlockPos p_214998_4_, NoPlacementConfig p_214998_5_, ConfiguredFeature<FC> p_214998_6_)
//  {
//    return super.place(p_214998_1_, p_214998_2_, p_214998_3_, p_214998_4_, p_214998_5_, p_214998_6_);
//  }
  
  @Override
  protected Stream<BlockPos> getPositions(Random random, NoPlacementConfig config, BlockPos pos)
  {
    return IntStream.range(0, frequency).mapToObj(i ->
    {
      int x = random.nextInt(16);
      int z = random.nextInt(16);
      
      return pos.add(x, 0, z);
    });
  }
}
