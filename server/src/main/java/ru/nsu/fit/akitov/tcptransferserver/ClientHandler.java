package ru.nsu.fit.akitov.tcptransferserver;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
public class ClientHandler implements Runnable {

    private static final Path UPLOAD_PATH = Path.of("uploads");
    private static final int BUFFER_SIZE = 8192;

    Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        log.info(socket.getInetAddress().getHostAddress() + " connected");
        if (!resolveUploadDirectory()) {
            return;
        }

        upload();

        try {
            socket.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info(socket.getInetAddress().getHostAddress() + " disconnected");
    }

    private boolean resolveUploadDirectory() {
        if (Files.exists(UPLOAD_PATH) && Files.isDirectory(UPLOAD_PATH)) {
            return true;
        }

        if (Files.exists(UPLOAD_PATH) && !Files.isDirectory(UPLOAD_PATH)) {
            try {
                Files.delete(UPLOAD_PATH);
            } catch (IOException e) {
                log.error(e.getMessage());
                return false;
            }
        }

        try {
            Files.createDirectory(UPLOAD_PATH);
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean resolveUploadFile(Path upload) {
        try {
            Files.deleteIfExists(upload);
            Files.createFile(upload);
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    private void upload() {
        try (DataInputStream stream = new DataInputStream(socket.getInputStream())) {
            String fileName = stream.readUTF();
            Path upload = UPLOAD_PATH.resolve(Path.of(fileName));
            if (!resolveUploadFile(upload)) {
                return;
            }

            long fileSize = stream.readLong();
            log.info("name: " + fileName + ", size: " + fileSize);

            loadFileContent(upload, stream);
            log.info(fileName + " from " + socket.getInetAddress().getHostAddress() + " successfully uploaded");
        } catch (IOException e) {
            log.error(socket.getInetAddress().getHostAddress() + ": " + e.getMessage());
        }
    }

    private void loadFileContent(Path upload, InputStream stream) throws IOException {
        try (FileOutputStream output = new FileOutputStream(upload.toFile())) {
            byte[] buffer = new byte[BUFFER_SIZE];
            for (int size = stream.read(buffer); size != -1; size = stream.read(buffer)) {
                output.write(buffer, 0, size);
            }
        } catch (IOException e) {
            Files.delete(upload);
            log.error(e.getMessage());
        }
    }

}
