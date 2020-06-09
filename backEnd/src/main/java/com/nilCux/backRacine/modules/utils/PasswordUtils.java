package com.nilCux.backRacine.modules.utils;

import com.nilCux.backRacine.config.ConstantsSBS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;

/**
 *
 * @description: Password encryption tools
 * @author: nilCux
 * @date: 2019/10/13 0013 15:25
 */
@Slf4j
public class PasswordUtils {

    /**
     * Check if the password is valid
     *
     * @param password:
     * @param encodedPassword：encoded password saved in DB
     * @param salt：salt value
     * @return
     */
    public static boolean isValidPassword(String password, String encodedPassword, String salt) {
        return encodedPassword.equalsIgnoreCase(encodePassword(password, salt));
    }

    /**
     * Encode password with SHA1 algorithm
     *
     * @param password
     * @param salt
     * @return String
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
            int iterations = ConstantsSBS.HASH_ITERATIONS - 1;
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
            throw new IllegalArgumentException("Invalid String variable input");
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
