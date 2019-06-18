package com.naxanria.nom.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.Random;
import java.util.function.BiConsumer;

public class StrippableLogBlock extends CustomLogBlock
{
  private final Block stripped;
  private BiConsumer<StripContext, Random> stripCallback;
  
  public StrippableLogBlock(MaterialColor materialColor, Properties properties, Block stripped)
  {
    super(materialColor, properties);
    
    this.stripped = stripped;
  }
  
  public StrippableLogBlock setStripCallback(BiConsumer<StripContext, Random> stripCallback)
  {
    this.stripCallback = stripCallback;
    return this;
  }
  
  @Override
  public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult traceResult)
  {
    ItemStack usingStack = hand == Hand.MAIN_HAND ? playerEntity.getHeldItemMainhand() : playerEntity.getHeldItemOffhand();
    if (!usingStack.isEmpty())
    {
      if (usingStack.getItem().getToolTypes(usingStack).contains(ToolType.AXE))
      {
        world.playSound(playerEntity, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);
        
        if (!world.isRemote)
        {
          world.setBlockState(pos, stripped.getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)), 11);
          
          usingStack.damageItem(1, playerEntity, p -> {
            p.getHeldItem(hand);
          });
          
          if (stripCallback != null)
          {
            StripContext context = new StripContext(playerEntity, world, pos, playerEntity.getHeldItem(hand));
            
            stripCallback.accept(context, RANDOM);
          }
        }
      }
    }
    
    return super.onBlockActivated(state, world, pos, playerEntity, hand, traceResult);
  }

}
