package ru.nsu.fit.akitov.tcptransferclient;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    private static String usage() {
        return """
                usage: tcp-transfer-client <filename> <server address> <server port>
                """;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println(usage());
            return;
        }

        if (!Files.exists(Path.of(args[0]))) {
            System.out.println("Error: file does not exist");
            return;
        }


    }

}