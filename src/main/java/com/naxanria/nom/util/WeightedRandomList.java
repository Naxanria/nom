package com.naxanria.nom.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedRandomList<T>
{
  private List<Entry<T>> entries = new ArrayList<>();
  private float total;
  private final Random random;
  
  public WeightedRandomList()
  {
    random = new Random();
  }
  
  public WeightedRandomList(Random random)
  {
    this.random = random;
  }
  
  public WeightedRandomList<T> add(T value, float weight)
  {
    Entry<T> entry = new Entry<T>(value, weight, total);
    total += weight;
    entries.add(entry);
    
    return this;
  }
  
  public T get()
  {
    float roll = random.nextFloat() * total;
    for (Entry<T> entry:
         entries)
    {
      roll -= entry.weight;
      if (roll <= 0)
      {
        return entry.value;
      }
    }
    
    return entries.get(random.nextInt(entries.size())).value;
    
//    float roll = random.nextFloat() * total;
//
//
//    int min = 0;
//    int max = entries.size();
//
//    Entry<T> current;
//
//    int tries = 0;
//
//    do
//    {
//      int index = (min + max) / 2;
//      current = entries.get(index);
//      if (roll >= current.total && roll <= current.total + current.weight)
//      {
//        break;
//      }
//
//      if (roll < current.total)
//      {
//        max = index;
//      }
//      else
//      {
//        min = index;
//      }
//    }
//    while (tries++ < entries.size());
//
//    return current.value;
  }
  
  private static class Entry<T>
  {
    T value;
    float weight;
    float total;
  
    public Entry(T value, float weight, float total)
    {
      this.value = value;
      this.weight = weight;
      this.total = total;
    }
  }
}
