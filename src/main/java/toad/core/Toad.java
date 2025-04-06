package toad.core;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import toad.commands.ToolCommand;

@Command(name = "toad", mixinStandardHelpOptions = true, version = "Toad v0.1.1",
        description = "Custom CLI tool with subcommands",
        subcommands = {
                ToadShellCommand.class,
                ToolCommand.class
          }
        )


public class Toad implements Runnable {
    @Override
    public void run() {
        System.out.println("Did you really expect that to work? -type 'toad -help' to see the list of available commands");
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Toad()).execute(args);
        System.exit(exitCode);
    }
}
