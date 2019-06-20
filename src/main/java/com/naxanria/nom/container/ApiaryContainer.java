package com.naxanria.nom.container;

import com.naxanria.nom.NomContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IWorldPosCallable;

import javax.annotation.Nullable;

public class ApiaryContainer extends Container
{
  
  public ApiaryContainer(int id, PlayerInventory inventory)
  {
    this(id, inventory, IWorldPosCallable.DUMMY);
  }
  
  public ApiaryContainer(int id, PlayerInventory inventory, IWorldPosCallable callable)
  {
    super(NomContainerTypes.APIARY_CONTAINER, id);
  }
//
//  public ApiaryContainer(@Nullable ContainerType<?> containerType, int winID)
//  {
//    super(containerType, winID);
//  }
  
  @Override
  public boolean canInteractWith(PlayerEntity playerIn)
  {
    return true;
  }
}
