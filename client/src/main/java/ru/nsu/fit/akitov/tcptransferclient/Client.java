package ru.nsu.fit.akitov.tcptransferclient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Client {

    private InetAddress serverAddress;
    private int serverPort;
    private Socket clientSocket;

    public Client(InetAddress serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void transfer(Path filename) throws IOException {
        clientSocket = new Socket(serverAddress, serverPort);
        DataOutputStream stream = new DataOutputStream(clientSocket.getOutputStream());
        stream.write(filename.getFileName().toString().getBytes(StandardCharsets.UTF_8));
        stream.writeLong(Files.size(filename));
    }

}
