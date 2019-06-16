package com.naxanria.nom.util;

import java.util.List;

public class ListUtil
{
  public static <T> List<T> addNoDuplicates(List<T> into, List<T> toAdd)
  {
    for (T t :
      toAdd)
    {
      if (!into.contains(t))
      {
        into.add(t);
      }
    }
    
    return into;
  }
}
