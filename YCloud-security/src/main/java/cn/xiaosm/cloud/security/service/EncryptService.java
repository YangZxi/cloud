package cn.xiaosm.cloud.security.service;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;

public class EncryptService {

    private static final byte[] KEY = "test1234test1234".getBytes();
    private static final byte[] IV = KEY;

    // 加密
    public static String encrypt(String raw) throws Exception {
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, KEY, IV);
        // 加密为16进制表示
        return aes.encryptHex(raw);
    }

    // 解密
    public static String decrypt(String arg) throws Exception {
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, KEY, IV);
        // 解密为字符串
        return aes.decryptStr(arg, CharsetUtil.CHARSET_UTF_8);
    }
}