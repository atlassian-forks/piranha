package com.uber.piranha.bytecode.constants;

import static com.uber.piranha.bytecode.BytecodeUtils.u2;

import com.uber.piranha.bytecode.ConstantPoolType;

public class StringReference implements ConstantItem<Integer> {
  Integer value;

  public StringReference(final byte[] bytes, int srcPos) {
    this.value = u2(bytes, srcPos);
  }

  @Override
  public ConstantPoolType getType() {
    return ConstantPoolType.STRING_REF;
  }

  @Override
  public Integer getValue() {
    return value;
  }
}
