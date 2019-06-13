package com.naxanria.nom.world;

import com.mojang.datafixers.Dynamic;
import com.naxanria.nom.Nom;
import com.naxanria.nom.NomBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class BeeHiveFeature extends Feature<NoFeatureConfig>
{
  public static final BeeHiveFeature INSTANCE = new BeeHiveFeature(dynamic -> NoFeatureConfig.NO_FEATURE_CONFIG);
  
  private static final BlockState DIRT_STATE = Blocks.DIRT.getDefaultState();
  private static final BlockState GRASS_STATE = Blocks.GRASS_BLOCK.getDefaultState();
  private static final BlockState BEE_HIVE_STATE = NomBlocks.BEE_HIVE.getDefaultState();
  private static final BlockState OAK_LEAVES_STATE = Blocks.OAK_LEAVES.getDefaultState();
  private static final BlockState BIRCH_LEAVES_STATE = Blocks.BIRCH_LEAVES.getDefaultState();
  
  private BeeHiveFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49878_1_)
  {
    super(p_i49878_1_);
  }

  @Override
  public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config)
  {
    if (rand.nextFloat() < 0.15f)
    {
  
      BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos(pos);
      int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING, check.getX(), check.getZ());
  
//      Nom.LOGGER.info("Attempting to place a block at [{} {} {}]", check.getX(), y, check.getZ());
      
      for (; y > 0; y--)
      {
        check.setY(y);
        BlockState state = world.getBlockState(check);
        if (state.getBlock() == Blocks.OAK_LEAVES || state.getBlock() == Blocks.BIRCH_LEAVES)
        {
          BlockPos place = check.down();
          if (world.getBlockState(place).isAir(world, place))
          {
            setBlockState(world, place, BEE_HIVE_STATE);
            
//            Nom.LOGGER.info("Placed a block [{}] at [{} {} {}]", BEE_HIVE_STATE, place.getX(), place.getY(), place.getZ());
            return true;
          }
        }
      }
    }
    
    return false;
  }
}
