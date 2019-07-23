package com.naxanria.nom.container;

import com.naxanria.nom.tile.ApiaryTile;
import com.naxanria.nom.util.WorldPos;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ObjectHolder;

public class ApiaryContainer extends TEContainer<ApiaryTile>
{
  @ObjectHolder("nom:apiary_container")
  public static final ContainerType<? extends ApiaryContainer> TYPE = null;
  
  public ApiaryContainer(int id, PlayerInventory inventory, World world, BlockPos pos)
  {
    super(TYPE, id, inventory, world, pos);
  
    createInventorySlots(inventory, 8, 84);
    
    addSlot(new SlotItemHandler(tileEntity.getInput(), 0, 56, 36));
    addSlot(new SlotItemHandler(tileEntity.getOutput(), 0, 114, 36));
  }
  
  public ApiaryContainer(int id, PlayerInventory inventory, PacketBuffer data)
  {
//    super(TYPE, id, inventory, inventory.player.world, data.readBlockPos());
    this(id, inventory, inventory.player.world, data.readBlockPos());
  }
}
