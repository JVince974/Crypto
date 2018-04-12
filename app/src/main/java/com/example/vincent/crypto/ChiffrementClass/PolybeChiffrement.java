package com.example.vincent.crypto.ChiffrementClass;

import android.util.Log;

import java.text.Normalizer;
import java.util.ArrayList;

/**
 * Created by Vincent on 08/04/2018.
 */

public class PolybeChiffrement {
    private static final String TAG = "PolybeChiffrement";

    private static String[][] polybe = {
            {"a", "b", "c", "d", "e"},
            {"f", "g", "h", "ij", "k"},
            {"l", "m", "n", "o", "p"},
            {"q", "r", "s", "t", "u"},
            {"v", "w", "x", "y", "z"}
    };


    public static String crypt(String message) {
        message = removeAccents(message).toLowerCase();
        String out = "";
        boolean positionFounded; // booléen pour quitter les boucles quand char trouvé

        // Pour chaque caractère du message regarder sa position ligne-col dans le tableau de Polybe
        for (char ch : message.toCharArray()) {
            positionFounded = false;

            // chercher sa position dans le carré de Polybe
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    // Si la position a été trouvée ajouter au message crypter
                    if (polybe[row][col].indexOf(ch) != -1) {
                        out = out + (row + 1) + (col + 1);
                        positionFounded = true;
                    }
                    if (positionFounded) break;
                }
                if (positionFounded) break;
            }

            // Si position non trouvé dans polybe ajouté le caractère quand même
            // ex : les espaces
            if (!positionFounded) out = out + ch;
        }


        return out;
    }


    public static String decrypt(String message) throws Exception {
        message = removeAccents(message).toLowerCase();
        String out = "";
        String[] lesMots = message.split(" ");

        // Decrypter chaque mot de la phrase
        for (String unMot : lesMots) {
            // couper un mot en lettres et traduire chaque lettre
            ArrayList<String> lesLettres = couper2a2(unMot);
            for (String uneLettre : lesLettres) {
                String[] position = uneLettre.split("");
                try {
                    int row = Integer.parseInt(position[1]) - 1;
                    int col = Integer.parseInt(position[2]) - 1;
                    out = out + polybe[row][col];
                } catch (Exception e) {
                    Log.e(TAG, "decrypt: " + e.getMessage());
                    erreurCritique("  Impossible de décrypter ce mot : " + unMot);
                }
            }
            out = out + " ";
        }


        return out;
    }


    /********************************
     *
     *    OUTILS
     *
     /********************************/


    /**
     * Enlève les accents dans la phrase
     */
    private static String removeAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }


    /**
     * Coupe une phrase en réunissant les caractères 2 à 2
     * Attention le message doit être pair, sinon erreur critique
     */
    private static ArrayList<String> couper2a2(String message) throws Exception {
        ArrayList<String> tableauMessage = new ArrayList<>();

        if (message.length() % 2 != 0) {
            erreurCritique("La taille de ce message n'est pas paire : " + message);
        } else {
            while (message.length() > 0) {
                // ajouter les 2 premiers charactères au tableau
                tableauMessage.add(message.substring(0, 2));
                message = message.substring(2);
            }
        }

        return tableauMessage;
    }


    private static void erreurCritique(String erreur) throws Exception {
        throw new Exception("Erreur critique \n" + erreur);
    }

}
