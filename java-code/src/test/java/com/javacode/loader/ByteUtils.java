package com.javacode.loader;

public class ByteUtils {

    public static int bytes2Int(byte[] b, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = start; i < end; i++) {
            int n = b[i] & 0xff;
            n <<= (--len) * 8;
            sum += n;
        }
        return sum;
    }

    public static byte[] int2Bytes(int value, int len) {
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[len - i - 1] = (byte) (value >>> (i * 8) & 0xff);
        }
        return b;
    }

    public static String bytes2String(byte[] b, int start, int len) {
        return new String(b, start, len);
    }

    public static byte[] string2Bytes(String str) {
        return str.getBytes();
    }

    public static byte[] bytesReplace(byte[] b, int start, int len, byte[] newBytes) {
        byte[] newByte = new byte[b.length + newBytes.length - len];
        System.arraycopy(b, 0, newByte, 0, start);
        System.arraycopy(newBytes, 0, newByte, start, newBytes.length);
        System.arraycopy(b, start + len, newByte, start + newBytes.length, b.length - start - len);
        return newByte;
    }
}
