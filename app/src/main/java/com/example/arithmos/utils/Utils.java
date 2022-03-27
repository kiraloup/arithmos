package com.example.arithmos.utils;

import android.util.Log;

import java.util.Random;

public class Utils {

    private static final String[] nombre = {"zero", "un", "deux", "trois", "quatre", "cinq", "six",
            "sept", "huit", "neuf", "dix", "onze", "douze", "treize", "quatorze", "quinze",
            "seize", "dix sept", "dix huit", "dix neuf"};

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
                            return dizaine[v-1] + " " + nombre[(num % 10 + 10)];
                        } else if(num > 90) {
                            return "quatre vint" + " " + nombre[(num % 10 + 10)];
                        }
                        else {
                            return dcap + " " + nombre[num % 10];
                        }

                    return dcap;
                }
            }
        }
        return dcap;
    }


    /**
     *
     * @param TypeOfimages should in ascending order since we want the lowest number of images
     * @param NumberOfimages should be a fix number of images for each type
     * @param value the actual value that need to be broken down
     * @return an array that contains how many image of each type do we need
     */
    public static int[] findNumberOfimage(int[] TypeOfimages, int[] NumberOfimages, int value) {
        int i;
        int[] res = new int[NumberOfimages.length];

        for(i = 0; i < TypeOfimages.length; i++) {
            //TODO : rewrite this stupid comment
            //value >= TypeOfimages[i] is to check decremente the number of images
            //until the value is greater that the "coin" at place i
            while(value >= TypeOfimages[i] && NumberOfimages[i] > 0 && value >= 0) {
                //decremente the value since we select the image
                value -= TypeOfimages[i];
                Log.d("Utils", value + " is decremente by " + TypeOfimages[i]);

                // we check how many images of this type we can still display
                //we decremente this value
                NumberOfimages[i] = NumberOfimages[i] - 1;

                //now we incremente the result for this specific images
                //since we select it
                res[i]  = res[i] + 1;
            }
        }

        return res;
    }
}
