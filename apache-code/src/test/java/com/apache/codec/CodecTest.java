package com.apache.codec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

@DisplayName("apache codec 测试类")
public class CodecTest {

    @Test
    public void hexTest() throws DecoderException {
        // 16进制
        String hex = Hex.encodeHexString("abc".getBytes(StandardCharsets.UTF_8));
        System.out.println(hex);

        String s = new String(Hex.decodeHex(hex));
        System.out.println(s);
    }

    /**
     * base16 0-9 A-F
     * base32 A-Z 2-7
     * base64 A-Z a-z 0-9 + /
     * <p>
     * base64 编码解码
     */
    @Test
    public void base64Test() {
        String s = Base64.encodeBase64String("abc".getBytes(StandardCharsets.UTF_8));
        System.out.println(s);

        String s1 = new String(Base64.decodeBase64(s));
        System.out.println(s1);
    }

    /**
     * url编码
     */
    @Test
    public void urlTest() throws EncoderException, DecoderException {
        URLCodec codec = new URLCodec();
        String url = codec.encode("http://coderead.cn/p/mybatis/map/file/结果集处理.map");
        String encode = codec.encode(url);
        System.out.println(encode);

        String decode = codec.decode(encode);
        System.out.println(decode);
    }

    /**
     * 摘要算法
     * <p>
     * md(message digest) 消息摘要
     * sha(secure hash algorithm) 安全散列
     * mac(message authentication code) 消息认证码
     */
    @Test
    public void digestTest() {
        String s = RandomStringUtils.randomAscii(10);

        String md5 = DigestUtils.md5Hex(s);
        System.out.println(s);
        System.out.println(md5);

        String sha256Hex = DigestUtils.sha256Hex(s);
        System.out.println(sha256Hex);

        String key = RandomStringUtils.randomAscii(32);
        // hmac-md5 摘要
        String hmacHex = new HmacUtils(HmacAlgorithms.HMAC_MD5, key).hmacHex(s);
        System.out.println(hmacHex);
        // hmac-sha256
        String hmac256Hex = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmacHex(s);
        System.out.println(hmac256Hex);
    }
}
