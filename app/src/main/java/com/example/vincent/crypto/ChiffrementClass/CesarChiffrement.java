package com.example.vincent.crypto.ChiffrementClass;

import java.text.Normalizer;

/**
 * Created by Vincent on 06/04/2018.
 */

public class CesarChiffrement {

    final static int ASCII_MIN = 32;
    final static int ASCII_MAX = 126;

    /**
     * Crypter
     */
    public static String crypt(int cle, String message) {
        // message a retourner
        String out = "";

        char[] messageToArray = removeAccents(message).toCharArray();
        for (char uneLettre : messageToArray) {
            // récupérer la valeur ascii et décaler du rang demandé
            int asciiLetter = (int) uneLettre;
            asciiLetter += cle;


            while (asciiLetter > ASCII_MAX)
                asciiLetter -= ASCII_MAX;

            while (asciiLetter < ASCII_MIN)
                asciiLetter += ASCII_MIN;


            out = out + String.valueOf((char) asciiLetter);
        }


        return out;
    }


    /**
     * Decrypter
     */
    public static String decrypt(int cle, String message) {
        // message a retourner
        String out = "";

        char[] messageToArray = removeAccents(message).toCharArray();
        for (char uneLettre : messageToArray) {
            // récupérer la valeur ascii et décaler du rang demandé
            int asciiLetter = (int) uneLettre;
            asciiLetter -= cle;


            while (asciiLetter > ASCII_MAX)
                asciiLetter -= ASCII_MAX;

            while (asciiLetter < ASCII_MIN)
                asciiLetter = ASCII_MAX - (ASCII_MIN - asciiLetter);


            out = out + String.valueOf((char) asciiLetter);
        }


        return out;
    }


    /**
     * Enlève les accents dans la phrase
     */
    private static String removeAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }


}
