package com.uber.piranha.bytecode;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum BytecodeInstruction {
  ALOAD("aload", (byte) 0x19, 1),
  ALOAD_0("aload_0", (byte) 0x2a, 0),
  ANEWARRAY("anewarray", (byte) 0xbd, 2),
  ASTORE("astore", (byte) 0x3a, 1),
  PUTFIELD("putfield", (byte) 0xb5, 2),
  GETFIELD("getfield", (byte) 0xb4, 2),
  RET("ret", (byte) 0xa9, 1),
  ARETURN("areturn", (byte) 0xb0, 0),
  CHECKCAST("checkcast", (byte) 0xc0, 2),
  INSTANCEOF("instanceof", (byte) 0xc1, 2),
  INVOKEDYNAMIC("invokedynamic", (byte) 0xba, 4),
  INVOKESPECIAL("invokespecial", (byte) 0xb7, 2),
  INVOKEINTERFACE("invokeinterface", (byte) 0xb9, 4),
  INVOKESTATIC("invokestatic", (byte) 0xb8, 2),
  INVOKEVIRTUAL("invokevirtual", (byte) 0xb6, 2),
  GETSTATIC("getstatic", (byte) 0xb2, 2),
  PUTSTATIC("putstatic", (byte) 0xb3, 2),
  AASTORE("aastore", (byte) 0x53, 0),
  BIPUSH("bipush", (byte) 0x10, 1),
  SIPUSH("sipush", (byte) 0x11, 2),
  ICONST_0("iconst_0", (byte) 0x03, 0),
  ICONST_1("iconst_1", (byte) 0x04, 0),
  ICONST_2("iconst_2", (byte) 0x05, 0),
  ICONST_3("iconst_3", (byte) 0x06, 0),
  ICONST_4("iconst_4", (byte) 0x07, 0),
  ICONST_5("iconst_5", (byte) 0x08, 0),
  MULTIANEWARRAY("multianewarray ", (byte) 0xc5, 3),
  NEW("new", (byte) 0xbb, 2),
  NEWARRAY("newarray", (byte) 0xbc, 1),
  LDC("ldc", (byte) 0x12, 1),
  LDC_W("ldc_w", (byte) 0x13, 2),
  LDC2_W("ldc2_w", (byte) 0x14, 2),
  LLOAD("lload", (byte) 0x16, 1),
  LSTORE("lstore", (byte) 0x37, 1),
  DLOAD("dload", (byte) 0x18, 1),
  DSTORE("dstore", (byte) 0x39, 1),
  FLOAD("fload", (byte) 0x17, 1),
  FSTORE("fstore", (byte) 0x38, 1),
  ISTORE("istore", (byte) 0x36, 1),
  GOTO("goto", (byte) 0xa7, 2),
  GOTO_W("goto_w", (byte) 0xc8, 4),
  JSR("jsr", (byte) 0xa8, 2),
  JSR_W("jst_w", (byte) 0xc9, 4),

  IF_ACMPEQ("if_acmpeq", (byte) 0xa5, 2),
  IF_ACMPNE("if_acmpne", (byte) 0xa6, 2),
  IF_ICMPEQ("if_icmpeq", (byte) 0x9f, 2),
  IF_ICMPGE("if_icmpge", (byte) 0xa2, 2),
  IF_ICMPGT("if_icmpgt", (byte) 0xa3, 2),
  IF_ICMPLE("if_icmple", (byte) 0xa4, 2),
  IF_ICMPLT("if_icmplt", (byte) 0xa1, 2),
  IF_ICMPNE("if_icmpne", (byte) 0xa0, 2),
  IFEQ("ifeq", (byte) 0x99, 2),
  IFGE("ifge", (byte) 0x9c, 2),
  IFGT("ifgt", (byte) 0x9d, 2),
  IFLE("ifle", (byte) 0x9e, 2),
  IFLT("iflt", (byte) 0x9b, 2),
  IFNE("ifne", (byte) 0x9a, 2),
  IFNONNULL("ifnonnull", (byte) 0xc7, 2),
  IFNULL("ifnull", (byte) 0xc6, 2),
  IINC("iinc", (byte) 0x84, 2),

  DUP("dup", (byte) 0x59, 0),
  UNKNOWN("unknown", (byte) 0x0, 0);

  private final String name;
  private final byte opcode;
  private final int otherBytes;

  private static final Map<Byte, BytecodeInstruction> bytecodeMap = toMap();

  private static Map<Byte, BytecodeInstruction> toMap() {
    return Arrays.stream(BytecodeInstruction.values())
        .collect(Collectors.toMap(BytecodeInstruction::getOpcode, inst -> inst));
  }

  public String getName() {
    return name;
  }

  public byte getOpcode() {
    return opcode;
  }

  public int getOtherBytes() {
    return otherBytes;
  }

  public static BytecodeInstruction fromOpcode(byte opcode) {
    if (bytecodeMap.containsKey(opcode)) {
      BytecodeInstruction instruction = bytecodeMap.get(opcode);
      if (instruction == null) {
        return BytecodeInstruction.UNKNOWN;
      }
      return instruction;
    }
    return BytecodeInstruction.UNKNOWN;
  }

  BytecodeInstruction(String name, byte opcode, int otherBytes) {
    this.name = name;
    this.opcode = opcode;
    this.otherBytes = otherBytes;
  }
}
