package com.uber.piranha.bytecode.constants;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.uber.piranha.bytecode.ConstantPoolType;

public class UnicodeConstant implements ConstantItem<String> {
  String value;

  public UnicodeConstant(final byte[] bytes, int srcPos, int length) {
    byte[] result = new byte[length];
    System.arraycopy(bytes, srcPos, result, 0, length);
    this.value = new String(result, UTF_8);
  }

  @Override
  public ConstantPoolType getType() {
    return ConstantPoolType.UNICODE;
  }

  @Override
  public String getValue() {
    return value;
  }
}
