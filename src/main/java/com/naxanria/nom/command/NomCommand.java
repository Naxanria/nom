package com.naxanria.nom.command;

import net.minecraft.command.CommandSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.function.Predicate;

public class NomCommand
{
  public static final Predicate<CommandSource> IS_OPERATOR = (cs) -> cs.hasPermissionLevel(2);
  
  public static void error(CommandSource source, String text)
  {
    source.sendErrorMessage(new StringTextComponent(text));
  }
  
  public static void feedBack(CommandSource source, String text)
  {
    source.sendFeedback(new StringTextComponent(text), true);
  }
  
  public static void error(CommandSource source, ITextComponent text)
  {
    source.sendErrorMessage(text);
  }
  
  public static void feedBack(CommandSource source, ITextComponent text)
  {
    source.sendFeedback(text, true);
  }
}
