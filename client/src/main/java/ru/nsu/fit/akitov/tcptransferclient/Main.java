package ru.nsu.fit.akitov.tcptransferclient;

public class Main {

    public static void main(String[] args) {
        Arguments arguments;
        try {
            arguments = Arguments.buildFromArray(args);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + Utils.usage());
        }


    }

}