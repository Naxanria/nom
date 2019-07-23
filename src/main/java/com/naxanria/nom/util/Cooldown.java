package com.naxanria.nom.util;

import net.minecraft.nbt.CompoundNBT;

public class Cooldown implements INBTUpdate
{
  private int time;
  private int cooldown;
  private boolean autoRestart = false;
  private Action action;
  
  private boolean fired = false;
  
  public Cooldown(int cooldown)
  {
    this(0, cooldown);
  }
  
  public Cooldown(int time, int cooldown)
  {
    this.time = time;
    this.cooldown = cooldown;
  }
  
  public int getTime()
  {
    return time;
  }
  
  public Cooldown setTime(int time)
  {
    this.time = time;
    return this;
  }
  
  public int getCooldown()
  {
    return cooldown;
  }
  
  public Cooldown setCooldown(int cooldown)
  {
    this.cooldown = cooldown;
    return this;
  }
  
  public boolean isAutoRestart()
  {
    return autoRestart;
  }
  
  public Cooldown setAutoRestart(boolean autoRestart)
  {
    this.autoRestart = autoRestart;
    return this;
  }
  
  public Action getAction()
  {
    return action;
  }
  
  public Cooldown setAction(Action action)
  {
    this.action = action;
    return this;
  }
  
  public boolean isDone()
  {
    return time >= cooldown;
  }
  
  public int getRemaining()
  {
    return (time >= cooldown) ? 0 : cooldown - time;
  }
  
  public float getProgress()
  {
    if (cooldown == 0)
    {
      return 0;
    }
    
    return (cooldown - getRemaining()) / (float) cooldown;
  }
  
  public Cooldown restart()
  {
    time = 0;
    fired = false;
    
    return this;
  }
  
  public Cooldown update()
  {
    time++;
    
    if (!fired && time >= cooldown)
    {
      fired = true;
      
      if (action != null)
      {
        action.invoke();
      }
      
      if (autoRestart)
      {
        restart();
      }
    }
    
    return this;
  }
  
  
  @Override
  public CompoundNBT writeToNBT(CompoundNBT compound)
  {
    compound.putInt("time", time);
    compound.putInt("cooldown", cooldown);
    compound.putBoolean("restart", autoRestart);
  
    return compound;
  }
  
  @Override
  public void readFromNBT(CompoundNBT compound)
  {
    time = compound.getInt("time");
    cooldown = compound.getInt("cooldown");
    autoRestart = compound.getBoolean("restart");
  }
}
