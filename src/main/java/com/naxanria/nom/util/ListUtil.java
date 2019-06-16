package com.naxanria.nom.util;

import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

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
  
//  public static <T> Optional<Integer> indexOf(List<T> list, T search, BiPredicate<T, T> check)
//  {
//    for (int i = 0; i < list.size(); i++)
//    {
//      if (check.test(list.get(i), search))
//      {
//        return Optional.of(i);
//      }
//    }
//
//    return Optional.empty();
//  }
  
  public static <T, S> Optional<Integer> indexOf(List<T> list, S search, BiPredicate<T, S> check)
  {
    for (int i = 0; i < list.size(); i++)
    {
      if (check.test(list.get(i), search))
      {
        return Optional.of(i);
      }
    }
    
    return Optional.empty();
  }
  
  public static <T> List<T> asList(NonNullList<T> listIn)
  {
    return new ArrayList<>(listIn);
  }
}
