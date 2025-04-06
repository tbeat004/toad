package toad.commands;

import picocli.CommandLine.Command;
import toad.commands.tools.*;

import java.util.concurrent.Callable;

import static org.fusesource.jansi.Ansi.ansi;

@Command(name = "tool", description = "A collection of tools Usage: tool tool <command>",
        subcommands = {
                HashTool.class


        })
public class ToolCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        System.out.println(ansi().fgBrightBlack().a("Runs 'toad tool <command>' - try 'hash'").reset());
        return 0;
    }
}

