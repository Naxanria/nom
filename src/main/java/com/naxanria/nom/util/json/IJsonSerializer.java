package com.naxanria.nom.util.json;

import com.google.gson.JsonObject;

public interface IJsonSerializer<T>
{
  JsonObject serialize(T t);
  T deserialize(JsonObject t);
}
