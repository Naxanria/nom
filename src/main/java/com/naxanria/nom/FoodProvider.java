package com.naxanria.nom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.naxanria.nom.util.json.Serializers;
import net.minecraft.item.Food;
import net.minecraft.util.JSONUtils;

import java.util.HashMap;
import java.util.Map;

public class FoodProvider
{
  private static Map<String, Food> foodMap = new HashMap<>();
  
  public static Food add(String key, Food food)
  {
    foodMap.put(key, food);
    
    return food;
  }
  
  public static void readFromJson(JsonObject object)
  {
    if (object == null)
    {
      return;
    }
    
    if (!object.has("foods"))
    {
      return;
    }
    
    JsonArray foods = object.getAsJsonArray("foods");
    for (int i = 0; i < foods.size(); i++)
    {
      if (!foods.get(i).isJsonObject())
      {
        Nom.LOGGER.error("Incorrect format for the foods info. It needs to be an object");
        return;
      }
      
      JsonObject obj = foods.get(i).getAsJsonObject();
      String id = JSONUtils.getString(obj, "id", "");
      if (id.isEmpty())
      {
        Nom.LOGGER.error("Cant have an empty 'id'");
        return;
      }
      
      Food food = Serializers.FOOD_SERIALIZER.deserialize(obj.getAsJsonObject("food"));
      
      add(id, food);
    }
  }
  
  public static JsonObject writeToJson()
  {
    JsonObject object = new JsonObject();
    
    JsonArray foods = new JsonArray();
  
    for (String id :
      foodMap.keySet())
    {
      Food food = getFood(id);
      JsonObject foodObject = new JsonObject();
      foodObject.addProperty("id", id);
      foodObject.add("food", Serializers.FOOD_SERIALIZER.serialize(food));
      
      foods.add(foodObject);
    }
    
    object.add("foods", foods);
    
    return object;
  }
  
  public static Food getFood(String id)
  {
    if (!foodMap.containsKey(id))
    {
      Nom.LOGGER.warn("Could not find food entry for {}", id);
      return new Food.Builder().build();
    }
    
    return foodMap.get(id);
  }
  
  public static void clear()
  {
    foodMap.clear();
  }
}
