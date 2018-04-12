package com.example.vincent.crypto.ChiffrementClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vincent on 07/04/2018.
 */


/**
 * http://www.bibmath.net/crypto/index.php?action=affiche&quoi=ancienne/transposition
 */
public class RectangulaireChiffrement {

    final static int ASCII_MIN = 32;
    final static int ASCII_MAX = 126;

    // contient le tableau rectangulaire
    private static ArrayList<ArrayList> tableauRectangulaire;

    /**
     * Crypter
     */
    public static String crypt(String cle, String message) {
        System.out.println("------------------CRYPTAGE------------------------");
        // message a retourner
        String out = "";

        // tableau rectangulaire
        ArrayList<ArrayList> tableauRectangulaire = new ArrayList<>();
        int nbCol = cle.length(); // nb de colonnes dans le tableau
        // Ajouter la clé dans le tableau
        tableauRectangulaire.add(new ArrayList<>(Arrays.asList(cle.split(""))));
        tableauRectangulaire.add(trierCle(cle)); // trier la clé selon l'ordre alphabétique


        /********************
         *
         * Phase composition du tableau
         *
         ********************/
        // ajouter chaque morceau de mots dans la phrase
        char[] messageCoupe = message.toCharArray();
        ArrayList ligne = new ArrayList();
        for (char ch : messageCoupe) {
            // passer à la ligne
            if (ligne.size() == cle.length()) {
                tableauRectangulaire.add(ligne);
                ligne = new ArrayList();
            }
            ligne.add(ch);
        }

        // ajouter la dernière ligne si elle n'est pas vide
        if (!ligne.isEmpty()) {
            tableauRectangulaire.add(ligne);
        }

        // Afficher le tableau
        for (int i = 0; i < tableauRectangulaire.size(); i++) {
            System.out.println(tableauRectangulaire.get(i));
        }


        /********************
         *
         * Phase cryptage
         *
         ********************/
        ArrayList ordreCryptage = tableauRectangulaire.get(1);
        List<ArrayList> tableauMessage = tableauRectangulaire.subList(2, tableauRectangulaire.size());
        int ordreColRecherche = 1;

        // tant qu'on a pas atteint la dernire position
        while (ordreColRecherche < ordreCryptage.size()) {
            for (int colActuelle = 0; colActuelle < ordreCryptage.size(); colActuelle++) {
                if ((int) ordreCryptage.get(colActuelle) == ordreColRecherche) {
                    for (ArrayList ligneMessage : tableauMessage) {
                        try {
                            out = out + ligneMessage.get(colActuelle);
                        } catch (IndexOutOfBoundsException e) {
                        }
                    }
                    ordreColRecherche++;
                }
            }
        }


        return out;
    }


    /**
     * Decrypter
     */
    public static String decrypt(String cle, String message) {
        System.out.println("------------------DECRYPTAGE------------------------");
        // message a retourner
        String out = "";
        // tableau rectangulaire
        ArrayList<ArrayList> tableauRectangulaire = new ArrayList<>();
        int nbCol = cle.length(); // nb de colonnes dans le tableau
        // Ajouter la clé dans le tableau
        tableauRectangulaire.add(new ArrayList<>(Arrays.asList(cle.split(""))));
        tableauRectangulaire.add(trierCle(cle)); // trier la clé selon l'ordre alphabétique


        /********************
         *
         * Phase composition du tableau
         *
         ********************/
        // ajouter chaque morceau de mots dans la phrase
        char[] messageCoupe = message.toCharArray();
        int totalLettres = messageCoupe.length;

        // créer le tableau dynamiquement avec des lignes a colonne dynamiques
        while (totalLettres > 0) {
            if (totalLettres > cle.length())
                // tableauRectangulaire.add(new ArrayList<String>(cle.length()));
                tableauRectangulaire.add(new ArrayList<String>(Arrays.asList(new String[cle.length()])));
            else
                tableauRectangulaire.add(new ArrayList<String>(Arrays.asList(new String[totalLettres])));

            totalLettres -= cle.length();
        }


        ArrayList ordreCryptage = tableauRectangulaire.get(1);
        int currentLetter = 0;
        int ordreRechercheCol = 1;

        // tant qu'on a pas rempli le tableau avec toutes le message
        while (currentLetter < messageCoupe.length) {
            // tant qu'on a pas atteint l'odre des colonnes
            while (ordreRechercheCol < ordreCryptage.size()) {
                for (int colActuelle = 0; colActuelle < ordreCryptage.size(); colActuelle++) {
                    // si on a trouvé la bonne colonne
                    if ((int) ordreCryptage.get(colActuelle) == ordreRechercheCol) {
                        // remplir chaque colonne de chaque ligne du tableau
                        for (int ligne = 2; ligne < tableauRectangulaire.size(); ligne++) {
                            try {
                                // remplir la case assossiée
                                tableauRectangulaire.get(ligne).set(colActuelle, messageCoupe[currentLetter]);
                                currentLetter++;
                            } catch (IndexOutOfBoundsException e) {
                                // System.out.println("ligne: " + ligne + ", col:" + colActuelle + " n'existe pas");
                            }
                        }
                        ordreRechercheCol++;
                    }
                }
            }
        }


        // Afficher le tableau
        for (int i = 0; i < tableauRectangulaire.size(); i++) {
            System.out.println(tableauRectangulaire.get(i));
        }


        /********************
         *
         * Phase décryptage
         *
         ********************/
        // remplir chaque colonne de chaque ligne du tableau
        for (int ligne = 2; ligne < tableauRectangulaire.size(); ligne++) {
            ArrayList currentLigne = tableauRectangulaire.get(ligne);
            for (int col = 0; col < currentLigne.size(); col++) {
                out = out + currentLigne.get(col);
            }
        }


        return out;
    }


    /**
     * Classe chaque lettre de la clé selon un ordre prédéfini
     */
    private static ArrayList trierCle(String cle) {
        ArrayList<Integer> cleTrier = new ArrayList<>();
        char[] cleToArray = cle.toCharArray();

        while (cleTrier.size() < cle.length()) {
            // comparer les lettres 2 à 2
            for (int i = 0; i < cle.length(); i++) {
                // position de la lettre actuelle
                int position = cle.length();
                int firstLetter = cleToArray[i];

                for (int j = 0; j < cle.length(); j++) {
                    if (i != j) {
                        int secondLetter = cleToArray[j];
                        if (firstLetter < secondLetter) {
                            position--;
                        } else if (firstLetter == secondLetter && i < j) {
                            position--;
                        }
                    }
                }
                cleTrier.add(position);
            }
        }

        return cleTrier;
    }

}
