package com.naxanria.nom.block.trees;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class CinnamonTree extends Tree
{
  @Nullable
  @Override
  protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random)
  {
    return new CinnamonTreeFeature(NoFeatureConfig::deserialize, true);
  }
}
