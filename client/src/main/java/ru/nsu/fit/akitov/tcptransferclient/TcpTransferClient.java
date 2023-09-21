package ru.nsu.fit.akitov.tcptransferclient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TcpTransferClient {

    private InetAddress serverAddress;
    private int serverPort;
    private Socket clientSocket;

    public TcpTransferClient(InetAddress serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void start() throws IOException {
        clientSocket = new Socket(serverAddress, serverPort);
    }

}
