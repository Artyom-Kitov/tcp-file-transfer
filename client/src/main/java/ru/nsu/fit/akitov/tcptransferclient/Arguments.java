package ru.nsu.fit.akitov.tcptransferclient;

import lombok.Builder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;

@Builder
public record Arguments(Path fileName, InetAddress serverAddress, int serverPort) {

    public static Arguments buildFromArray(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("wrong number of parameters");
        }
        try {
            Path path = Path.of(args[0]);
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("file does not exist");
            }
            InetAddress address = InetAddress.getByName(args[1]);
            int port = Integer.parseInt(args[2]);
            return Arguments.builder()
                    .fileName(path)
                    .serverAddress(address)
                    .serverPort(port)
                    .build();
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("invalid server address");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("invalid server port");
        }
    }

}
