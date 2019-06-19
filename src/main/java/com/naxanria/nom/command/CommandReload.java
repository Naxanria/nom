package com.naxanria.nom.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.naxanria.nom.NomRegistry;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class CommandReload extends NomCommand
{
  static ArgumentBuilder<CommandSource, ?> register()
  {
    return Commands.literal("reload")
      .requires(IS_OPERATOR)
      .executes(ctx -> reload(ctx.getSource()));
  }
  
  private static int reload(CommandSource source)
  {
    feedBack(source, "reloading");
    long start = System.currentTimeMillis();
  
    NomRegistry.reloadFoods();
    
    long time = System.currentTimeMillis() - start;
    feedBack(source, "reloaded! in " + time + "ms");
    
    return 1;
  }
}
