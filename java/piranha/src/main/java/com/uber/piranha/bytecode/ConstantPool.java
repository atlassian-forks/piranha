package com.uber.piranha.bytecode;

import com.uber.piranha.bytecode.constants.ConstantItem;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ConstantPool {
  Map<Integer, ConstantItem<?>> pool;

  public ConstantPool() {
    this.pool = new HashMap<>();
  }

  public void addItem(int index, ConstantItem<?> item) {
    pool.put(index, item);
  }

  public boolean contains(int index) {
    return pool.containsKey(index);
  }

  public ConstantItem<?> get(int index) {
    ConstantItem<?> val = pool.get(index);
    if (val == null) {
      return new ConstantItem<String>() {
        @Override
        public ConstantPoolType getType() {
          return ConstantPoolType.DYNAMIC;
        }

        @Override
        public String getValue() {
          return "oops nothin here";
        }
      };
    }
    return val;
  }

  @Override
  public String toString() {
    return pool.entrySet()
        .stream()
        .map(entry -> "Entry[" + entry.getKey() + "]: " + entry.getValue().getValue())
        .collect(Collectors.joining("\n"));
  }
}
