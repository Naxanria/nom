package com.naxanria.nom.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Match<T>
{
  protected List<T> compareList = new ArrayList<>();
  
  public Match(T... toCompare)
  {
    compareList.addAll(Arrays.asList(toCompare));
  }
  
  public Match<T> addCompare(T compare)
  {
    compareList.add(compare);
    
    return this;
  }
  
  public abstract boolean matches(T toCheck);
  
  public boolean isEmpty()
  {
    return compareList.isEmpty();
  }
}
