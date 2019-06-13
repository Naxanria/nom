package com.naxanria.nom.util;

public class Time
{
  public static class Ticks
  {
    public static final int SECOND = 20;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = MINUTE * 60;
    public static final int DAY = HOUR * 24;
  }
  
  public static class Milli
  {
    public static final int SECOND = 1000;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = MINUTE * 60;
    public static final int DAY = HOUR * 24;
  }
  
  public static class Second
  {
    public static final int SECOND = 1;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = MINUTE * 60;
    public static final int DAY = HOUR * 24;
  }
}
