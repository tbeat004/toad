package toad.core;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "toad", mixinStandardHelpOptions = true, version = "Toad v0.1.0",
        description = "Custom CLI tool with subcommands")


public class Toad implements Runnable {
    @Override
    public void run() {
        System.out.println("Do something idk");
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Toad()).execute(args);
        System.exit(exitCode);
    }
}

