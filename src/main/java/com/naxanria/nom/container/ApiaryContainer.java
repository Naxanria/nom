package com.naxanria.nom.container;

import com.naxanria.nom.NomContainerTypes;
import com.naxanria.nom.tile.ApiaryTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.Optional;

public class ApiaryContainer extends TEContainer<ApiaryTile>
{
  @ObjectHolder("nom:apiary_container")
  public static final ContainerType<? extends ApiaryContainer> TYPE = null;
  
  public ApiaryContainer(int id, PlayerInventory inventory, PacketBuffer data)
  {
    super(TYPE, id, inventory, inventory.player.world, data.readBlockPos());
  }
  
  @Override
  public boolean canInteractWith(PlayerEntity playerIn)
  {
    return true;
  }
}
