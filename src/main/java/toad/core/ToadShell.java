package toad.core;

import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import picocli.CommandLine;
import picocli.shell.jline3.PicocliJLineCompleter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.fusesource.jansi.Ansi.ansi;

public class ToadShell {

    public static int start() {
        AnsiConsole.systemInstall();

        CommandLine cmd = new CommandLine(new toad.core.Toad());

        Terminal terminal;
        LineReader reader;
        DefaultHistory history;

        Path toadCliDir;
        try {
            toadCliDir = Paths.get(
                    ToadShell.class
                            .getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI()
            ).getParent();
        } catch (URISyntaxException e) {
            System.out.println(ansi().fgRed().a("❌ Could not resolve toadCLI path: ").a(e.getMessage()).reset());
            return 1;
        }

        Path historyPath = toadCliDir.resolve(".toad_history");

        try {
            terminal = TerminalBuilder.builder()
                    .system(true)
                    .build();

            reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new PicocliJLineCompleter(cmd.getCommandSpec()))
                    .build();

            reader.setVariable(LineReader.HISTORY_FILE, historyPath);
            history = (DefaultHistory) reader.getHistory();
            history.load();

        } catch (IOException e) {
            System.out.println(ansi().fgRed().a("❌ Failed to initialize shell: ").a(e.getMessage()).reset());
            return 1;
        }

        String prompt = ansi().fgBrightCyan().a("Toad 🐸 > ").reset().toString();

        System.out.println(ansi().fgBrightGreen().a("Welcome to Toad Shell!").reset());

        while (true) {
            String line;
            try {
                line = reader.readLine(prompt);
            } catch (Exception e) {
                break;
            }

            if (line == null || line.trim().equalsIgnoreCase("exit")) break;

            cmd.execute(line.split("\\s+"));
        }

        System.out.println(ansi().fgYellow().a("Goodbye! 🪷").reset());

        AnsiConsole.systemUninstall();
        return 0;
    }
}
