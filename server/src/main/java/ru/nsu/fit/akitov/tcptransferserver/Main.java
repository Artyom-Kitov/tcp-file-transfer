package ru.nsu.fit.akitov.tcptransferserver;

public class Main {

    public static void main(String[] args) {
        Runnable clientAcceptor = new ConnectionListener(args);
        clientAcceptor.run();
    }

}