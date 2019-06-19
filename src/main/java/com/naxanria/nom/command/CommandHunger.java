package com.naxanria.nom.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class CommandHunger extends NomCommand
{
  static ArgumentBuilder<CommandSource, ?> register()
  {
    return Commands.literal("hunger")
      .requires(IS_OPERATOR)
      .executes(CommandHunger::hunger);
  }
  
  public static int hunger(CommandContext<CommandSource> context) throws CommandSyntaxException
  {
    ServerPlayerEntity player = context.getSource().asPlayer();
    player.getFoodStats().setFoodLevel(0);
    player.getFoodStats().setFoodSaturationLevel(0);
    
    return 1;
  }
}
