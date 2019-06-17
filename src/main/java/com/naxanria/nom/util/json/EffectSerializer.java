package com.naxanria.nom.util.json;

import com.google.gson.JsonObject;
import com.naxanria.nom.Nom;
import com.naxanria.nom.util.StringUtil;
import com.naxanria.nom.util.Time;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectSerializer implements IJsonSerializer<EffectInstance>
{
  private static final EffectInstance DEFAULT = new EffectInstance(Effects.GLOWING, 1);
  
  @Override
  public JsonObject serialize(EffectInstance effectInstance)
  {
    JsonObject object = new JsonObject();
    object.addProperty("id", getProperId(effectInstance.getEffectName()));
    object.addProperty("duration", effectInstance.getDuration());
    object.addProperty("strength", effectInstance.getAmplifier());
    object.addProperty("particles", effectInstance.doesShowParticles());
    object.addProperty("icon", effectInstance.isShowIcon());
    object.addProperty("ambient", effectInstance.isAmbient());
//    object.addProperty("max_duration", effectInstance.getIsPotionDurationMax());
    
    return object;
  }
  
  private String getProperId(String id)
  {
    if (id.contains("effect."))
    {
      id = id.substring(7);
    }
    
    id = id.replace('.', ':');
    
    return id;
  }
  
  @Override
  public EffectInstance deserialize(JsonObject object)
  {
    String id = JSONUtils.getString(object, "id", "");
    if (id.isEmpty())
    {
      Nom.LOGGER.info("[Effect]: Incorrect value for effect 'id' in {}", object.toString());
      
      return DEFAULT;
    }
  
    ResourceLocation location = new ResourceLocation(id);
    
    Effect effect = ForgeRegistries.POTIONS.containsKey(location) ? ForgeRegistries.POTIONS.getValue(location) : null;
    
    if (effect == null)
    {
      Nom.LOGGER.info("Could not find potion effect for {}", location);
      
      return DEFAULT;
    }
    
    int duration = JSONUtils.getInt(object, "duration", Time.Ticks.SECOND * 5);
    int strength = JSONUtils.getInt(object, "strength", 1);
    boolean showParticles = JSONUtils.getBoolean(object, "particles", false);
    boolean icon = JSONUtils.getBoolean(object, "icon", true);
    boolean ambient = JSONUtils.getBoolean(object, "ambient", false);
//    boolean maxDuration = JSONUtils.getBoolean(object, "max_duration", false);
    
    return new EffectInstance(effect, duration, strength, ambient, showParticles, icon);
  }
}
