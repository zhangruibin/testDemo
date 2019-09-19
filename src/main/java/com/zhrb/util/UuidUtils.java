package com.zhrb.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * V 1.0.0
 * Created by tong on 2015/3/18.
 * <p>
 * Copyright © 2015 中国电信股份有限公司增值业务运营中心 版权所有
 * <p>
 * 所有文字、代码资料，版权均属中国电信股份有限公司增值业务运营中
 * 心所有，任何公司或个人未经本司协议授权，不得复制、下载、存储或
 * 公开显示。违者本公司将依法追究责任。
 */
public class UuidUtils {

    /**
     * 产生UUID（32位大写）
     *
     * @return String
     */
    public static synchronized String randomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").toUpperCase();
    }

    /**
     * 生成 Base58 UUID
     *
     * @return  UUID(长度21或22位)
     */
    public static synchronized String base58Uuid() {
        UUID uuid = UUID.randomUUID();
        return base58Uuid(uuid);
    }

    public static synchronized String base58Uuid(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return Base58.encode(bb.array());
    }

    public static synchronized String encodeBase58Uuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        return base58Uuid(uuid);
    }

    public static synchronized String decodeBase58Uuid(String base58uuid) {
        byte[] byUuid = Base58.decode(base58uuid);
        ByteBuffer bb = ByteBuffer.wrap(byUuid);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid.toString();
    }
}

class Base58 {
    //public static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    //zhrb-20190529-修复漏洞
    protected static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final int[] INDEXES = new int[128];

    static {
        for (int i = 0; i < INDEXES.length; i++) {
            INDEXES[i] = -1;
        }
        for (int i = 0; i < ALPHABET.length; i++) {
            INDEXES[ALPHABET[i]] = i;
        }
    }

    /**
     * Encodes the given bytes in base58. No checksum is appended.
     */
    public static String encode(byte[] input) {
        if (input.length == 0) {
            return "";
        }
        input = copyOfRange(input, 0, input.length);
        // Count leading zeroes.
        int zeroCount = 0;
        while (zeroCount < input.length && input[zeroCount] == 0) {
            ++zeroCount;
        }
        // The actual encoding.
        byte[] temp = new byte[input.length * 2];
        int j = temp.length;

        int startAt = zeroCount;
        while (startAt < input.length) {
            byte mod = divmod58(input, startAt);
            if (input[startAt] == 0) {
                ++startAt;
            }
            temp[--j] = (byte) ALPHABET[mod];
        }

        // Strip extra '1' if there are some after decoding.
        while (j < temp.length && temp[j] == ALPHABET[0]) {
            ++j;
        }
        // Add as many leading '1' as there were leading zeros.
        while (--zeroCount >= 0) {
            temp[--j] = (byte) ALPHABET[0];
        }

        byte[] output = copyOfRange(temp, j, temp.length);
        try {
            return new String(output, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);  // Cannot happen.
        }
    }

    public static byte[] decode(String input) throws IllegalArgumentException {
        if (input.length() == 0) {
            return new byte[0];
        }
        byte[] input58 = new byte[input.length()];
        // Transform the String to a base58 byte sequence
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);

            int digit58 = -1;
            if (Character.getNumericValue(c) >= 0 && Character.getNumericValue(c) < 128) {
                digit58 = INDEXES[c];
            }
            if (digit58 < 0) {
                throw new IllegalArgumentException("Illegal character " + c + " at " + i);
            }

            input58[i] = (byte) digit58;
        }
        // Count leading zeroes
        int zeroCount = 0;
        while (zeroCount < input58.length && input58[zeroCount] == 0) {
            ++zeroCount;
        }
        // The encoding
        byte[] temp = new byte[input.length()];
        int j = temp.length;

        int startAt = zeroCount;
        while (startAt < input58.length) {
            byte mod = divmod256(input58, startAt);
            if (input58[startAt] == 0) {
                ++startAt;
            }

            temp[--j] = mod;
        }
        // Do no add extra leading zeroes, move j to first non null byte.
        while (j < temp.length && temp[j] == 0) {
            ++j;
        }

        return copyOfRange(temp, j - zeroCount, temp.length);
    }

    public static BigInteger decodeToBigInteger(String input) throws IllegalArgumentException {
        return new BigInteger(1, decode(input));
    }

    //
    // number -> number / 58, returns number % 58
    //
    private static byte divmod58(byte[] number, int startAt) {
        int remainder = 0;
        for (int i = startAt; i < number.length; i++) {
            int digit256 = (int) number[i] & 0xFF;
            int temp = remainder * 256 + digit256;

            number[i] = (byte) (temp / 58);

            remainder = temp % 58;
        }

        return (byte) remainder;
    }

    //
    // number -> number / 256, returns number % 256
    //
    private static byte divmod256(byte[] number58, int startAt) {
        int remainder = 0;
        for (int i = startAt; i < number58.length; i++) {
            int digit58 = (int) number58[i] & 0xFF;
            int temp = remainder * 58 + digit58;

            number58[i] = (byte) (temp / 256);

            remainder = temp % 256;
        }

        return (byte) remainder;
    }

    private static byte[] copyOfRange(byte[] source, int from, int to) {
        byte[] range = new byte[to - from];
        System.arraycopy(source, from, range, 0, range.length);

        return range;
    }
}