package com.naxanria.nom.Item;

import net.minecraft.item.Food;
import net.minecraft.item.Item;

import javax.annotation.Nullable;

public class FoodItem extends Item
{
  private Food food;
  
  public FoodItem(Properties p_i48487_1_)
  {
    super(p_i48487_1_);
    
    food = super.getFood();
  }
  
  @Nullable
  @Override
  public Food getFood()
  {
    return food;
  }
  
  public FoodItem setFood(Food food)
  {
    this.food = food;
    
    return this;
  }
  
}
