package com.example.vincent.crypto.ChiffrementClass;

import java.text.Normalizer;

/**
 * Created by Vincent on 07/04/2018.
 */

public class VigenereChiffrement {
    private static final String TAG = "VigenereChiffrement";

    final static int ASCII_MIN = 32;
    final static int ASCII_MAX = 126;


    /**
     * Crypter
     */
    public static String crypt(String cle, String message) {
        // message a retourner
        String out = "";
        message = removeAccents(message);


        int pos_cle = 0;
        int pos_message = 0;

        // parcourir chaque lettre du message
        while (pos_message < message.length()) {
            // récupérer la lettre du message à l'index pos_message
            char current_message_letter = message.charAt(pos_message);
            // récupérer la lettre de la clé à l'index pos_cle
            char current_cle_letter = cle.charAt(pos_cle);

            // transformer les lettres en valeur ascii
            int ascii_cle = (int) current_cle_letter;
            int ascii_message = (int) current_message_letter;

            //décaler le chr de la clé avec la différence entre la clé et le message
            int decalage_message = ascii_cle + (ascii_message - ASCII_MIN);
            if (decalage_message > ASCII_MAX) {
                decalage_message = decalage_message - ASCII_MAX + ASCII_MIN;
            }

            // rajouter le message décaler au message finale
            out = out + String.valueOf((char) decalage_message);

            pos_message++;
            pos_cle++;
            if (pos_cle >= cle.length())
                pos_cle = 0;
        }


        return out;
    }


    /**
     * Decrypter
     */
    public static String decrypt(String cle, String message) {
        // message a retourner
        String out = "";
        message = removeAccents(message);


        int pos_cle = 0;
        int pos_message = 0;

        // parcourir chaque lettre du message
        while (pos_message < message.length()) {
            // récupérer la lettre du message à l'index pos_message
            char current_message_letter = message.charAt(pos_message);
            // récupérer la lettre de la clé à l'index pos_cle
            char current_cle_letter = cle.charAt(pos_cle);

            // transformer les lettres en valeur ascii
            int ascii_cle = (int) current_cle_letter;
            int ascii_message = (int) current_message_letter;

            //décaler le chr de la clé avec la différence entre la clé et le message
            int decalage_message = ascii_message - (ascii_cle - ASCII_MIN);
            if (decalage_message < ASCII_MIN) {
                decalage_message = ASCII_MAX - (ASCII_MIN - decalage_message);
            }

            // rajouter le message décaler au message finale
            out = out + String.valueOf((char) decalage_message);

            pos_message++;
            pos_cle++;
            if (pos_cle >= cle.length())
                pos_cle = 0;
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
