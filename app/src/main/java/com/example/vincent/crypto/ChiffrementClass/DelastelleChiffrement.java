package com.example.vincent.crypto.ChiffrementClass;

/**
 * Created by Vincent on 09/04/2018.
 */

public class DelastelleChiffrement {


    static String clef;
    static String message;
    static char[] clefChar;
    static int n;
    static char[][] grille;
    static char[][] tabMSG;
    static int nbrBLOK;
    static char[] messageCHAR;


    public static String main(String[] args) {
        System.out.println("clef, message, n, c|d ");

        int i = 0;

        clef = args[0];
        message = args[1];
        n = Integer.parseInt(args[2]);
        String fonction = args[3];
        int tailleBLOK = n;

        clef = clef.replace("i", "j");
        clef = clear(clef);
        clefChar = clef.toCharArray();

        message = message.toLowerCase();
        message = message.replace(" ", "");//msg ==> enlever espaces
        message = message.replace("i", "j");//msg ==> remplacer i par j

        while ((message.length() % n) != 0) {//on complète le msg pour avoir un taille multiple de la taille des block (=n)
            message += "z";
        }

        nbrBLOK = message.length() / n;

        messageCHAR = message.toCharArray();

        int[] alphabet_deja_utiliser = new int[26]; // initialise le tableau a 0
        for (char c : clefChar) {
            alphabet_deja_utiliser[c - 97] = 1;
        }


        //construcion grille================================================================================
        grille = new char[5][5];
        i = 0;
        for (char c : clefChar) {
            System.out.println("grille[" + i / 5 + "] [" + i % 5 + "] = " + c);
            grille[i / 5][i % 5] = c;
            i++;
        }
        for (int j = 97; j < 97 + 26; j++) { // on parcours l'alphabet
            if (j == 105) {//  On confond la lettre "i"=105 ==> on reste au meme endroit mais on avance dans l'alphabet
                i--;
            } else if (alphabet_deja_utiliser[j - 97] == 0) { //si la lettre courante de l'alphabet pas encore use
                grille[(i + j - 97) / 5][(i + j - 97) % 5] = (char) (j);
            } else { // si deja use ==> on reste au meme endroit mais on avance dans l'alphabet
                i--;
            }
        }
        for (i = 0; i < 25; i++) {
            System.out.println("grilleFINAL[" + i / 5 + "] [" + i % 5 + "] = " + grille[i / 5][i % 5]);
        }
        //FIN construcion grille================================================================================


        System.out.println("msg (apres rajout)==>" + message);

        if (fonction.equals("d")) { //decryptage
            System.out.println(decrypter());
            return decrypter();
        } else if (fonction.equals("c")) { //cryptage
            System.out.println(crypter());
            return crypter();
        } else {
            System.out.println("commande non reconnu");
            return "commande non reconnu";
        }
    }


    public static String crypter() {
        String result = "";

        for (int i = 0; i <= nbrBLOK - 1; i++) {// on convertit BLOCK par BLOCK
            result += conversion(i);
        }

        return result;
    }


    public static String decrypter() {
        String result = "";

        for (int i = 0; i <= nbrBLOK - 1; i++) {// on convertit BLOCK par BLOCK
            result += DEconversion(i);
        }

        return result;
    }


    private static String DEconversion(int i) {
        String result = "";

        int[] tabCoord = new int[n];
        int k;
        for (k = 0; k <= n - 1; k++) { // on stock les coordonnees des characteres du message a dechiffrer ==> tabCoord
            int pos = getPos(messageCHAR[k + (i * n)]);
            tabCoord[k] = (pos / 5) * 10 + pos % 5;
        }
        int[] haut = new int[n + 1];
        int[] bas = new int[n + 1];
        for (k = 0; k <= (n / 2) - 1; k++) {// on creer la tableau du "haut" (plus tard fusione avec "bas" pour retrouver les characteres de-chiffrés)
            haut[k * 2] = tabCoord[k] / 10;
            haut[(k * 2) + 1] = tabCoord[k] % 10;
        }
        k--;
        if (n % 2 != 0) {// si la taille des BLOCK est un nombre impaire de characteres ==> "haut" & "bas" partagent une meme coordonee
            haut[(k * 2) + 2] = tabCoord[(n / 2)] / 10;
            bas[0] = tabCoord[(n / 2)] % 10;
            for (k = 1; k <= n / 2; k++) {//si la taille des BLOCK est un nombre impaire de characteres ==> bas a n/2 +1 cases au lieu de n/2
                bas[(k * 2) - 1] = tabCoord[(n / 2) + k] / 10;
                bas[k * 2] = tabCoord[(n / 2) + k] % 10;
            }
        } else {//si la taille des BLOCK est un nombre paire de characteres ==> "haut" & "bas" ne partagent pas une meme coordone
            for (k = 0; k <= n / 2 - 1; k++) {
                bas[k * 2] = tabCoord[(n / 2) + k] / 10;
                bas[(k * 2) + 1] = tabCoord[(n / 2) + k] % 10;
            }
        }
        for (k = 0; k <= n - 1; k++) {
            result += grille[haut[k]][bas[k]];//on prend le charactere de-crypter en regardant sa position dans la grille grace aux ligne "haut et "bas"
        }

        return result;
    }


    private static String conversion(int i) {
        String result = "";

        char charCourant;
        int[] total = new int[n * 2];
        for (int k = 0; k <= (n - 1); k++) {
            charCourant = messageCHAR[k + (i * n)]; // = char du msg
            int pos = getPos(charCourant);
            total[k] = pos / 5; //ligne du HAUT
            total[n + k] = pos % 5; //ligne du BAS
        }
        for (int k = 0; k <= (n * 2) - 2; k += 2) {
            result += grille[total[k]][total[k + 1]];
        }

        return result;
    }


    private static int getPos(char charCourant) {// retourne le num de la position de la lettre
        for (int i = 0; i <= 24; i++) {
            if (charCourant == grille[i / 5][i % 5])
                return i;
        }
        System.out.println("Erreur : " + charCourant);
        return 0;
    }


    public static String clear(String key) {
        if (key.length() > 1) {
            String firstLetter = key.substring(0, 1);
            return firstLetter + clear(key.replace(firstLetter, ""));
        } else {
            return key;
        }
    }


}
