package com.naxanria.nom.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.naxanria.nom.Nom;
import com.naxanria.nom.NomItems;
import com.naxanria.nom.util.ListUtil;
import com.naxanria.nom.util.RandomUtil;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GrinderRecipe implements ICraftingRecipe
{
  public static final Serializer SERIALIZER = new Serializer();
  
  private final ItemStack result;
  private final List<Ingredient> ingredients;
  private final ResourceLocation id;
  
  private String group = "";
  
  public GrinderRecipe(String id, ItemStack result, Ingredient ingredient, Ingredient... ingredients)
  {
    this(new ResourceLocation("nom", id), result, ingredient, ingredients);
  }
  
  public GrinderRecipe(ResourceLocation id, ItemStack result, Ingredient ingredient, Ingredient... ingredients)
  {
    this.result = result;
    this.id = id;
  
    if (ingredients.length >= 7)
    {
      throw new IllegalArgumentException("Needs at least 1 spot for the grinder.");
    }
    
    this.ingredients = Lists.asList(ingredient, ingredients);
  }
  
  public GrinderRecipe(ResourceLocation id, String group, ItemStack result, NonNullList<Ingredient> ingredients)
  {
    this.result = result;
    this.id = id;
    this.ingredients = new ArrayList<>(ingredients);
    this.group = group;
  }
  
  public GrinderRecipe setGroup(String group)
  {
    this.group = group;
    return this;
  }
  
  @Override
  public boolean matches(CraftingInventory inv, World worldIn)
  {
    int check = 0;
    boolean grinder = false;
  
    for (int slot = 0; slot < inv.getSizeInventory(); slot++)
    {
      ItemStack found = inv.getStackInSlot(slot);
      if (found.isEmpty())
      {
        continue;
      }
      
      if (found.getItem() == NomItems.GRINDER)
      {
        grinder = true;
      }
      else
      {
        Optional<Integer> index = ListUtil.indexOf
        (ingredients, found,
          (Ingredient ingredient, ItemStack f) ->
            Arrays.stream(ingredient.getMatchingStacks()).anyMatch
            (
              stack -> ItemStack.areItemsEqual(f, stack)
            )
        );
        
        if (!index.isPresent())
        {
          return false;
        }
        
        check |= 1 << index.get();
      }
    }
    
//    Nom.LOGGER.info("{} <> {} <> {}", grinder, check, (1 << (ingredients.size() - 1)));
    
    return check == (1 << (ingredients.size() - 1)) && grinder;
  }
  
  @Override
  public ItemStack getCraftingResult(CraftingInventory inv)
  {
    return result.copy();
  }
  
  @Override
  public ItemStack getRecipeOutput()
  {
    return result.copy();
  }
  
  @Override
  public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv)
  {
    NonNullList<ItemStack> list = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
    
    for (int slot = 0; slot < inv.getSizeInventory(); slot++)
    {
      ItemStack found = inv.getStackInSlot(slot);
      if (found.getItem() == NomItems.GRINDER)
      {
        int newDamage = found.getDamage() + 1;
        if (newDamage < found.getMaxDamage())
        {
          // FIXME: why do we need to add a new ItemStack here?
          ItemStack left = new ItemStack(NomItems.GRINDER);
          left.setDamage(newDamage);
          list.set(slot, left);
        }
        
        
//        int dmg = found.getDamage();
//        Nom.LOGGER.info("[1] dmg: {}/{}", dmg, found.getMaxDamage());
//        if (dmg < found.getMaxDamage())
//        {
//          found.setDamage(dmg + 1);
//          boolean dmgSuccess = found.attemptDamageItem(1, RandomUtil.random, null);
//
//          list.set(slot, found);
//          Nom.LOGGER.info("[2] dmg: {}/{} success: {}", dmg, found.getMaxDamage(), dmgSuccess);
//        }
      }
    }
    
    return list;
  }
  
  @Override
  public ResourceLocation getId()
  {
    return id;
  }
  
  @Override
  public IRecipeSerializer<?> getSerializer()
  {
    return SERIALIZER;
  }
//
//  @Override
//  public NonNullList<Ingredient> getIngredients()
//  {
//    return ingredients;
//  }
  
  public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<GrinderRecipe>
  {
    public Serializer()
    {
      setRegistryName("nom", "crafting_grinder");
    }
  
    @Override
    public GrinderRecipe read(ResourceLocation recipeId, JsonObject json)
    {
      String group = JSONUtils.getString(json, "group", "");
      NonNullList<Ingredient> ingredients = NonNullList.create();
  
      JsonArray array = json.getAsJsonArray("ingredients");
  
      for (int i = 0; i < array.size(); i++)
      {
        Ingredient ingredient = Ingredient.deserialize(array.get(i));
        
        if (!ingredient.hasNoMatchingItems())
        {
          ingredients.add(ingredient);
        }
      }
      
      if (ingredients.isEmpty())
      {
        throw new JsonParseException("No ingredients found for grinder recipe");
      }
      else if (ingredients.size() >= 8)
      {
        throw new JsonParseException("To many ingredients, maximum is 8.");
      }
      else
      {
        ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
        
        return new GrinderRecipe(recipeId, group, result, ingredients);
      }
    }
  
    @Override
    public GrinderRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
    {
      String group = buffer.readString(Short.MAX_VALUE);
      int ingSize = buffer.readVarInt();
      NonNullList<Ingredient> ingredients = NonNullList.withSize(ingSize, Ingredient.EMPTY);
  
      for (int i = 0; i < ingSize; i++)
      {
        ingredients.set(i, Ingredient.read(buffer));
      }
      
      ItemStack result = buffer.readItemStack();
      
      return new GrinderRecipe(recipeId, group, result, ingredients);
    }
  
    @Override
    public void write(PacketBuffer buffer, GrinderRecipe recipe)
    {
      buffer.writeString(recipe.group);
      buffer.writeVarInt(recipe.ingredients.size());
  
      for (Ingredient ing :
        recipe.ingredients)
      {
        ing.write(buffer);
      }
      
      buffer.writeItemStack(recipe.result);
    }
  }
}
