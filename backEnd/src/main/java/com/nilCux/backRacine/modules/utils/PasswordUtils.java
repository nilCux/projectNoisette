package com.nilCux.backRacine.modules.utils;

import com.nilCux.backRacine.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;

/**
 *  <p> 加密工具 </p>
 *
 * @description:
 * @author: zhengqing
 * @date: 2019/10/13 0013 15:25
 */
@Slf4j
public class PasswordUtils {

    /**
     * 校验密码是否一致
     *
     * @param password: 前端传过来的密码
     * @param hashedPassword：数据库中储存加密过后的密码
     * @param salt：盐值
     * @return
     */
    public static boolean isValidPassword(String password, String hashedPassword, String salt) {
        return hashedPassword.equalsIgnoreCase(encodePassword(password, salt));
    }

    /**
     * 通过SHA1对密码进行编码
     *
     * @param password
     * @param salt
     * @return
     */
    public static String encodePassword(String password, String salt) {
        String encodedPassword;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            if (salt != null) {
                digest.reset();
                digest.update(salt.getBytes());
            }
            byte[] hashed = digest.digest(password.getBytes());
            int iterations = Constants.HASH_ITERATIONS - 1;
            for (int i = 0; i < iterations; ++i) {
                digest.reset();
                hashed = digest.digest(hashed);
            }
            encodedPassword = new String(Hex.encode(hashed));
        } catch (Exception e) {
            log.error("Exception encoding password :", e);
            return null;
        }
        return encodedPassword;
    }

    public static String saltGenerator(String saltyWater) {
        if (saltyWater == null || saltyWater.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(saltyWater.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }
}
