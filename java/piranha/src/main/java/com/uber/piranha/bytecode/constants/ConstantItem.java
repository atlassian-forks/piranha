package com.uber.piranha.bytecode.constants;

import com.uber.piranha.bytecode.ConstantPoolType;

public interface ConstantItem<T> {
  ConstantPoolType getType();

  T getValue();
}
