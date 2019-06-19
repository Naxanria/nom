package com.naxanria.nom.util.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.naxanria.nom.NomItems;
import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.JSONUtils;
import org.apache.commons.lang3.tuple.Pair;

public class FoodSerializer implements IJsonSerializer<Food>
{
  @Override
  public JsonObject serialize(Food food)
  {
    JsonObject object = new JsonObject();
    
    object.addProperty("hunger", food.getHealing());
    object.addProperty("saturation", food.getSaturation());
    object.addProperty("fast_eating", food.isFastEating());
    object.addProperty("always_edible", food.canEatWhenFull());
    object.addProperty("meat", food.isMeat());
    
    JsonArray effects = new JsonArray();
  
    for (Pair<EffectInstance, Float> pair:
      food.getEffects())
    {
      JsonObject effect = new JsonObject();
      effect.add("effect", Serializers.EFFECT_SERIALIZER.serialize(pair.getLeft()));
      effect.addProperty("chance", pair.getRight());
      
      effects.add(effect);
    }
    
    object.add("effects", effects);
    
    return object;
  }
  
  @Override
  public Food deserialize(JsonObject object)
  {
    Food.Builder builder = new Food.Builder();
  
    builder.hunger(JSONUtils.getInt(object, "hunger", 1));
    builder.saturation(JSONUtils.getFloat(object, "saturation", 1));
    if (JSONUtils.getBoolean(object, "fast_eating", false))
    {
      builder.fastToEat();
    }
    
    if (JSONUtils.getBoolean(object, "always_edible", false))
    {
      builder.setAlwaysEdible();
    }
    
    if (JSONUtils.getBoolean(object, "meat", false))
    {
      builder.meat();
    }
    
    JsonArray effects = JSONUtils.getJsonArray(object, "effects", null);
    if (effects != null)
    {
      for (int i = 0; i < effects.size(); i++)
      {
        EffectInstance instance = Serializers.EFFECT_SERIALIZER.deserialize(effects.get(i).getAsJsonObject().getAsJsonObject("effect"));
        float chance = JSONUtils.getFloat(effects.get(i).getAsJsonObject(), "chance", 1.0f);
        
        builder.effect(instance, chance);
      }
    }
    
    return builder.build();
  }
}
