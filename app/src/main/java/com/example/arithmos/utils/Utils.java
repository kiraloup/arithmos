package com.example.arithmos.utils;

import java.util.Random;

public class Utils {

    public static int generateInteger(int min, int max) {
        return new Random().nextInt((max - min) + 1 ) + min;
    }
}
