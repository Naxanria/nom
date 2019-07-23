package com.naxanria.nom.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

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
    inventoryStart = inventorySlots.size();
    
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
  public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
  {
    ItemStack empty = ItemStack.EMPTY;
    
    int inventoryEnd = inventoryStart + 26;
    int hotbarStart = inventoryEnd + 1;
    int hotbarEnd = hotbarStart + 8;
    
    Slot slot = inventorySlots.get(index);
    
    if (slot != null && slot.getHasStack())
    {
      ItemStack newStack = slot.getStack();
      ItemStack currentStack = newStack.copy();
      
      if (index >= inventoryStart)
      {
        SlotHandleState state = handleSpecialSlots(playerIn, newStack);
        
        if (state == SlotHandleState.FAILURE)
        {
          return empty;
        }
        else if (state == SlotHandleState.SUCCESS)
        {
          slot.onSlotChanged();
          
          if (currentStack.getCount() == newStack.getCount())
          {
            return empty;
          }
          
          slot.onTake(playerIn, newStack);
          
          return currentStack;
        }
        else if (index <= inventoryEnd && !mergeItemStack(newStack, hotbarStart, hotbarEnd + 1, false))
        {
          return empty;
        }
        else if (index >= hotbarStart + 1 && index < hotbarEnd + 1 && !mergeItemStack(newStack, inventoryStart, inventoryEnd + 1, false))
        {
          return empty;
        }
      }
      else if (mergeItemStack(newStack, inventoryStart, hotbarEnd + 1, false))
      {
        return empty;
      }
      
      slot.onSlotChanged();
      
      if (currentStack.getCount() == newStack.getCount())
      {
        return empty;
      }
      
      slot.onTake(playerIn, newStack);
      
      return currentStack;
    }
    
    return empty;
  }
  
  public SlotHandleState handleSpecialSlots(PlayerEntity player, ItemStack stack)
  {
    for (int i = 0; i < inventoryStart; i++)
    {
      Slot insert = inventorySlots.get(i);
      if (insert.isItemValid(stack))
      {
        return mergeItemStack(stack, i, i + 1, false) ? SlotHandleState.SUCCESS : SlotHandleState.FAILURE;
      }
    }
    
    return SlotHandleState.INVALID;
  }
  
  @Override
  public boolean canInteractWith(PlayerEntity playerIn)
  {
    return true;
  }
}
