package com.naxanria.nom;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class NomMaterials
{
  public static class Builder
  {
    private final MaterialColor color;
    
    private PushReaction pushReaction;
    private boolean blocksMovement = true;
    private boolean flammable;
    private boolean requiresNoTool = true;
    private boolean isLiquid;
    private boolean isOpaque = true;
    private boolean replaceable;
    private boolean isSolid = true;
  
    public Builder(MaterialColor color)
    {
      this.color = color;
    }
  
    public Builder setPushReaction(PushReaction pushReaction)
    {
      this.pushReaction = pushReaction;
      return this;
    }
  
    public Builder setBlocksMovement(boolean blocksMovement)
    {
      this.blocksMovement = blocksMovement;
      return this;
    }
  
    public Builder setFlammable(boolean flammable)
    {
      this.flammable = flammable;
      return this;
    }
  
    public Builder setRequiresTool()
    {
      this.requiresNoTool = false;
      return this;
    }
  
    public Builder setLiquid()
    {
      return setLiquid(true);
    }
  
    public Builder setLiquid(boolean liquid)
    {
      isLiquid = liquid;
      return this;
    }
  
    public Builder setOpaque()
    {
      return setOpaque(true);
    }
  
    public Builder setOpaque(boolean opaque)
    {
      isOpaque = opaque;
      return this;
    }
  
    public Builder setReplaceable()
    {
      return setReplaceable(true);
    }
  
    public Builder setReplaceable(boolean replaceable)
    {
      this.replaceable = replaceable;
      return this;
    }
  
    public Builder setSolid()
    {
      return setSolid(true);
    }
  
    public Builder setSolid(boolean solid)
    {
      isSolid = solid;
      return this;
    }
    
    public Material build()
    {
      return new Material(color, isLiquid, isSolid, blocksMovement, isOpaque, requiresNoTool, flammable, replaceable, pushReaction);
    }
  }
  
  public static final Material BEE_HIVE = new Builder(MaterialColor.WOOL).setRequiresTool().setOpaque(false).build();
}
