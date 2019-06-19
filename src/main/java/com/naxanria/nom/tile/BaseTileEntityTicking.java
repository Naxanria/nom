package com.naxanria.nom.tile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class BaseTileEntityTicking extends BaseTileEntity implements ITickableTileEntity
{
  private int ticks = 0;
  
  public BaseTileEntityTicking(TileEntityType<?> type)
  {
    super(type);
  }
  
  @Override
  public final void tick()
  {
    ticks++;
    
    tileUpdate();
  }
  
  protected abstract void tileUpdate();
  
  @Override
  public CompoundNBT write(CompoundNBT compound)
  {
    compound.putInt("ticks", ticks);
    
    return super.write(compound);
  }
  
  @Override
  public void read(CompoundNBT compound)
  {
    ticks = compound.getInt("ticks");
    
    super.read(compound);
  }
}
