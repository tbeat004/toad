package toad.commands.tools;
import picocli.CommandLine.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;


@Command(name = "hash", description = "returns a hashed input string", mixinStandardHelpOptions = true)
public class HashTool implements Runnable {
    @Parameters(paramLabel = "<The string to hash>")

    private String input = null;
    @Option(names = "--algo", description = "Hash algorithm to use (MD5, SHA_1, SHA_256, SHA_512)", defaultValue = "SHA_256")
    HashAlgorithm algorithm;

    enum HashAlgorithm {
        MD5, SHA_1, SHA_256, SHA_512;

        public String toJavaName() {
            return name().replace("_", "-");
        }
    }

    @Override
    public void run() {
        String hexedInput = convertToHex(input);
        System.out.println("Hexed Input (" + algorithm.toJavaName() + "): " + hexedInput);
    }

    public String convertToHex(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm.toJavaName());
            byte[] hash = digest.digest(input.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Oops! Something weird happened!");

        }
        return null;
    }
}
