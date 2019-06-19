package com.naxanria.nom.util;

import net.minecraft.nbt.CompoundNBT;

public interface ISerializable<T>
{
  CompoundNBT serialize();
  T deserialize(CompoundNBT tag);
}
