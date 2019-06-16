package com.naxanria.nom.util;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

import java.util.ArrayList;

public class BiomeList extends ArrayList<Biome>
{
  public static final BiomeList OCEAN = asList(Biomes.OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_WARM_OCEAN, Biomes.FROZEN_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN);
  public static final BiomeList BIRCH = asList(Biomes.BIRCH_FOREST_HILLS, Biomes.BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.TALL_BIRCH_FOREST);
  public static final BiomeList FOREST = asList(Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS);
  public static final BiomeList JUNGLE = asList(Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE);
  
  public static BiomeList asList(Biome... biomes)
  {
    return new BiomeList().addAll(biomes);
  }
  
  public static BiomeList combine(BiomeList a, BiomeList b)
  {
    BiomeList list = new BiomeList();
    list.addAll(a);
    ListUtil.addNoDuplicates(list, b);
    
    return list;
  }
  
  public BiomeList addAll(Biome... biomes)
  {
    for (Biome biome :
      biomes)
    {
      add(biome);
    };
    
    return this;
  }
  
  public BiomeList combine(BiomeList other)
  {
    ListUtil.addNoDuplicates(this, other);
    
    return this;
  }
  
  public BiomeList addBiome(Biome biome)
  {
    add(biome);
    
    return this;
  }
  
  public BiomeList copy()
  {
    return new BiomeList(){{addAll(this);}};
  }
}
