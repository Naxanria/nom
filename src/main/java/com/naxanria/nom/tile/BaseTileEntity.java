package com.naxanria.nom.tile;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.Random;

public abstract class BaseTileEntity extends TileEntity
{
  protected final Random RAND = new Random();
  
  public BaseTileEntity(TileEntityType<?> type)
  {
    super(type);
  }
  
  @Override
  public CompoundNBT write(CompoundNBT compound)
  {
    super.write(compound);
    writeData(compound, SaveType.SAVE);
    
    return compound;
  }
  
  @Override
  public void read(CompoundNBT compound)
  {
    super.read(compound);
    readData(compound, SaveType.SAVE);
  }
  
  @Override
  public CompoundNBT getUpdateTag()
  {
    CompoundNBT compound = super.getUpdateTag();
    writeData(compound, SaveType.SAVE);
    
    return super.getUpdateTag();
  }
  
  @Override
  public void handleUpdateTag(CompoundNBT tag)
  {
    readData(tag, SaveType.SAVE);
    onUpdatePacket(SaveType.SAVE);
//    super.handleUpdateTag(tag);
  }
  
  private void onUpdatePacket(SaveType type)
  {
    markDirty();
  }
  
  @Override
  public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
  {
    readData(pkt.getNbtCompound(), SaveType.UPDATE);
    onUpdatePacket(SaveType.UPDATE);
  }
  
  protected abstract void writeData(CompoundNBT compound, SaveType type);
  protected abstract void readData(CompoundNBT compound, SaveType type);
  
}
