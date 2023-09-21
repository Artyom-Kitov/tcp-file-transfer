package ru.nsu.fit.akitov.tcptransferserver;

import java.net.Socket;

public class TcpWorker implements Runnable {

    Socket socket;

    public TcpWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println(socket.getInetAddress().getHostAddress());
    }
}
