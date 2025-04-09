package toad.commands.tools;

import picocli.CommandLine.*;
import toad.core.Config;
import toad.core.Toad;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Command(
        name = "ytdl",
        description = "Download YouTube videos via yt-dlp",
        mixinStandardHelpOptions = true,
        subcommands = {
                YtdlTool.Mp3Command.class,
                YtdlTool.Mp4Command.class
        }
)

public class YtdlTool implements Callable<Integer> {
    public static String ytDlpPath = "";
    @Unmatched
    List<String> args = new ArrayList<>();

    @Option(names = "--update", description = "Update yt-dlp to the latest stable version")
    boolean update;

    @Override
    public Integer call() throws Exception {
        if (update) {
            return runYtDlpCommand(List.of("-U"));
        }

        if (args.isEmpty()) {
            System.out.println("   No arguments provided. Try:");
            System.out.println("   toad tool ytdl --update");
            System.out.println("   toad tool ytdl mp3/mp4 --dir <folder> --save-dir (stores as default) --out <filename> <url>");
            System.out.println("   NOTE: all --flags are optional ");
            return 1;
        }

        return 0;
    }

    private Integer runYtDlpCommand(List<String> ytArgs) throws Exception {
        ytDlpPath = getytPath(System.getProperty("os.name").toLowerCase());
        File binary = new File(ytDlpPath);

        if (!binary.exists()) {
            System.out.println("yt-dlp binary not found at: " + ytDlpPath);
            return 1;
        }

        List<String> command = new ArrayList<>();
        command.add(ytDlpPath);
        command.addAll(ytArgs);

        ProcessBuilder pb = new ProcessBuilder(command).inheritIO();
        return pb.start().waitFor();
    }


    public static String getytPath(String os) throws URISyntaxException {
        String jarPath = new File(Toad.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()).getParent();
        boolean isWindows = os.contains("win");
        boolean isMac = os.contains("mac");

        String osFolder = jarPath + File.separator + "bin" + File.separator +
                (isWindows ? "windows" : isMac ? "macOS"  : "linux" );

        return osFolder + File.separator +
                (isWindows ? "yt-dlp.exe" : isMac ? "yt-dlp_macos" : "yt-dlp_linux");
    }

    @Command(name = "mp3", description = "Download audio from a video as MP3")
    static class Mp3Command implements Callable<Integer> {

        @Parameters(index = "0", description = "YouTube URL")
        String url;

        @Option(names = "--out", description = "Custom output filename (without extension)")
        String outputName;

        @Option(names = "--dir", description = "Output directory")
        File outputDir;

        @Option(names = "--save-dir", description = "Save the provided --dir as the default for mp3 files")
        boolean saveDir;

        private File resolveOutputDir() {
            if (outputDir != null) {
                if (!outputDir.exists()) {
                    boolean created = outputDir.mkdirs();
                    if (!created) {
                        System.err.println("Failed to create directory: " + outputDir.getAbsolutePath());
                        return new File(Config.Manager.get().default_mp3_dir); // or mp4
                    }
                } else if (!outputDir.isDirectory()) {
                    System.err.println("Provided --dir is not a directory: " + outputDir.getAbsolutePath());
                    return new File(Config.Manager.get().default_mp3_dir); // or mp4
                }

                if (saveDir) {
                    String path = outputDir.getAbsolutePath();
                    Config config = Config.Manager.get();
                    config.default_mp3_dir = path;
                    Config.Manager.save();
                    System.out.println("Default MP3 directory updated to: " + path);
                }

                return outputDir;
            }

            return new File(Config.Manager.get().default_mp3_dir);
        }


        public Integer call() throws Exception {
            File dir = resolveOutputDir();
            if (!dir.exists()) dir.mkdirs();

            String outPath = outputName != null
                    ? new File(dir, outputName + ".%(ext)s").getPath()
                    : new File(dir, "%(title)s.%(ext)s").getPath();

            String ytDlpPath;
            try {
                ytDlpPath = YtdlTool.getytPath(System.getProperty("os.name").toLowerCase());
            } catch (Exception e) {
                System.err.println("Failed to resolve yt-dlp path: " + e.getMessage());
                return 1;
            }

            List<String> command = List.of(
                    ytDlpPath, "-x", "--audio-format", "mp3", "-o", outPath, url
            );

            return new ProcessBuilder(command).inheritIO().start().waitFor();
        }
    }

    @Command(name = "mp4", description = "Download video as MP4")
    static class Mp4Command implements Callable<Integer> {

        @Parameters(index = "0", description = "YouTube URL")
        String url;

        @Option(names = "--out", description = "Custom output filename (without extension)")
        String outputName;

        @Option(names = "--dir", description = "Output directory")
        File outputDir;

        @Option(names = "--save-dir", description = "Save the provided --dir as the default for mp4 files")
        boolean saveDir;

        private File resolveOutputDir() {
            if (outputDir != null) {
                if (!outputDir.exists()) {
                    boolean created = outputDir.mkdirs();
                    if (!created) {
                        System.err.println("Failed to create directory: " + outputDir.getAbsolutePath());
                        return new File(Config.Manager.get().default_mp4_dir);
                    }
                } else if (!outputDir.isDirectory()) {
                    System.err.println("Provided --dir is not a directory: " + outputDir.getAbsolutePath());
                    return new File(Config.Manager.get().default_mp4_dir);
                }

                if (saveDir) {
                    String path = outputDir.getAbsolutePath();
                    Config config = Config.Manager.get();
                    config.default_mp4_dir = path;
                    Config.Manager.save();
                    System.out.println("Default MP4 directory updated to: " + path);
                }

                return outputDir;
            }

            return new File(Config.Manager.get().default_mp4_dir);
        }


        public Integer call() throws Exception {
            File dir = resolveOutputDir();
            if (!dir.exists()) dir.mkdirs();

            String outPath = outputName != null
                    ? new File(dir, outputName + ".%(ext)s").getPath()
                    : new File(dir, "%(title)s.%(ext)s").getPath();

            String ytDlpPath;
            try {
                ytDlpPath = YtdlTool.getytPath(System.getProperty("os.name").toLowerCase());
            } catch (Exception e) {
                System.err.println("Failed to resolve yt-dlp path: " + e.getMessage());
                return 1;
            }

            List<String> command = List.of(
                    ytDlpPath, "-x", "--audio-format", "mp3", "-o", outPath, url
            );

            return new ProcessBuilder(command).inheritIO().start().waitFor();
        }
    }
}
