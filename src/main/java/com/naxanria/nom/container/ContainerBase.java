package com.naxanria.nom.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;

import javax.annotation.Nullable;

public abstract class ContainerBase extends Container
{
  protected int inventoryStart = 0;
  
  protected ContainerBase(@Nullable ContainerType<?> p_i50105_1_, int p_i50105_2_)
  {
    super(p_i50105_1_, p_i50105_2_);
  }
  
  protected void createInventorySlots(PlayerInventory inventory, int x, int y)
  {
    inventoryStart = inventorySlots.size() - 1;
    
    for(int row = 0; row < 3; row++)
    {
      for(int column = 0; column < 9; ++column)
      {
        this.addSlot(new Slot(inventory,row * 9 + column + 9, x + column * 18, y + row * 18 ));
      }
    }
  
    for(int slot = 0; slot < 9; slot++)
    {
      this.addSlot(new Slot(inventory, slot, x + slot * 18, y + 3 * 18 + 4));
    }
  }
  
  
  
  @Override
  public boolean canInteractWith(PlayerEntity playerIn)
  {
    return true;
  }
}
