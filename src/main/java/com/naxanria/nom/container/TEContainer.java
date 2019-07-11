package com.naxanria.nom.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class TEContainer<TE extends TileEntity> extends Container
{
  protected TE tileEntity;
  
  protected TEContainer(@Nullable ContainerType<?> type, int id)
  {
    super(type, id);
  }
  
  public TEContainer(ContainerType<?> type, int id, PlayerInventory inventory, World world, BlockPos pos)
  {
    super(type, id);
    
    TileEntity entity = world.getTileEntity(pos);
    if (entity != null)
    {
      setTileEntity((TE) entity);
    }
  }
  
  public TEContainer<TE> setTileEntity(TE tileEntity)
  {
    this.tileEntity = tileEntity;
    
    return this;
  }
  
  public TE getTileEntity()
  {
    return tileEntity;
  }
  
  @Override
  public boolean canInteractWith(PlayerEntity playerIn)
  {
    return true;
  }
}
