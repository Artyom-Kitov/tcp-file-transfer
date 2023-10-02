package ru.nsu.fit.akitov.tcptransferserver;

public class Main {

    public static void main(String[] args) {
        ClientAcceptor clientAcceptor = new ClientAcceptor(args);
        clientAcceptor.start();
    }

}