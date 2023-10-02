package ru.nsu.fit.akitov.tcptransferserver;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

@Log4j2
public class ClientHandler implements Runnable {

    private static final Path UPLOAD_PATH = Path.of("uploads");
    private static final int SPEED_MEASURE_INTERVAL_MILLIS = 3000;
    private static final int BUFFER_SIZE = 8192;

    private final Socket socket;
    private final String host;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.host = socket.getInetAddress().getHostAddress();
    }

    @Override
    public void run() {
        log.info(host + " connected");
        if (!resolveUploadDirectory()) {
            return;
        }

        upload();

        try {
            socket.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info(host + " disconnected");
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
            log.info(host + " name=" + fileName + ", size=" + fileSize);

            loadFileContent(upload, stream, fileSize);
            log.info(host + " " + fileName + " successfully uploaded");
        } catch (IOException e) {
            log.error(host + " " + e.getMessage());
        }
    }

    private void loadFileContent(Path upload, InputStream stream, long bytesExpected) throws IOException {
        long totalBytesReceived = 0;
        long start = System.currentTimeMillis();
        long intervalTimestamp = System.currentTimeMillis();
        long intervalBytesReceived = 0;

        try (FileOutputStream output = new FileOutputStream(upload.toFile())) {
            byte[] buffer = new byte[BUFFER_SIZE];
            for (int size = stream.read(buffer); size != -1; size = stream.read(buffer)) {
                output.write(buffer, 0, size);
                totalBytesReceived += size;
                intervalBytesReceived += size;

                long deltaT = System.currentTimeMillis() - intervalTimestamp;
                if (deltaT >= SPEED_MEASURE_INTERVAL_MILLIS) {
                    log.info(host + " current speed: " + intervalBytesReceived * 1000 / deltaT + " bytes/s");
                    intervalBytesReceived = 0;
                    intervalTimestamp = System.currentTimeMillis();
                }
            }
            if (totalBytesReceived != bytesExpected) {
                throw new IOException(host + " bytes expected: " + bytesExpected + ", bytes received: " + totalBytesReceived);
            }
        } catch (IOException e) {
            Files.delete(upload);
            log.error(host + ": " + e.getMessage());
            return;
        }

        long elapsedMillis = System.currentTimeMillis() - start;
        log.info(host + " average speed: " + totalBytesReceived * 1000 / elapsedMillis + " bytes/s");
    }

}
