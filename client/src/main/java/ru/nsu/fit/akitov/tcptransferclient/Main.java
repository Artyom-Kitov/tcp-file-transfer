package ru.nsu.fit.akitov.tcptransferclient;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Arguments arguments;
        try {
            arguments = Arguments.buildFromArray(args);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + "\n" + Utils.usage());
            return;
        }

        new TcpTransferClient(arguments.serverAddress(), arguments.serverPort()).start();
    }

}