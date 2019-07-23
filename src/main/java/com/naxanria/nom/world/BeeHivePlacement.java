package com.naxanria.nom.world;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

public class BeeHivePlacement extends SimplePlacement<NoPlacementConfig>
{
  public static final BeeHivePlacement INSTANCE = new BeeHivePlacement(dynamic -> NoPlacementConfig.NO_PLACEMENT_CONFIG);
  
  private BeeHivePlacement(Function<Dynamic<?>, ? extends NoPlacementConfig> dynamic)
  {
    super(dynamic);
  }
  
  @Override
  protected Stream<BlockPos> getPositions(Random random, NoPlacementConfig config, BlockPos blockPos)
  {
    return Stream.of(blockPos);
  }
  
  @Override
  protected <FC extends IFeatureConfig> boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, NoPlacementConfig config, ConfiguredFeature<FC> configuredFeature)
  {
    configuredFeature.place(world, generator, random, pos);
    
    return true;
  }
}
