/**
 * Oracle Corporation, Inc.
 *
 * Copyright (C) 2016, Oracle and/or its affiliates. All rights reserved
 */

package com.oracle.communications.incubation.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 */
public class Utils {
  /**
   * A utility function to build a lookup map from an array of Objects. More formally, given an
   * array of values of type V which have a unique attribute of type K, a Map will be produced where
   * a given instance of V is stored using its value for K as a key.
   *
   * Example usage: Map<String, MyEnum> map = buildLookupMap(MyEnum.values(), MyEnum::getKey);
   *
   * @param <K> the type of the keys
   * @param <V> the type of the values
   * @param values an array of values of type V
   * @param lambda a function which accepts an instance of type V and returns a key of type K
   * @return a Map containing the values by key
   */
  public static <K, V> Map<K, V> buildLookupMap(V[] values,
      Function<V, K> lambda) {
    Map<K, V> map = new HashMap<>();

    for (V value : values) {
      K key = lambda.apply(value);
      if (map.containsKey(key)) {
        throw new IllegalArgumentException("Key '" + key + "' in "
            + value.getClass() + " maps to multiple values '" + value
            + "' and '" + map.get(key) + "'");
      }

      map.put(key, value);
    }

    return map;
  }
}
