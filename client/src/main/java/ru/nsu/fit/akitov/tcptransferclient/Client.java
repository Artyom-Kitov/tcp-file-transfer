package ru.nsu.fit.akitov.tcptransferclient;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.cli.ParseException;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

@Log4j2
public class Client {

    private static final int BUFFER_SIZE = 8192;

    private final String[] args;
    private Socket socket;

    public Client(String[] args) {
        this.args = args;
    }

    public void start() {
        Arguments arguments;
        try {
            arguments = Arguments.buildFromArray(args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return;
        }

        try {
            socket = new Socket(arguments.serverAddress(), arguments.serverPort());
        } catch (IOException e) {
            log.error(e.getMessage());
            return;
        }

        transfer(arguments.fileName());

        try {
            socket.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void transfer(Path fileName) {
        try {
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF(fileName.getFileName().toString());
            output.writeLong(Files.size(fileName));
            transferFileContent(fileName, output);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void transferFileContent(Path fileName, OutputStream output) {
        byte[] buffer = new byte[BUFFER_SIZE];
        try (FileInputStream input = new FileInputStream(fileName.toFile())) {
            for (int size = input.read(buffer); size != -1; size = input.read(buffer)) {
                output.write(buffer, 0, size);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
