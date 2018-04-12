package com.example.vincent.crypto.ChiffrementClass;

import java.text.Normalizer;

/**
 * Created by Vincent on 08/04/2018.
 */

public class AtbashChiffrement {

    final static int ASCII_MIN = 65;
    final static int ASCII_MAX = 122;


    public static String crypt(String message) {
        String out = ""; // message de sortie
        message = removeAccents(message);

        // pour chaque lettre
        for (char uneLettre : message.toCharArray()) {
            int asciiLetter = (int) uneLettre;
            if (asciiLetter >= 65 && asciiLetter <= 90) {
                int ecartAvecPremiereLettre = asciiLetter - 65;
                asciiLetter = 90 - ecartAvecPremiereLettre;
                out = out + String.valueOf((char) asciiLetter);
            } else if (asciiLetter >= 97 && asciiLetter <= 122) {
                int ecartAvecPremiereLettre = asciiLetter - 97;
                asciiLetter = 122 - ecartAvecPremiereLettre;
                out = out + String.valueOf((char) asciiLetter);
            } else if (asciiLetter == 32) {
                out = out + String.valueOf((char) asciiLetter);
            }
        }

        return out;
    }


    public static String decrypt(String message) {
        String out = ""; // message de sortie
        message = removeAccents(message.toLowerCase());

        // pour chaque lettre
        for (char uneLettre : message.toCharArray()) {
            int asciiLetter = (int) uneLettre;
            if (asciiLetter >= 65 && asciiLetter <= 90) {
                int ecartDerniereLettre = 90 - asciiLetter;
                asciiLetter = 65 + ecartDerniereLettre;
                out = out + String.valueOf((char) asciiLetter);
            } else if (asciiLetter >= 97 && asciiLetter <= 122) {
                int ecartDerniereLettre = 122 - asciiLetter;
                asciiLetter = 97 + ecartDerniereLettre;
                out = out + String.valueOf((char) asciiLetter);
            } else if (asciiLetter == 32) {
                out = out + String.valueOf((char) asciiLetter);
            }
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
