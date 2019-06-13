package com.naxanria.nom;

import com.naxanria.nom.world.NomWorldGen;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("nom")
public class Nom
{
  public static final Logger LOGGER = LogManager.getLogger();
  public static final String MODID = "nom";
  public static final String NAME = "Nax's Organic Menu";
  
  public Nom()
  {
    LOGGER.info("Loading " + NAME);
    
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    
//    MinecraftForge.EVENT_BUS.register(this);
  }
  
  private void setup(final FMLCommonSetupEvent event)
  {
    LOGGER.info("Common setup");
    NomRegistry.registerFeatures();
    NomWorldGen.setup();
  }
  
  private void setupClient(final FMLClientSetupEvent event)
  {
    LOGGER.info("Client setup");
  }
}
