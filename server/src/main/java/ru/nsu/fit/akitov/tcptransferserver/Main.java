package ru.nsu.fit.akitov.tcptransferserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            return;
        }
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("invalid port number");
            return;
        }

        System.out.println("ip: " + InetAddress.getLocalHost().getHostAddress());
        new TcpListener(port).start();
    }

}