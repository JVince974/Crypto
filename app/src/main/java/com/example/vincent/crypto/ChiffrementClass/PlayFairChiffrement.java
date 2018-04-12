package com.example.vincent.crypto.ChiffrementClass;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Vincent on 07/04/2018.
 */

public class PlayFairChiffrement {
    private static final String TAG = "PlayFairChiffrement";


    final static String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
    final static String lettreInutile = "Q";
    static char[][] tableauPlayFair;


    /**
     * Décrypte. fonction accessible depuis le publique
     *
     * @param cle
     * @param message
     */
    public static String d(String cle, String message) {
        String out = ""; // message de sortie
        ArrayList<String> splittedMessage = prepare(cle, message);

        // crypter chaque mot
        for (String morceauMessage : splittedMessage) {
            out = out + decryptMessage(morceauMessage);
        }

        return out;
    }

    /**
     * Crypte. fonction accessible depuis le publique
     *
     * @param cle
     * @param message
     */
    public static String c(String cle, String message) {
        String out = ""; // message de sortie
        ArrayList<String> splittedMessage = prepare(cle, message);


        // crypter chaque mot
        for (String morceauMessage : splittedMessage) {
            out = out + cryptMessage(morceauMessage);
        }

        return out;
    }









    /*
     ******************************
     *
     *  CRYPTER DECRYPTER FONCTION PRIVEE
     *
     ******************************
     */

    private static ArrayList<String> prepare(String cle, String message) {
        cle = cle.toUpperCase().replace(" ", "");
        message = message.toUpperCase();
        // variable pour le message crypté


        // Affichage des logs
        System.out.println("--------------------------------------------------");
        System.out.println("cle = " + cle + "\n"
                + "message = " + message);

        // Nettoyer le message
        message = separerCaracteresConsecutifs(message).replaceAll(" ", "");
        System.out.println("message = " + message);

        // créer le tableau PlayFair à partir de la clé
        tableauPlayFair = getTableauPlayfair(cle);
        System.out.println("--------------------------------------------------");
        System.out.println("Tableau FairPlay");
        System.out.println(Arrays.toString(tableauPlayFair[0]));
        System.out.println(Arrays.toString(tableauPlayFair[1]));
        System.out.println(Arrays.toString(tableauPlayFair[2]));
        System.out.println(Arrays.toString(tableauPlayFair[3]));
        System.out.println(Arrays.toString(tableauPlayFair[4]));
        System.out.println("--------------------------------------------------");

        // couper le message par mot 2 à 2
        ArrayList<String> splittedMessage = couperMessage2a2(message);
        System.out.println("Message coupé 2 à 2 :");
        System.out.println(splittedMessage.toString());
        System.out.println("--------------------------------------------------");

        return splittedMessage;
    }


