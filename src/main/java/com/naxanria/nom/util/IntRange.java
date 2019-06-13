package com.naxanria.nom.util;

import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class IntRange
{
  public final int min, max;
  
  public IntRange(int min, int max)
  {
    this.min = min;
    this.max = max;
  }
  
  public boolean inRange(int i)
  {
    return i >= min && i <= max;
  }
  
  public int get(Random random)
  {
    return MathHelper.nextInt(random, min, max);
  }
}
