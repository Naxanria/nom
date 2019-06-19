package com.naxanria.nom.util;

import net.minecraft.nbt.CompoundNBT;

public interface INBTUpdate
{
  CompoundNBT writeToNBT(CompoundNBT compound);
  void readFromNBT(CompoundNBT tag);
}
