package com.pp.pikachu.dagger.modules;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

@Singleton public class DataParser implements Serializable {

  public DataParser() {
  }

  public List<String> parseKeys(final JsonObject jsonObject) {
    final Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
    List<String> keys = new ArrayList<>();
    for (Map.Entry<String, JsonElement> entry : entries) {
      keys.add(entry.getKey());
    }
    return keys;
  }
}
