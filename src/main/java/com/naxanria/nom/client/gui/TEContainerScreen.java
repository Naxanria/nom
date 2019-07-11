package com.naxanria.nom.client.gui;

import com.naxanria.nom.container.TEContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;

public abstract class TEContainerScreen<TE extends TileEntity, TC extends TEContainer<TE>> extends ContainerScreen<TC>
{
  protected final TE tile;
  
  public TEContainerScreen(TC container, PlayerInventory inventory, ITextComponent title)
  {
    super(container, inventory, title);
    
    tile = container.getTileEntity();
  }
}
