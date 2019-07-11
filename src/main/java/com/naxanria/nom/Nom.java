package com.naxanria.nom;

import com.naxanria.nom.command.NomCommands;
import com.naxanria.nom.world.NomWorldGen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;

@Mod("nom")
public class Nom
{
  public static final Logger LOGGER = LogManager.getLogger();
  public static final String MODID = "nom";
  public static final String NAME = "Nax's Organic Menu";
  
  public static Path configFolder;
  
  public Nom()
  {
    LOGGER.info("Loading " + NAME);
    
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    
    MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
  
    configFolder = FMLPaths.CONFIGDIR.get().resolve(MODID + "/");
    File configFolderFile = configFolder.toFile();
    if (!configFolderFile.exists())
    {
      if (configFolderFile.mkdir())
      {
        LOGGER.info("Created config folder");
      }
      else
      {
        LOGGER.warn("failed to create config folder");
      }
    }
  }
  
  private void serverStarting(final FMLServerStartingEvent event)
  {
    // todo: make this config sensitive
    new NomCommands(event.getCommandDispatcher(), true);
  }
  
  private void setup(final FMLCommonSetupEvent event)
  {
    LOGGER.info("Common setup");
    NomRegistry.registerFeatures();
    NomWorldGen.setup();
    NomRegistry.postInit();
  }
  
  private void setupClient(final FMLClientSetupEvent event)
  {
    LOGGER.info("Client setup");
    
    NomClientRegistry.registerScreens();
  }
  
  public static File getConfigSubFile(String fileName)
  {
    return new File(configFolder.toFile(), fileName);
  }
}
