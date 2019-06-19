package com.naxanria.nom.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.naxanria.nom.util.TextComponentBuilder;
import com.naxanria.nom.util.json.EffectSerializer;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class CommandFood extends NomCommand
{
  static ArgumentBuilder<CommandSource, ?> register()
  {
    return Commands.literal("food")
      .requires(IS_OPERATOR)
      .executes(ctx -> food(ctx.getSource()));
  }
  
  private static int food(CommandSource source) throws CommandSyntaxException
  {
    ServerPlayerEntity player = source.asPlayer();
    ItemStack held = player.getHeldItemMainhand();
    if (held.isEmpty())
    {
      error(source, "Hand is empty");
      return 0;
    }
  
    Item item = held.getItem();
    Food food = item.getFood();
    
    if (food == null)
    {
      feedBack(source, "This is not a food item.");
      return 1;
    }

    List<ITextComponent> info = new ArrayList<>();
    
    info.add
    (
      TextComponentBuilder.create("Hunger:", true).darkGreen().next(food.getHealing()).aqua()
      .next("Saturation:").darkGreen().next(food.getSaturation()).aqua()
      .build()
    );
    
    info.add
    (
      TextComponentBuilder.create("AlwaysEdible:", true).darkGreen().next(food.canEatWhenFull())
      .next("FastEating:").darkGreen().next(food.isFastEating())
      .next("Meat:").darkGreen().next(food.isMeat())
      .build()
    );
  
    List<Pair<EffectInstance, Float>> foodEffects = food.getEffects();
    if (foodEffects.size() > 0)
    {
      info.add(TextComponentBuilder.create("Effects: ").darkGreen().build());
      for (Pair<EffectInstance, Float> pair:
         foodEffects)
      {
        EffectInstance instance = pair.getLeft();
        float chance = pair.getRight();
        info.add
        (
          TextComponentBuilder.create("    Id:", true).darkGreen().next(EffectSerializer.getProperId(instance.getEffectName())).aqua()
          .next("Strength:").darkGreen().next(instance.getAmplifier()).aqua()
          .next("Duration:").darkGreen().next(instance.getDuration()).aqua()
          .next("Chance:").darkGreen().next(chance).aqua()
          .build()
        );
      }
    }
  
    info.forEach((s) -> feedBack(source, s));
    
    return 1;
  }

}
