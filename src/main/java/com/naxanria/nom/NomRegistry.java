package com.naxanria.nom;

import com.naxanria.nom.block.BeeHiveBlock;
import com.naxanria.nom.block.CustomLeavesBlock;
import com.naxanria.nom.block.CustomLogBlock;
import com.naxanria.nom.block.StrippableLogBlock;
import com.naxanria.nom.block.trees.CinnamonSapling;
import com.naxanria.nom.block.trees.CinnamonTreeFeature;
import com.naxanria.nom.recipe.GrinderRecipe;
import com.naxanria.nom.util.BiomeList;
import com.naxanria.nom.util.StringUtil;
import com.naxanria.nom.util.Time;
import com.naxanria.nom.util.WorldUtil;
import com.naxanria.nom.util.json.JsonProvider;
import com.naxanria.nom.world.BlockPlaceFeature;
import com.naxanria.nom.world.BlockPlacement;
import com.naxanria.nom.world.NomWorldGen;
import com.naxanria.nom.world.TreePlacement;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.File;
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
  
  private static Item registerFood(String name)
  {
    Food food = FoodProvider.getFood(name);
    return registerItem(name, new Item(getItemProperties().food(food)));
  }
  
  private static Item registerFood(String name, int food, float saturation)
  {
    return registerFood(name, food, saturation, null, 0);
  }
  
  private static Item registerFood(String name, int food, float saturation, EffectInstance effectInstance, float chance)
  {
    Food.Builder builder = new Food.Builder().hunger(food).saturation(saturation);
    if (effectInstance != null)
    {
      builder.effect(effectInstance, chance);
    }
    
    return registerItem(name, new Item(getItemProperties().food(builder.build())));
  }
  
  private static <T extends Block> T registerBlock(String name, T block)
  {
    return registerBlock(name, block, true);
  }

  private static <T extends Block> T registerBlock(String name, T block, boolean createItemBlock)
  {
    block.setRegistryName(new ResourceLocation(Nom.MODID, name));
    
    if (createItemBlock)
    {
      blockItems.add((BlockItem) new BlockItem(block, getItemProperties()).setRegistryName(new ResourceLocation(Nom.MODID, name)));
    }
    
    blockRegistry.register(block);
    
    return block;
  }
  
  @SubscribeEvent
  public static void onRegisterBlocks(final RegistryEvent.Register<Block> event)
  {
    Nom.LOGGER.info("Block registration");
    blockRegistry = event.getRegistry();
    
    registerBlock("bee_hive", new BeeHiveBlock(Block.Properties.create(NomMaterials.BEE_HIVE)));
    
    Block stripped = registerBlock("stripped_cinnamon_log", new CustomLogBlock(MaterialColor.WOOD, Block.Properties.create(Material.WOOD).hardnessAndResistance(2f).sound(SoundType.WOOD)));
    registerBlock("cinnamon_log", new StrippableLogBlock(MaterialColor.WOOD, getBlockProperties(Material.WOOD).hardnessAndResistance(2f).sound(SoundType.WOOD), stripped));
    registerBlock("cinnamon_leaves", new CustomLeavesBlock(getBlockProperties(Material.LEAVES).hardnessAndResistance(0.2f).tickRandomly().sound(SoundType.PLANT)));
    registerBlock("cinnamon_sapling", new CinnamonSapling(getBlockProperties(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0).sound(SoundType.PLANT)));
  }
  
  @SubscribeEvent
  public static void onRegisterItems(final RegistryEvent.Register<Item> event)
  {
    Nom.LOGGER.info("Item registration");
    itemRegistry = event.getRegistry();
  
    for (BlockItem blockItem :
      blockItems)
    {
      itemRegistry.register(blockItem);
    }
    
    FoodProvider.add("test", new Food.Builder().hunger(10).build());
    FoodProvider.add("test2", new Food.Builder().hunger(10).effect(getEffect(Effects.NAUSEA, 20, 1), 1).build());
    
    FoodProvider.add("cooked_carrot", new Food.Builder().hunger(5).saturation(0.6f).build());
    FoodProvider.add("honey_glazed_carrot", new Food.Builder().hunger(12).saturation(1.2f).effect(getEffect(Effects.NIGHT_VISION, Time.Ticks.MINUTE * 3, 1), 1).build());
    
    JsonProvider.writeToDisk(Nom.getConfigSubFile("default_foods.json"), FoodProvider.writeToJson());
    
    File foodsFile = Nom.getConfigSubFile("foods.json");
    if (!foodsFile.exists())
    {
      JsonProvider.writeToDisk(foodsFile, FoodProvider.writeToJson());
    }
    else
    {
      FoodProvider.readFromJson(JsonProvider.readFromDisk(Nom.getConfigSubFile("foods.json")));
    }
    
//    registerFood("cooked_carrot", 5, 0.6f);
    
//    registerFood("honey_glazed_carrot", 12, 1.2f, getEffect(Effects.NIGHT_VISION, Time.Ticks.MINUTE * 3, 1), 1f); // night vision
    
    registerFood("cooked_carrot");
    registerFood("honey_glazed_carrot");
    
    registerItem("honey_comb", new Item(getItemProperties()));
    registerFood("honey", 1, 0.1f);
    
    registerFood("cinnamon", 1, 0.1f, getEffect(Effects.LEVITATION, Time.Ticks.SECOND * 2, 10), 0.3f);
    
    registerItem("flour", new Item(getItemProperties()));
    
    registerFood("dough", 1, 0.2f, getEffect(Effects.NAUSEA, Time.Ticks.SECOND * 2, 1), 0.33f);
    registerFood("bun", 6, 2);
    registerFood("cinnamon_bun", 8, 8, getEffect(Effects.ABSORPTION, Time.Ticks.SECOND * 10, 1), 1);
    
    // **** Tools ****//
    
    registerItem("grinder", new Item(getItemProperties().maxDamage(120)));
  }
  
  @SubscribeEvent
  public static void registerRecipes(final RegistryEvent.Register<IRecipeSerializer<?>> event)
  {
    event.getRegistry().register(GrinderRecipe.SERIALIZER);
//    ForgeRegistries.RECIPE_SERIALIZERS.register(GrinderRecipe.SERIALIZER);
  }
  
  private static Item.Properties getItemProperties()
  {
    return new Item.Properties().group(itemGroup);
  }
  
  private static Block.Properties getBlockProperties(Material material)
  {
    return Block.Properties.create(material);
  }
  
  private static EffectInstance getEffect(Effect effect, int duration, int strength)
  {
    //                                                     ambient          showParticles     showIcon
    return new EffectInstance(effect, duration, strength, true, false, true);
  }
  
  public static void registerFeatures()
  {
    NomWorldGen.create
    (
      GenerationStage.Decoration.VEGETAL_DECORATION,
      BlockPlaceFeature.builder().addAboveCheck(Blocks.OAK_LEAVES, Blocks.BIRCH_LEAVES).addBlock(NomBlocks.BEE_HIVE, 1).build(),
      new BlockPlacement(1),
      BiomeList.BIRCH.copy().addAll(Biomes.FOREST, Biomes.PLAINS)
    );
    
    NomWorldGen.create
    (
      GenerationStage.Decoration.VEGETAL_DECORATION,
      new CinnamonTreeFeature(NoFeatureConfig::deserialize, true),
      TreePlacement.get(1),
      BiomeList.JUNGLE
    );
  }
  
  public static void postInit()
  {
    ((StrippableLogBlock) NomBlocks.CINNAMON_LOG).setStripCallback((wp, rand) ->
    {
      int roll = MathHelper.nextInt(rand, 0, 3);
      for (int i = 0; i < roll; i++)
      {
        ItemStack cinnamon = new ItemStack(NomItems.CINNAMON, 1);
        WorldUtil.spawnAsEntity(wp.world, wp.pos, cinnamon);
      }
    });
  }
}
