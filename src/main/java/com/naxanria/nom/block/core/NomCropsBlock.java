package com.naxanria.nom.block.core;

import com.naxanria.nom.Nom;
import net.minecraft.block.*;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class NomCropsBlock extends Block implements IPlantable, IGrowable
{
  protected final CropsProperties cropsProperties;
  protected final IntegerProperty AGE_PROPERTY;
  
  public NomCropsBlock(Properties properties, CropsProperties cropsProperties)
  {
    super(properties);
    
    this.cropsProperties = cropsProperties;
    AGE_PROPERTY = cropsProperties.getAgeProperty();
    
    setDefaultState(stateContainer.getBaseState().with(AGE_PROPERTY, 0));
  }
  
//  @Override
  public IntegerProperty getAgeProperty()
  {
    return AGE_PROPERTY;
  }
  
//  @Override
  public int getMaxAge()
  {
    return cropsProperties.getMaxAge();
  }
  
  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
  {
    Nom.LOGGER.info("Filling state container for {}", getRegistryName());
    
    IntegerProperty property = getAgeProperty();
    if (property == null)
    {
      if (cropsProperties == null)
      {
        property = BlockStateProperties.AGE_0_3;
      }
      else
      {
        property = cropsProperties.getAgeProperty();
      }
    }
    
    builder.add(property);
  }
  
//  @Override
  protected int getBonemealAgeIncrease(World worldIn)
  {
    return cropsProperties.getBoneMealGrowth().get(worldIn.rand);
  }
  
  protected boolean hasProperLight(IWorldReader world, BlockPos pos)
  {
    int light = world.getLight(pos);
    return light <= cropsProperties.getMaxLight() && light >= cropsProperties.getMinLight();
  }
  
//  @Override
  protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos)
  {
    Block block = state.getBlock();
    return cropsProperties.getValidGround().contains(block);
  }
  
  @Override
  public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
  {
    return hasProperLight(worldIn, pos) && super.isValidPosition(state, worldIn, pos);
  }
  
//  @Override
  protected IItemProvider getSeedsItem()
  {
    return cropsProperties.getSeedsItem();
  }
  
  @Override
  public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient)
  {
    return !isMaxAge(state);
  }
  
  private boolean isMaxAge(BlockState state)
  {
    return getAge(state) >= getMaxAge();
  }
  
  @Override
  public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state)
  {
    return cropsProperties.canUseBonemeal();
  }
  
  @Override
  public void grow(World worldIn, Random rand, BlockPos pos, BlockState state)
  {
    int next = getAge(state) + getBonemealAgeIncrease(worldIn);
    if (next > getMaxAge())
    {
      next = getMaxAge();
    }
    
    worldIn.setBlockState(pos, withAge(next), 2);
  }
  
  private BlockState withAge(int age)
  {
    return getDefaultState().with(getAgeProperty(), age);
  }
  
  private int getAge(BlockState state)
  {
    return state.get(getAgeProperty());
  }
  
  @Override
  public PlantType getPlantType(IBlockReader world, BlockPos pos)
  {
    return PlantType.Crop;
  }
  
  @Override
  public BlockState getPlant(IBlockReader world, BlockPos pos) {
    BlockState state = world.getBlockState(pos);
    if (state.getBlock() != this) return getDefaultState();
    return state;
  }
  
  @Override
  public BlockRenderLayer getRenderLayer()
  {
    return BlockRenderLayer.CUTOUT;
  }
  
  @Override
  public void tick(BlockState state, World worldIn, BlockPos pos, Random random)
  {
    super.tick(state, worldIn, pos, random);
    
    if (!worldIn.isAreaLoaded(pos, 1))
    {
      return;
    }
    
    if (hasProperLight(worldIn, pos))
    {
      int age = getAge(state);
      if (age < getMaxAge())
      {
        // todo: more clever grow chance?
        float growChance = 0.03f;
        if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int)(25.0F / growChance) + 1) == 0))
        {
          worldIn.setBlockState(pos, withAge(age + 1), 2);
          ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
      }
    }
  }
}
