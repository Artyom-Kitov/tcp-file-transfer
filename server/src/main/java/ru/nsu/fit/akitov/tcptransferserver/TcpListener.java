package ru.nsu.fit.akitov.tcptransferserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TcpListener {

    private final int port;
    private ServerSocket socket;

    private List<Thread> clients = new ArrayList<>();

    public TcpListener(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        socket = new ServerSocket(port);
        while (true) {
            Socket client = socket.accept();
            Thread thread = new Thread(new TcpWorker(client));
            thread.start();
            clients.add(thread);
        }
    }

}
