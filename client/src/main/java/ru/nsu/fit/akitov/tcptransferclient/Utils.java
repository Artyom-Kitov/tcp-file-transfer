package ru.nsu.fit.akitov.tcptransferclient;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

    public static String usage() {
        return """
                usage: tcp-transfer-client <filename> <server address> <server port>
                """;
    }

}
