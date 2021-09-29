package com.uber.piranha.bytecode;

import com.uber.piranha.PiranhaRuntimeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.tools.JavaFileObject;

public class BytecodeUtils {
  public static byte[] getBytecode(JavaFileObject classfile) {
    try (InputStream is = classfile.openInputStream()) {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      int byteStream;
      byte[] data = new byte[16384];
      while ((byteStream = is.read(data, 0, data.length)) != -1) {
        buffer.write(data, 0, byteStream);
      }
      return buffer.toByteArray();
    } catch (IOException e) {
      throw new PiranhaRuntimeException("Could not read classfile.");
    }
  }

  public static int u2(byte[] bytes, int startIndex) {
    return ((bytes[startIndex] & 0xFF) << 8) | (bytes[startIndex + 1] & 0xFF);
  }

  public static int u4(byte[] bytes, int startIndex) {
    return ((bytes[startIndex] & 0xFF) << 24)
        | ((bytes[startIndex + 1] & 0xFF) << 16)
        | ((bytes[startIndex + 2] & 0xFF) << 8)
        | (bytes[startIndex + 3] & 0xFF);
  }
}
