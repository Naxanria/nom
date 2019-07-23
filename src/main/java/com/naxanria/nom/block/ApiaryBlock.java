package com.naxanria.nom.block;

import com.naxanria.nom.Nom;
import com.naxanria.nom.block.core.TileBlock;
import com.naxanria.nom.container.ApiaryContainer;
import com.naxanria.nom.tile.ApiaryTile;
import com.naxanria.nom.util.DataUtil;
import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ApiaryBlock extends TileBlock<ApiaryTile>
{
  public static final TEProvider<ApiaryTile> PROVIDER = (block, world, state) -> new ApiaryTile();
  private static Provider provider;
  
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
    
    if (player.isSneaking())
    {
      return false;
    }
  
    NetworkHooks.openGui(
      (ServerPlayerEntity) player,
      getContainer(state, worldIn, pos),
      data -> data.writeBlockPos(pos)
    );
//    player.openContainer(getContainer(state, worldIn, pos));
    
    return true;
  }
  
  @Nullable
  @Override
  public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos)
  {
    
    if (provider == null)
    {
      provider = new Provider(state, worldIn, pos);
    }
    else
    {
      provider.pos = pos;
      provider.world = worldIn;
      provider.state = state;
    }
    
    return provider;
  }
  
  private static class Provider implements INamedContainerProvider
  {
    private BlockState state;
    private World world;
    private BlockPos pos;
  
    public Provider(BlockState state, World world, BlockPos pos)
    {
      this.state = state;
      this.world = world;
      this.pos = pos;
    }
  
    @Override
    public ITextComponent getDisplayName()
    {
      return new TranslationTextComponent("nom:tile.gui.apiary");
    }
  
    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player)
    {
//      ApiaryContainer container = ApiaryContainer.TYPE.create(windowId, inventory, DataUtil.pos(pos));
//      container.setTileEntity((ApiaryTile) world.getTileEntity(pos));
//
//      if (container.getTileEntity() == null)
//      {
//        Nom.LOGGER.error("Tile is null!");
//
//        return null;
//      }
//
//      return container;
      
      return  new ApiaryContainer(windowId, inventory, player.world, pos);
    }
  }
}
