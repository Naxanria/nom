package com.naxanria.nom;

import com.naxanria.nom.block.BeeHiveBlock;
import com.naxanria.nom.util.Time;
import com.naxanria.nom.world.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NomRegistry
{
  private static List<BlockItem> blockItems = new ArrayList<>();
  
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
  
  private static <T extends Item> T registerItem(String name, T item)
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
    
    return registerItem(name, new Item(getProperties().func_221540_a(builder.func_221453_d())));
  }
  
  private static <T extends Block> T registerBlock(String name, T block)
  {
    return registerBlock(name, block, true);
  }
  
  private static <T extends Block> T registerBlock(String name, T block,  boolean createItemBlock)
  {
    block.setRegistryName(new ResourceLocation(Nom.MODID, name));
    
    if (createItemBlock)
    {
      blockItems.add((BlockItem) new BlockItem(block, getProperties()).setRegistryName(new ResourceLocation(Nom.MODID, name)));
    }
    
    blockRegistry.register(block);
    
    return block;
  }
  
  @SubscribeEvent
  public static void onRegisterBlocks(final RegistryEvent.Register<Block> event)
  {
    blockRegistry = event.getRegistry();
    
    registerBlock("bee_hive", new BeeHiveBlock(Block.Properties.create(NomMaterials.BEE_HIVE)));
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
    
    registerFood("honey_glazed_carrot", 12, 1.2f, getEffect(Effects.field_76439_r, Time.Ticks.MINUTE * 3, 1), 1f); // night vision
    registerItem("honey_comb", new Item(getProperties()));
    registerFood("honey", 1, 0.1f);
  }
  
  private static Item.Properties getProperties()
  {
    return new Item.Properties().group(itemGroup);
  }
  
  private static EffectInstance getEffect(Effect effect, int duration, int strength)
  {
    //                                                     ambient          showParticles     showIcon
    return new EffectInstance(effect, duration, strength, true, false, true);
  }
  
  public static void registerFeatures()
  {
    NomWorldGen.create(GenerationStage.Decoration.VEGETAL_DECORATION, BeeHiveFeature.INSTANCE, BeeHivePlacement.INSTANCE, Biomes.PLAINS, Biomes.BIRCH_FOREST, Biomes.FOREST, Biomes.FLOWER_FOREST);
  }
}
