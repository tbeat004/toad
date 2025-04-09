package toad.commands.tools;

import picocli.CommandLine.*;
import toad.core.Toad;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Command(
        name = "ytdl",
        description = "Download YouTube videos via yt-dlp",
        mixinStandardHelpOptions = true
)
public class YtdlTool implements Callable<Integer> {

    @Unmatched
    List<String> args = new ArrayList<>();

    @Option(names = "--update", description = "Update yt-dlp to the latest stable version")
    boolean update;

    @Override
    public Integer call() throws Exception {
        String os = System.getProperty("os.name").toLowerCase();
        String ytDlpPath = getytPath(os);


        File binary = new File(ytDlpPath);
        if (!binary.exists()) {
            System.out.println("yt-dlp binary not found at: " + ytDlpPath);
            return 1;
        }

        if (update) {
            List<String> updateCmd = List.of(ytDlpPath, "-U");
            ProcessBuilder updater = new ProcessBuilder(updateCmd);
            updater.inheritIO();
            Process updateProcess = updater.start();
            updateProcess.waitFor();
            return 0;
        }

        List<String> command = new ArrayList<>();
        command.add(ytDlpPath);
        command.addAll(args);

        if (args.isEmpty()) {
            System.out.println("   No arguments provided. Try:");
            System.out.println("   toad tool ytdl --update");
            System.out.println("   toad tool ytdl [yt-dlp arguments]");
            return 1;
        }

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.inheritIO();
        Process process = pb.start();
        return process.waitFor();
    }

    private static String getytPath(String os) throws URISyntaxException {
        String jarPath = new File(Toad.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()).getParent();
        boolean isWindows = os.contains("win");
        boolean isMac = os.contains("mac");

        String osFolder = jarPath + File.separator + "bin" + File.separator +
                (isWindows ? "windows" : isMac ? "macOS"  : "linux" );

        String ytDlpPath = osFolder + File.separator +
                (isWindows ? "yt-dlp.exe" : isMac ? "yt-dlp_macos" : "yt-dlp_linux");
        return ytDlpPath;
    }
}
