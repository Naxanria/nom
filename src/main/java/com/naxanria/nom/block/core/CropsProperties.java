package com.naxanria.nom.block.core;

import com.naxanria.nom.util.IntRange;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class CropsProperties
{
  private static final Map<Integer, IntegerProperty> properties = new HashMap<>();
  
  private int maxAge;
  private Item seedsItem;
  private Supplier<Item> seedSupplier = null;
  private List<Block> validGround = new ArrayList<Block>(){{add(Blocks.FARMLAND);}};
  private int minLight = 8;
  private int maxLight = Integer.MAX_VALUE;
  private boolean canUseBonemeal = true;
  private IntRange boneMealGrowth = new IntRange(2, 5);
  
  public static CropsProperties create(int maxAge, Item seedsItem)
  {
    return new CropsProperties(maxAge, seedsItem);
  }
  
  public static CropsProperties create(int maxAge, Supplier<Item> seedSupplier)
  {
    CropsProperties cropsProperties = new CropsProperties(maxAge, null);
    cropsProperties.seedSupplier = seedSupplier;
    return cropsProperties;
  }
  
  protected CropsProperties(int maxAge, Item seedsItem)
  {
    this.maxAge = maxAge;
    this.seedsItem = seedsItem;
  }
  
  public int getMinLight()
  {
    return minLight;
  }
  
  public CropsProperties minLight(int minLight)
  {
    this.minLight = minLight;
    return this;
  }
  
  public int getMaxLight()
  {
    return maxLight;
  }
  
  public CropsProperties maxLight(int maxLight)
  {
    this.maxLight = maxLight;
    return this;
  }
  
  public boolean canUseBonemeal()
  {
    return canUseBonemeal;
  }
  
  public CropsProperties canUseBonemeal(boolean canUseBonemeal)
  {
    this.canUseBonemeal = canUseBonemeal;
    return this;
  }
  
  public CropsProperties setValidGround(Block... valid)
  {
    validGround.clear();
    validGround.addAll(Arrays.asList(valid));
    return this;
  }
  
  public List<Block> getValidGround()
  {
    return validGround;
  }
  
  public int getMaxAge()
  {
    return maxAge;
  }
  
  public Item getSeedsItem()
  {
    if (seedsItem == null)
    {
      return seedSupplier.get();
    }
    
    return seedsItem;
  }
  
  public IntRange getBoneMealGrowth()
  {
    return boneMealGrowth;
  }
  
  public CropsProperties setBoneMealGrowth(int min, int max)
  {
    setBoneMealGrowth(new IntRange(min, max));
    
    return this;
  }
  
  public CropsProperties setBoneMealGrowth(IntRange boneMealGrowth)
  {
    this.boneMealGrowth = boneMealGrowth;
    return this;
  }
  
  public IntegerProperty getAgeProperty()
  {
    switch (maxAge)
    {
      case 1:
        return BlockStateProperties.AGE_0_1;
        
      case 2:
        return BlockStateProperties.AGE_0_2;
        
      case 3:
        return BlockStateProperties.AGE_0_3;
        
      case 5:
        return BlockStateProperties.AGE_0_5;
        
      case 7:
        return BlockStateProperties.AGE_0_7;
        
      case 15:
        return BlockStateProperties.AGE_0_15;
        
      case 25:
        return BlockStateProperties.AGE_0_25;
    }
    
    if (properties.containsKey(maxAge))
    {
      return properties.get(maxAge);
    }
    
    IntegerProperty ageProperty = IntegerProperty.create("age", 0, maxAge);
    
    properties.put(maxAge, ageProperty);
    
    return ageProperty;
  }
  
  
}
