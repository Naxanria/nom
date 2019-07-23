package com.naxanria.nom.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.naxanria.nom.Nom;
import com.naxanria.nom.container.ApiaryContainer;
import com.naxanria.nom.tile.ApiaryTile;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ApiaryScreen extends TEContainerScreen<ApiaryTile, ApiaryContainer>
{
  public static final ResourceLocation BACKGROUND = new ResourceLocation("nom", "textures/gui/container/apiary.png");
  
  public ApiaryScreen(ApiaryContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_)
  {
    super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
  }
  
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    int x = width / 2 - xSize / 2;
    int y = height / 2 - ySize / 2;
    
    GlStateManager.color4f(1, 1, 1, 1);
    minecraft.getTextureManager().bindTexture(BACKGROUND);
    blit(x, y, 0, 0, xSize, ySize);
    int w = (int) (tile.progress() * 23);
    blit(x + 80, y + 34, 176, 0, w + 1, 16);
    drawCenteredString(font, title.getFormattedText(), width / 2, y + 6, 0xffffffff);
    
//    DrawHelper.progressBar(x + 80, y + 41, 31, 3, tile.progress(),0xffffffff, 0xff121212);
//    drawCenteredString(font, tile.progress() + "" ,x + 80, y + 41 - 10, 0xffffffff);
    
//    drawCenteredString(font, "Slots: " + container.inventorySlots.size(), x, y, 0xffffffff);
  }
  
  @Override
  public void render(int p_render_1_, int p_render_2_, float p_render_3_)
  {
    super.render(p_render_1_, p_render_2_, p_render_3_);
  }
}
