package com.uber.piranha.bytecode;

import static com.uber.piranha.bytecode.BytecodeUtils.u2;

import com.uber.piranha.PiranhaRuntimeException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ConstantPoolType {
  UNICODE(1, 2),
  INTEGER(3, 4),
  FLOAT(4, 4),
  LONG(5, 8),
  DOUBLE(6, 8),
  CLASS_REF(7, 2),
  STRING_REF(8, 2),
  FIELD_REF(9, 4),
  METHOD_REF(10, 4),
  IMETHOD_REF(11, 4),
  NAME_AND_TYPE_DESC(12, 4),
  METHOD_HANDLE(15, 3),
  METHOD_TYPE(16, 2),
  DYNAMIC(17, 4),
  INVOKE_DYNAMIC(18, 4),
  MODULE(19, 2),
  PACKAGE(20, 2);

  private final byte value;
  private final int size;

  private static final Map<Byte, ConstantPoolType> constantPoolTypeMap = toMap();

  private static Map<Byte, ConstantPoolType> toMap() {
    return Arrays.stream(ConstantPoolType.values())
        .collect(Collectors.toMap(ConstantPoolType::getValue, poolType -> poolType));
  }

  public byte getValue() {
    return value;
  }

  public int getSize(byte[] bytes, int index) {
    if (this == UNICODE) {
      return size + u2(bytes, index + 1);
    }
    return size;
  }

  public static ConstantPoolType fromValue(final byte value) {
    if (constantPoolTypeMap.containsKey(value)) {
      ConstantPoolType poolType = constantPoolTypeMap.get(value);
      if (poolType != null) {
        return poolType;
      }
    }
    throw new PiranhaRuntimeException("Could not find constant pool type matching `" + value + "`");
  }

  ConstantPoolType(final int value, final int size) {
    this.value = (byte) value;
    this.size = size;
  }
}
