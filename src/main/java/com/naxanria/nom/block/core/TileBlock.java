package com.naxanria.nom.block.core;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class TileBlock<T extends TileEntity> extends Block
{
  public interface TEProvider<T extends TileEntity>
  {
    T get(TileBlock<T> block, IBlockReader world, BlockState state);
  }
  
  protected final TEProvider<T> provider;
  
  public TileBlock(Properties properties, TEProvider<T> provider)
  {
    super(properties);
    this.provider = provider;
  }
  
  @Override
  public boolean hasTileEntity(BlockState state)
  {
    return true;
  }
  
  @Nullable
  @Override
  public T createTileEntity(BlockState state, IBlockReader world)
  {
    return provider.get(this, world, state);
  }
}
