package com.naxanria.nom.client.gui;

import com.naxanria.nom.Nom;
import com.naxanria.nom.container.ApiaryContainer;
import com.naxanria.nom.tile.ApiaryTile;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;

public class ApiaryScreen extends TEContainerScreen<ApiaryTile, ApiaryContainer>
{
  public ApiaryScreen(ApiaryContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_)
  {
    super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    int x = width / 2 - xSize / 2;
    int y = height / 2 - ySize / 2;

    fill(x, y, x + xSize, y + ySize, 0xff999999);
    drawCenteredString(font, title.getFormattedText(), width / 2, y + 6, 0xff444444);
    
    //ApiaryTile tile = container.getTileEntity();
//    ApiaryTile tile = getTile();
    
    if (tile == null)
    {
      Nom.LOGGER.warn("tile is null");
      return;
    }
    
    drawString(font, "Bees: " + tile.getBees(), x + 10, y + 6 + 25, 0xff888888);
    drawString(font, "HoneyComb: " + tile.getOutput().getStackInSlot(0).getCount(), x + xSize - 50, y + 6 + 25, 0xff888888);
  }
}
