package com.naxanria.nom.client.gui;

import com.naxanria.nom.container.ApiaryContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ApiaryScreen extends ContainerScreen<ApiaryContainer>
{
  public ApiaryScreen(ApiaryContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_)
  {
    super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    fill(10, 10, width, height, 0xffcccccc);
  }
}
