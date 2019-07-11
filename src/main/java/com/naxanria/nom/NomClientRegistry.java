package com.naxanria.nom;

import com.naxanria.nom.client.gui.ApiaryScreen;
import com.naxanria.nom.container.ApiaryContainer;
import net.minecraft.client.gui.ScreenManager;


public class NomClientRegistry
{
  public static void registerScreens()
  {
    ScreenManager.registerFactory(ApiaryContainer.TYPE, ApiaryScreen::new);
  }
}
