package com.extensions.crashTest;

import java.util.Random;

public class CrashExtension {

    public CrashExtension() throws TireliDindonException {
            Random randomgenerator = randomgenerator = new Random();
            int i = randomgenerator.nextInt(1);
            if(i==0){
                throw new TireliDindonException();
            } else {
                System.out.println("Ca marche du tonnere");
            }
    }
}
