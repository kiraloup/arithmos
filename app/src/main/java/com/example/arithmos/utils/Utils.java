package com.example.arithmos.utils;

import java.util.Random;

public class Utils {

    public static int generateInteger(int min, int max) {
        return new Random().nextInt((max - min) + 1 ) + min;
    }

    public static String convertIntToString(int num) {
        String[] dizaine = {"vingt", "trente", "quarante", "cinquante", "soixante",
                "soixante dix", "quatre vingt", "quatre vint dix"};

        String[] nombre = {"zero", "un", "deux", "trois", "quatre", "cinq", "six",
                "sept", "huit", "neuf", "dix", "onze", "douze", "treize", "quatorze", "quinze",
                "seize", "dix sept", "dix huit", "dix neuf"};

        String dcap = "";
        if(num < 20) {
            return dcap.concat(nombre[num]);
        }
        else {
            for (int v = 0; v < dizaine.length; v++) {
                dcap = dizaine[v];
                int dval = 20 + 10 * v;
                if (dval + 10 > num) {
                    if ((num % 10) != 0)
                        return dcap + " " + nombre[num % 10];
                    return dcap;
                }
            }
        }
        return dcap;
    }
}
