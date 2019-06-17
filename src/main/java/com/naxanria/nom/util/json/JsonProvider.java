package com.naxanria.nom.util.json;

import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.naxanria.nom.util.ListUtil;
import com.naxanria.nom.util.StringUtil;
import net.minecraft.util.JSONUtils;

import java.io.*;
import java.nio.file.Files;

public class JsonProvider
{
  public static JsonObject getJson(String file)
  {
    return JSONUtils.fromJson(file);
  }
  
  public static <T> T get(String file, IJsonSerializer<T> serializer)
  {
    return serializer.deserialize(getJson(file));
  }
  
  public static String toString(JsonObject object)
  {
    try
    {
      StringWriter stringWriter = new StringWriter();
      JsonWriter jsonWriter = new JsonWriter(stringWriter);
      jsonWriter.setLenient(true);
      jsonWriter.setIndent(" ");
      Streams.write(object, jsonWriter);
      return stringWriter.toString();
    }
    catch (IOException e)
    {
      throw new AssertionError(e);
    }
  }
  
  public static void writeToDisk(File file, JsonObject object)
  {
    try
    {
      Files.write(file.toPath(), ListUtil.asList(JsonProvider.toString(object).split("\n")));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public static JsonObject readFromDisk(File file)
  {
    if (!file.exists())
    {
      return new JsonObject();
    }
  
    try
    {
      String string = StringUtil.compact(Files.readAllLines(file.toPath()));
      return JSONUtils.fromJson(string);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  
    return new JsonObject();
  }
}
