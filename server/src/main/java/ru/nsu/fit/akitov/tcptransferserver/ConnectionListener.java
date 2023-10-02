package ru.nsu.fit.akitov.tcptransferserver;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Log4j2
public class ConnectionListener implements Runnable {

    private final String[] args;

    public ConnectionListener(String[] args) {
        this.args = args;
    }

    @Override
    public void run() {
        int port;
        try {
            port = Arguments.buildFromArray(args).port();
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return;
        }

        startAcceptor(port);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void startAcceptor(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            log.info("Started at " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
            while (true) {
                Socket clientSocket = socket.accept();
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

}
