package ru.nsu.fit.akitov.tcptransferclient;

public class Main {

    public static void main(String[] args) {
        Runnable client = new Client(args);
        client.run();
    }

}