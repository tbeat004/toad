package toad.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public String default_mp3_dir = System.getProperty("user.dir");
    public String default_mp4_dir = System.getProperty("user.dir");
    public Map<String, String> aliases = new HashMap<>();

    // Static holder for config instance and loader/saver
    public static class Manager {
        private static final File CONFIG_FILE = resolveConfigPath();
        private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        private static Config config;

        public static Config get() {
            if (config != null) return config;

            if (!CONFIG_FILE.exists()) {
                config = new Config();
                save(); // Create it on first use
            } else {
                try {
                    config = mapper.readValue(CONFIG_FILE, Config.class);
                } catch (IOException e) {
                    System.err.println("⚠️ Failed to read config. Using default.");
                    config = new Config();
                }
            }
            return config;
        }

        public static void save() {
            try {
                CONFIG_FILE.getParentFile().mkdirs();
                mapper.writerWithDefaultPrettyPrinter().writeValue(CONFIG_FILE, config);
            } catch (IOException e) {
                System.err.println("⚠️ Failed to save config: " + e.getMessage());
            }
        }

        private static File resolveConfigPath() {
            try {
                File jarDir = new File(Config.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI()).getParentFile();
                return new File(jarDir, "config.yaml");
            } catch (Exception e) {
                throw new RuntimeException("Unable to resolve config path", e);
            }
        }
    }
}
