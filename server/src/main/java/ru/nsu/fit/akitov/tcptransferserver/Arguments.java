package ru.nsu.fit.akitov.tcptransferserver;

import org.apache.commons.cli.*;
import lombok.Builder;

@Builder
public record Arguments(int port) {

    public static Arguments buildFromArray(String[] args) throws ParseException {
        Options options = new Options();

        Option portOption = Option.builder()
                .argName("p")
                .longOpt("port")
                .hasArg(true)
                .desc("server port")
                .required()
                .build();
        options.addOption(portOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        int port;
        try {
            port = Integer.parseInt(cmd.getOptionValue("port"));
        } catch (NumberFormatException e) {
            throw new ParseException("invalid port");
        }

        return Arguments.builder()
                .port(port)
                .build();
    }

}
