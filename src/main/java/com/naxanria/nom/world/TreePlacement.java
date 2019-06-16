package com.naxanria.nom.world;

import com.naxanria.nom.util.BlockStateMatch;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class TreePlacement extends Placement<NoPlacementConfig>
{
  public static final BlockStateMatch SOIL = new BlockStateMatch(true, Blocks.DIRT, Blocks.GRASS_BLOCK);
  
  public static TreePlacement get(int tries)
  {
    return new TreePlacement(tries);
  }
  
  private int tries;
  private BlockStateMatch matcher = SOIL;
  
  private TreePlacement(int tries)
  {
    super(NoPlacementConfig::deserialize);
    this.tries = tries;
  }
  
  public BlockStateMatch getMatcher()
  {
    return matcher;
  }
  
  public TreePlacement setMatcher(BlockStateMatch matcher)
  {
    this.matcher = matcher;
    return this;
  }
  
  @Override
  public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, NoPlacementConfig config, BlockPos pos)
  {
    List<BlockPos> list = new ArrayList<>();
  
    for (int i = 0; i < tries; i++)
    {
      int x = random.nextInt(16);
      int z = random.nextInt(16);
      int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.getX() + x, pos.getZ() + z);
      
      BlockPos toAdd = null;//
      
      BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos();
      
      if (matcher != null)
      {
        for (; y > 1; y--)
        {
          BlockState found = world.getBlockState(check.setPos(pos.getX() + x, y, pos.getZ() + z).down());
          if (matcher.matches(found))
          {
             toAdd = new BlockPos(x + pos.getX(), y, z + pos.getZ());
          }
        }
      }
      else
      {
        toAdd = new BlockPos(x + pos.getX(), y, z + pos.getZ());;
      }
  
      if (toAdd != null && !list.contains(toAdd))
      {
        list.add(toAdd);
      }
    }
    
    return list.stream();
  }
}
