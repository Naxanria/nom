package com.naxanria.nom.world;

import com.naxanria.nom.util.BiomeList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NomWorldGen
{
  private static Map<Biome, List<FeatureData>> featureMap = new HashMap<>();
  
  public static FeatureData<NoFeatureConfig, NoPlacementConfig> create(GenerationStage.Decoration decorationStage, Feature<NoFeatureConfig> feature, Placement<NoPlacementConfig> placement, BiomeList biomes)
  {
    return create(decorationStage, feature, NoFeatureConfig.NO_FEATURE_CONFIG, placement, NoPlacementConfig.NO_PLACEMENT_CONFIG, biomes);
  }
  
  public static FeatureData<NoFeatureConfig, NoPlacementConfig> create(GenerationStage.Decoration decorationStage, Feature<NoFeatureConfig> feature, Placement<NoPlacementConfig> placement, Biome... biomes)
  {
    return create(decorationStage, feature, NoFeatureConfig.NO_FEATURE_CONFIG, placement, NoPlacementConfig.NO_PLACEMENT_CONFIG, biomes);
  }
  
  public static <F extends IFeatureConfig, P extends IPlacementConfig> FeatureData<F, P> create(GenerationStage.Decoration decorationStage, Feature<F> feature, F featureConfig, Placement<P> placement, P placementConfig, Biome... biomes)
  {
    return new FeatureData<>(decorationStage, feature, featureConfig, placement, placementConfig, biomes);
  }
  
  public static <F extends IFeatureConfig, P extends IPlacementConfig> FeatureData<F, P> create(GenerationStage.Decoration decorationStage, Feature<F> feature, F featureConfig, Placement<P> placement, P placementConfig, BiomeList biomes)
  {
    return new FeatureData<>(decorationStage, feature, featureConfig, placement, placementConfig, biomes);
  }
  
  public static class FeatureData<F extends IFeatureConfig, P extends IPlacementConfig>
  {
    GenerationStage.Decoration decorationStage;
    ConfiguredFeature<?> configuredFeature;
  
    FeatureData(GenerationStage.Decoration decorationStage, Feature<F> feature, F featureConfig, Placement<P> placement, P placementConfig, Biome... biomes)
    {
      this(decorationStage, feature, featureConfig, placement, placementConfig, BiomeList.asList(biomes));
    }
    
    FeatureData(GenerationStage.Decoration decorationStage, Feature<F> feature, F featureConfig, Placement<P> placement, P placementConfig, BiomeList biomes)
    {
      this.decorationStage = decorationStage;
      configuredFeature = Biome.createDecoratedFeature(feature, featureConfig, placement, placementConfig);
  
      for (Biome biome :
        biomes)
      {
        featureMap.computeIfAbsent(biome, (b) -> new ArrayList<>());
        List<FeatureData> data = featureMap.get(biome);
        
        data.add(this);
      }
    }
  }
  
  public static void setup()
  {
    DeferredWorkQueue.runLater(NomWorldGen::addFeatures);
  }
  
  private static void addFeatures()
  {
    for (Biome biome :
      ForgeRegistries.BIOMES)
    {
      if (featureMap.containsKey(biome))
      {
        List<FeatureData> featureData = featureMap.get(biome);
        for (FeatureData data :
          featureData)
        {
          addFeatureToBiome(biome, data);
        }
        
      }
    }
  }
  
  private static void addFeatureToBiome(Biome biome, FeatureData<?, ?> feature)
  {
    biome.addFeature(feature.decorationStage, feature.configuredFeature);
  }
}
