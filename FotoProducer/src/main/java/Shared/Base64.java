/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author marcowillems
 */
public class Base64 {

    public static String Encode(String value) throws UnsupportedEncodingException {
        return java.util.Base64.getEncoder().encodeToString(value.getBytes("utf-8"));
    }

    public static String Decode(String value) throws UnsupportedEncodingException {
        byte[] base64decodedBytes = java.util.Base64.getDecoder().decode(value);
        return new String(base64decodedBytes, "utf-8");
    }
}
