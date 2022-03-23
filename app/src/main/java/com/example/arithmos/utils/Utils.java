package com.example.arithmos.utils;

import java.util.Random;

public class Utils {

    private static final String[] nombre = {"zero", "un", "deux", "trois", "quatre", "cinq", "six",
            "sept", "huit", "neuf", "dix", "onze", "douze", "treize", "quatorze", "quinze",
            "seize", "dix-sept", "dix-huit", "dix-neuf"};

    public static int generateInteger(int min, int max) {
        return new Random().nextInt((max - min) + 1 ) + min;
    }

    public static String convertIntToStringMillier(int num) {
        String word = "";
        int rem = num / 1000;
        int mod = num % 1000;
        if(rem == 1) {
            word = "mille ";
        } else if (rem > 0) {
            word = nombre[rem] + " milles";
            if (mod > 0) {
                word = word + " ";
            }
        }

        if (mod > 0) {
            word = word + convertIntToStringCentaine(mod);
        }
        return word;
    }

    public static String convertIntToStringCentaine(int num) {
        String word = "";
        int rem = num / 100;
        int mod = num % 100;
        if(rem == 1) {
            word = "cents ";
        } else if (rem > 0) {
            word = nombre[rem] + " cents";
            if (mod > 0) {
                word = word + " ";
            }
        }

        if (mod > 0) {
            word = word + convertIntToStringDizaine(mod);
        }
        return word;
    }

    private static String convertIntToStringDizaine(int num) {
        String[] dizaine = {"vingt", "trente", "quarante", "cinquante", "soixante",
                "soixante dix", "quatre vingt", "quatre vingt dix"};

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
                        if (num > 70 && num < 80) {
                            return dizaine[v-1] + "-" + nombre[(num % 10 + 10)];
                        } else if(num > 90) {
                            return "quatre-vint" + "-" + nombre[(num % 10 + 10)];
                        }
                        else {
                            return dcap + "-" + nombre[num % 10];
                        }

                    return dcap;
                }
            }
        }
        return dcap;
    }
}
