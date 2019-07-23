package com.naxanria.nom.client.gui;

import net.minecraft.client.gui.AbstractGui;

public class DrawHelper
{
  public static void progressBar(int x, int y, int width, int height, float progress, int color, int backgroundColor)
  {
    AbstractGui.fill(x, y, x + width, y + height, backgroundColor);
    int w = (int) (width * progress);
    AbstractGui.fill(x, y, x + w, y + height, color);
  }
}
