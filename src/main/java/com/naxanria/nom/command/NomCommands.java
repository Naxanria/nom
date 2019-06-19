package com.naxanria.nom.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class NomCommands
{
  public NomCommands(CommandDispatcher<CommandSource> dispatcher, boolean debug)
  {
    LiteralArgumentBuilder<CommandSource> builder = Commands.literal("nom")
      .then(CommandReload.register()
    );
  
    if (debug)
    {
      builder.then
      (
        Commands.literal("debug")
          .then(CommandFood.register())
          .then(CommandHunger.register())
      );
    }
    
    dispatcher.register(builder);
  }
}
