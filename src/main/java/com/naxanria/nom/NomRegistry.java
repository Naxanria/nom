package com.naxanria.nom;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NomRegistry
{
  private static List<? extends BlockItem> blockItems = new ArrayList<>();
  
  private static IForgeRegistry<Item> itemRegistry;
  private static IForgeRegistry<Block> blockRegistry;
  
  private static ItemGroup itemGroup = new ItemGroup(Nom.MODID)
  {
    @Override
    public ItemStack createIcon()
    {
      return new ItemStack(Blocks.CAKE);
    }
  };
  
  private static Item registerItem(Item item, String name)
  {
    ResourceLocation location = new ResourceLocation(Nom.MODID, name);
    item.setRegistryName(location);
    itemRegistry.register(item);
    
    return item;
  }
  
  private static Item registerFood(String name, int food, float saturation)
  {
    return registerFood(name, food, saturation, null, 0);
  }
  
  private static Item registerFood(String name, int food, float saturation, EffectInstance effectInstance, float chance)
  {
    Food.Builder builder = new Food.Builder().func_221456_a(food).func_221454_a(saturation);
    if (effectInstance != null)
    {
      builder.func_221452_a(effectInstance, chance);
    }
    
    return registerItem(new Item(getProperties().func_221540_a( builder.func_221453_d())), name);
  }
  
  private static Block registerBlock(Block block, String name)
  {
    return registerBlock(block, name, true);
  }
  
  private static Block registerBlock(Block block, String name, boolean createItemBlock)
  {
    if (createItemBlock)
    {
      // todo: create itemBlock
    }
    
    return block;
  }
  
  @SubscribeEvent
  public static void onRegisterBlocks(final RegistryEvent.Register<Block> event)
  {
    blockRegistry = event.getRegistry();
  }
  
  @SubscribeEvent
  public static void onRegisterItems(final RegistryEvent.Register<Item> event)
  {
    itemRegistry = event.getRegistry();
  
    for (BlockItem blockItem :
      blockItems)
    {
      itemRegistry.register(blockItem);
    }
    
    registerFood("cooked_carrot", 5, 0.6f);
  }
  
  private static Item.Properties getProperties()
  {
    return new Item.Properties().group(itemGroup);
  }
}
