package ru.nsu.fit.akitov.tcptransferclient;

import lombok.Builder;
import org.apache.commons.cli.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;

@Builder
public record Arguments(Path fileName, InetAddress serverAddress, int serverPort) {

    public static Arguments buildFromArray(String[] args) throws ParseException {
        Options options = new Options();

        Option filePathOption = Option.builder()
                .argName("f")
                .longOpt("file")
                .desc("file to upload")
                .hasArg(true)
                .required()
                .build();

        Option serverAddressOption = Option.builder()
                .argName("h")
                .longOpt("host")
                .desc("server address")
                .hasArg(true)
                .required()
                .build();

        Option serverPortOption = Option.builder()
                .argName("p")
                .longOpt("port")
                .desc("server port")
                .hasArg(true)
                .required()
                .build();

        options.addOption(filePathOption)
                .addOption(serverAddressOption)
                .addOption(serverPortOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        Path path = Path.of(cmd.getOptionValue("file"));
        InetAddress serverAddress;
        int serverPort;
        try {
            serverAddress = InetAddress.getByName(cmd.getOptionValue("host"));
            serverPort = Integer.parseInt(cmd.getOptionValue("port"));
        } catch (NumberFormatException e) {
            throw new ParseException("invalid server port");
        } catch (UnknownHostException e) {
            throw new ParseException("invalid server address");
        }

        return Arguments.builder()
                .fileName(path)
                .serverAddress(serverAddress)
                .serverPort(serverPort)
                .build();
    }

}
