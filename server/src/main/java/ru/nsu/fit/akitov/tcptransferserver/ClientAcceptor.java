package ru.nsu.fit.akitov.tcptransferserver;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.core.jmx.Server;
import org.apache.logging.log4j.core.net.SocketAddress;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Log4j2
public class ClientAcceptor {

    private final String[] args;

    public ClientAcceptor(String[] args) {
        this.args = args;
    }

    public void start() {
        int port;
        try {
            port = Arguments.buildFromArray(args).port();
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return;
        }

        startAcceptor(port);
    }

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
