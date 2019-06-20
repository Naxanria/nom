package com.naxanria.nom.block;

import com.naxanria.nom.block.core.TileBlock;
import com.naxanria.nom.tile.ApiaryTile;
import net.minecraft.block.BlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class ApiaryBlock extends TileBlock<ApiaryTile>
{
  public static final TEProvider<ApiaryTile> PROVIDER = (block, world, state) -> new ApiaryTile();
  
  public ApiaryBlock(Properties properties)
  {
    super(properties, PROVIDER);
  }
  
  @Override
  public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
  {
    if (worldIn.isRemote)
    {
      return true;
    }
    
    player.openContainer(getContainer(state, worldIn, pos));
    
    
    return true;
  }
  


}