    /**
     * Crypt le message selon l'algorithme de playFair
     */
    private static String cryptMessage(String message) {
        // récupérer chaque lettre du message
        String[] messageSplited = message.split("");
        String letter1 = messageSplited[1];
        String letter2 = messageSplited[2];

        // chercher la position de chaque lettre dans le tableau de FairPlay
        int[] pos1 = searchPositionTableauFairPlay(letter1);
        int[] pos2 = searchPositionTableauFairPlay(letter2);

        String messageCrypted = ""; // le message crypté à retourné

        /*
         * La suite de l'algo de chiffrement sur ce site
         * http://www.bibmath.net/crypto/index.php?action=affiche&quoi=ancienne/playfair
         */

        /*
         * si les deux lettres sont sur des lignes et des colonnes différentes,
         * alors chaque lettre est remplacée par la lettre située sur la même ligne qu'elle,
         * mais sur la colonne de l'autre ligne. Par exemple, avec le carré précédent, IL est chiffré en QM.
         */
        if (pos1[0] != pos2[0] && pos1[1] != pos2[1]) {
            // intervertir les colonnes
            int pos1Col = pos1[1];
            pos1[1] = pos2[1];
            pos2[1] = pos1Col;
            // Récupérer la lettre dans le tableauPlayFair
            messageCrypted = String.valueOf(tableauPlayFair[pos1[0]][pos1[1]]) + String.valueOf(tableauPlayFair[pos2[0]][pos2[1]]);
        }

        /*
         * si les deux lettres sont sur la même ligne,
         * alors on remplace chaque lettre par la lettre immédiatement à sa droite
         * (éventuellement, on revient à la première lettre de la ligne).
         */
        else if (pos1[0] == pos2[0]) {
            // décaler vers la droite
            pos1[1]++;
            pos2[1]++;
            // revenir à la première lettre de la ligne si on dépasse la dernière
            if (pos1[1] > 4) {
                pos1[1] = 0;
            }
            if (pos2[1] > 4) {
                pos2[1] = 0;
            }
            // Récupérer la lettre dans le tableauPlayFair
            messageCrypted = String.valueOf(tableauPlayFair[pos1[0]][pos1[1]]) + String.valueOf(tableauPlayFair[pos2[0]][pos2[1]]);
        }

        /*
         * si les deux lettres sont sur la même colonne,
         * alors on remplace chaque lettre par la lettre immédiatement sous elle
         * (éventuellement, on revient à la première lettre de la colonne).
         */
        else if (pos1[1] == pos2[1]) {
            // décaler vers le bas
            pos1[0]++;
            pos2[0]++;
            // revenir à la première lettre de la ligne si on dépasse la dernière
            if (pos1[0] > 4) {
                pos1[0] = 0;
            }
            if (pos2[0] > 4) {
                pos2[0] = 0;
            }
            // Récupérer la lettre dans le tableauPlayFair
            messageCrypted = String.valueOf(tableauPlayFair[pos1[0]][pos1[1]]) + String.valueOf(tableauPlayFair[pos2[0]][pos2[1]]);
        }

        return messageCrypted;
    }


    /**
     * Crypt le message selon l'algorithme de playFair
     */
    private static String decryptMessage(String message) {
        // récupérer chaque lettre du message
        String[] messageSplited = message.split("");
        String letter1 = messageSplited[1];
        String letter2 = messageSplited[2];

        // chercher la position de chaque lettre dans le tableau de FairPlay
        int[] pos1 = searchPositionTableauFairPlay(letter1);
        int[] pos2 = searchPositionTableauFairPlay(letter2);


        String messageCrypted = ""; // le message crypté à retourné

        /*
         * La suite de l'algo de chiffrement sur ce site
         * http://www.bibmath.net/crypto/index.php?action=affiche&quoi=ancienne/playfair
         */



        /*
         * si les deux lettres sont sur des lignes et des colonnes différentes,
         * alors chaque lettre est remplacée par la lettre située sur la même ligne qu'elle,
         * mais sur la colonne de l'autre ligne. Par exemple, avec le carré précédent, IL est chiffré en QM.
         */
        if (pos1[0] != pos2[0] && pos1[1] != pos2[1]) {
            // intervertir les colonnes
            int pos1Col = pos1[1];
            pos1[1] = pos2[1];
            pos2[1] = pos1Col;
            // Récupérer la lettre dans le tableauPlayFair
            messageCrypted = String.valueOf(tableauPlayFair[pos1[0]][pos1[1]]) + String.valueOf(tableauPlayFair[pos2[0]][pos2[1]]);
        }

        /*
         * si les deux lettres sont sur la même ligne,
         * alors on remplace chaque lettre par la lettre immédiatement à sa droite
         * (éventuellement, on revient à la première lettre de la ligne).
         */
        else if (pos1[0] == pos2[0]) {
            // décaler vers la gauche
            pos1[1]--;
            pos2[1]--;
            // revenir à la première lettre de la ligne si on dépasse la dernière
            if (pos1[1] < 0) {
                pos1[1] = 4;
            }
            if (pos2[1] < 0) {
                pos2[1] = 4;
            }
            // Récupérer la lettre dans le tableauPlayFair
            messageCrypted = String.valueOf(tableauPlayFair[pos1[0]][pos1[1]]) + String.valueOf(tableauPlayFair[pos2[0]][pos2[1]]);
        }

        /*
         * si les deux lettres sont sur la même colonne,
         * alors on remplace chaque lettre par la lettre immédiatement sous elle
         * (éventuellement, on revient à la première lettre de la colonne).
         */
        else if (pos1[1] == pos2[1]) {
            // décaler vers le haut
            pos1[0]--;
            pos2[0]--;
            // revenir à la première lettre de la ligne si on dépasse la dernière
            if (pos1[0] < 0) {
                pos1[0] = 4;
            }
            if (pos2[0] < 0) {
                pos2[0] = 4;
            }
            // Récupérer la lettre dans le tableauPlayFair
            messageCrypted = String.valueOf(tableauPlayFair[pos1[0]][pos1[1]]) + String.valueOf(tableauPlayFair[pos2[0]][pos2[1]]);
        }

        return messageCrypted;
    }










