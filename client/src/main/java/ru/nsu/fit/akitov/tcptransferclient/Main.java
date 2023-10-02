package ru.nsu.fit.akitov.tcptransferclient;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Client client = new Client(args);
        client.start();
    }

}