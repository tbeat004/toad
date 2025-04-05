package toad.core;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "shell", description = "Launches the interactive Toad shell")
public class ToadShellCommand implements Callable<Integer> {
    @Override
    public Integer call() {
        ToadShell.start();
        return 0;
    }
}