    /*
     ******************************
     *
     *  OUTILS
     *
     ******************************
     */


    /**
     * Fonction qui créer le tableau Fairplay
     *
     * @link http://www.bibmath.net/crypto/index.php?action=affiche&quoi=ancienne/playfair
     */
    public static char[][] getTableauPlayfair(String cle) {
        char[] carrePlayFair = enleverCharacterEnDouble(cle + alphabet).toCharArray();
        char[][] tableauPlayFair = new char[5][5];
        int row = 0;
        int col = 0;

        // parcourir le carre de haut en bas et écrire dans un tableau 5x5
        for (int i = 0; i < cle.length(); i++) {
            int j = i;
            while (j < carrePlayFair.length) {
                tableauPlayFair[row][col] = carrePlayFair[j];
                col++;
                // Passer à la ligne si on atteint la 5ème colonne
                if (col > 4) {
                    row++;
                    col = 0;
                }
                j += cle.length();
            }
        }

        return tableauPlayFair;
    }


    /**
     * Fonction qui enlève les charactères qui se répètent dans un mot
     * Fonction récursive
     */
    public static String enleverCharacterEnDouble(String word) {
        if (word.length() > 1) {
            String firstLetter = word.substring(0, 1);
            return firstLetter + enleverCharacterEnDouble(word.replace(firstLetter, ""));
        } else {
            return word;
        }
    }


    /**
     * Fonction qui sépare les caractères qui se suivent par d'autres caratères aléatoire
     */
    public static String separerCaracteresConsecutifs(String message) {
        if (message.length() > 1) {
            String firstLetter = message.substring(0, 1);
            String secondLetter = message.substring(1, 2);
            if (firstLetter.equals(secondLetter)) {
                return firstLetter + "Q" + separerCaracteresConsecutifs(message.substring(1));
            } else {
                return firstLetter + separerCaracteresConsecutifs(message.substring(1));
            }
        } else {
            return message;
        }
    }


    /**
     * Fonction qui coupe le message en réunissant les lettres 2 à 2
     */
    public static ArrayList<String> couperMessage2a2(String message) {
        ArrayList<String> splitMessage = new ArrayList<>();

        // tant que le message n'est pas coupé
        while (message.length() > 0) {
            // ajouté une lettre inutile s'il reste une derniere lettre seule
            if (message.length() == 1) {
                splitMessage.add(message + lettreInutile);
                break;
            }
            // sinon ajouter les 2 premiers charactères au tableau
            else {
                splitMessage.add(message.substring(0, 2));
                message = message.substring(2);
            }
        }

        return splitMessage;
    }


    /**
     * Cherche la position d'une lettre dans le tableau de FairPlay
     *
     * @return int[] la position de la lettre sous la forme [ligne, colonne]
     */
    private static int[] searchPositionTableauFairPlay(String letter) {
        int[] position = new int[0];
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (String.valueOf(tableauPlayFair[row][col]).equals(letter)) {
                    position = new int[]{row, col};
                }
            }
        }

        return position;
    }
}
