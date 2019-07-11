package com.naxanria.nom.tile;

import com.naxanria.nom.Nom;
import com.naxanria.nom.NomItems;
import com.naxanria.nom.NomTileTypes;
import com.naxanria.nom.util.Cooldown;
import com.naxanria.nom.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ApiaryTile extends BaseTileEntityTicking
{
  protected Cooldown produceCooldown = new Cooldown(200);
  private int bees = 0;
  
  ItemStackHandler input = new ItemStackHandler(1)
  {
    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack)
    {
      return stack.getItem() == NomItems.BEE;
    }
  };
  
  ItemStackHandler output = new ItemStackHandler(1)
  {
    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack)
    {
      return false;
    }
  };
  
  public ApiaryTile()
  {
    super(NomTileTypes.APIARY);
    
    produceCooldown.setAction(this::produce).setAutoRestart(true);
  }
  
  @Override
  protected void tileUpdate()
  {
    bees = input.getStackInSlot(0).getCount();
    
    if (bees >= 2)
    {
      produceCooldown.setCooldown(200 - (Math.max(bees - 2, 0) * 7));
  
      produceCooldown.update();
    }
    else
    {
      produceCooldown.restart();
    }
  }
  
  private void produce()
  {
    if (world.isRemote)
    {
      return;
    }
    
    Nom.LOGGER.info("Produced");
  
    WorldUtil.spawnAsEntity(world, getPos(), new ItemStack(NomItems.HONEY, RAND.nextInt(2) + 1));
    if (bees < 16 && RAND.nextFloat() <= 0.3f)
    {
      input.getStackInSlot(0).grow(1);
      Nom.LOGGER.info("BEES! {}", bees);
    }
    
    markDirty();
  }
  
  @Override
  protected void writeData(CompoundNBT compound, SaveType type)
  {
    compound.put("cooldown", produceCooldown.writeToNBT(new CompoundNBT()));
    compound.put("input", input.serializeNBT());
    compound.put("output", output.serializeNBT());
    compound.putInt("bees", bees);
  }
  
  @Override
  protected void readData(CompoundNBT compound, SaveType type)
  {
    produceCooldown.readFromNBT(compound.getCompound("cooldown"));
    input.deserializeNBT(compound.getCompound("input"));
    output.deserializeNBT(compound.getCompound("output"));
    bees = compound.getInt("bees");
  }
  
  public int getBees()
  {
    return bees;
  }
  
  public ItemStackHandler getInput()
  {
    return input;
  }
  
  public ItemStackHandler getOutput()
  {
    return output;
  }
}
