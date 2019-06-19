package com.naxanria.nom.tile;

import com.naxanria.nom.Nom;
import com.naxanria.nom.NomItems;
import com.naxanria.nom.NomTileTypes;
import com.naxanria.nom.util.Cooldown;
import com.naxanria.nom.util.WorldUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ApiaryTile extends BaseTileEntityTicking
{
  protected Cooldown produceCooldown = new Cooldown(200);
  private int bees = 0;
  
  public ApiaryTile()
  {
    super(NomTileTypes.APIARY);
    
    produceCooldown.setAction(this::produce).setAutoRestart(true);
  }
  
  @Override
  protected void tileUpdate()
  {
    produceCooldown.setCooldown(200 - (Math.max(bees - 2, 0) * 7));
    
    produceCooldown.update();
//    Nom.LOGGER.info("Apiary tick");
    
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
      bees++;
      Nom.LOGGER.info("BEES! {}", bees);
    }
    
    markDirty();
  }
  
  @Override
  protected void writeData(CompoundNBT compound, SaveType type)
  {
    compound.put("cooldown", produceCooldown.writeToNBT(new CompoundNBT()));
    compound.putInt("bees", bees);
  }
  
  @Override
  protected void readData(CompoundNBT compound, SaveType type)
  {
    produceCooldown.readFromNBT(compound.getCompound("cooldown"));
    bees = compound.getInt("bees");
  }
}
